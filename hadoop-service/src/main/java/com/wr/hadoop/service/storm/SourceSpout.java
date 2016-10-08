package com.wr.hadoop.service.storm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/24.
 */
public class SourceSpout extends BaseRichSpout {
    private static final Log LOG = LogFactory.getLog(SourceSpout.class);

    private SpoutOutputCollector collector;
    private static final String SOURCE_FILE = "Musical_Instruments_5.json";
    private List<String> reviewContent = null;
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        try {
            initReviewContent();
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    public void nextTuple() {
        for (String review : reviewContent) {
            this.collector.emit(new Values(review));
            try {
                Thread.sleep(5000); //每5秒发送一行
            } catch (InterruptedException e) {
                LOG.error(e.getLocalizedMessage());
            }
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("review"));
    }

    private void initReviewContent() throws IOException {
        if (reviewContent == null) {
            File file = new File(this.getClass().getResource("/").getPath() + "/resources/" + SOURCE_FILE);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    reviewContent.add(lineTxt);
                }
                read.close();
            }
        }
    }
}
