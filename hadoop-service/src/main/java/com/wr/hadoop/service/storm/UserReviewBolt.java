package com.wr.hadoop.service.storm;

import com.wr.hadoop.service.hbase.HBaseOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * 将评价信息转化为矩阵形式并写入HBase中
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/21.
 */
public class UserReviewBolt extends BaseRichBolt {
    private static final Log LOG = LogFactory.getLog(UserReviewBolt.class);

    private static final String USER_REVIEW_TABLE = "user-review";
    private static final String COLUME_FAMILY_REVIEW_INFO = "reviewInfo";
    private static final String COLUME_ITEM = "item";
    private static final String COLUME_RATING = "rating";
    private static final String COLUME_WORDS = "words";

    @Autowired
    private HBaseOperation hBaseOperation;

    private OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        try {
            if (!hBaseOperation.isTableExist(USER_REVIEW_TABLE)){
                String[] columFamilies = {COLUME_FAMILY_REVIEW_INFO};
                hBaseOperation.createTable(USER_REVIEW_TABLE, columFamilies);
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    public void execute(Tuple tuple) {
        String user = tuple.getStringByField("user");
        String item = tuple.getStringByField("item");
        Float rating = tuple.getFloatByField("rating");
        String words = tuple.getStringByField("words");

        insertRow(user, item, rating, words);

        String[] wordsArray = words.split("\\s+");
        for (String word : wordsArray) {
            collector.emit("words-review", tuple, new Values(word));
        }
        collector.ack(tuple);

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream("words-review", new Fields("word"));
    }

    public void insertRow(String user, String item, Float rating, String words) {
        Put put = new Put(Bytes.toBytes(user));
        put.addColumn(Bytes.toBytes(COLUME_FAMILY_REVIEW_INFO), Bytes.toBytes(COLUME_RATING), Bytes.toBytes(rating));
        put.addColumn(Bytes.toBytes(COLUME_FAMILY_REVIEW_INFO), Bytes.toBytes(COLUME_ITEM), Bytes.toBytes(item));
        put.addColumn(Bytes.toBytes(COLUME_FAMILY_REVIEW_INFO), Bytes.toBytes(COLUME_WORDS), Bytes.toBytes(words));
        try {
            hBaseOperation.insertRow(USER_REVIEW_TABLE, put);
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }
}
