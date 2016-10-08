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
import org.apache.storm.tuple.Tuple;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/21.
 */
public class WordsSumBolt extends BaseRichBolt {
    private static final Log LOG = LogFactory.getLog(WordsSumBolt.class);
    private OutputCollector collector;
    @Autowired
    private HBaseOperation hBaseOperation;

    private Map<String, Integer> wordsCount;
    private static final String WORDS_SUM_TABLE = "words-sum";
    private static final String COLUME_FAMILY_SUM_INFO = "countInfo";
    private static final String COLUME_COUNT = "count";

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        wordsCount = new HashMap<String, Integer>();
        try {
            if (!hBaseOperation.isTableExist(WORDS_SUM_TABLE)){
                String[] columFamilies = {COLUME_FAMILY_SUM_INFO};
                hBaseOperation.createTable(WORDS_SUM_TABLE, columFamilies);
            }
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        Integer count = wordsCount.get(word);
        if (count == null) {
            count = 0;
        }
        count++;
        wordsCount.put(word, count);

        Put put = new Put(Bytes.toBytes(word));
        put.addColumn(Bytes.toBytes(COLUME_FAMILY_SUM_INFO), Bytes.toBytes(COLUME_COUNT), Bytes.toBytes(count));
        try {
            hBaseOperation.insertRow(WORDS_SUM_TABLE, put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
