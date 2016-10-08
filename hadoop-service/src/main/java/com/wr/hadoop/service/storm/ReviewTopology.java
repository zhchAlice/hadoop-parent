package com.wr.hadoop.service.storm;

import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.Arrays;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/23.
 */
public class ReviewTopology {
    private static final String zks = "zk01:2181,zk02:2181,zk03:2181";
    private static final String topic = "review-topic";
    private static final String zkRoot = "/topics";
    private static final String id = "musicReview";

    public static void main(String[] args) {
        SpoutConfig spoutConf = getKafkaSpoutConf();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout", new KafkaSpout(spoutConf), 1);//定义kafkaSpout
        builder.setBolt("stopWordsFilter",new StopWordsFilterBolt()).shuffleGrouping("kafkaSpout");
        builder.setBolt("userReview", new UserReviewBolt()).fieldsGrouping("stopWordsFilter",new Fields("user"));
        builder.setBolt("wordsSum", new WordsSumBolt()).fieldsGrouping("userReview", new Fields("word"));
    }

    private static SpoutConfig getKafkaSpoutConf() {
        BrokerHosts brokerHosts = new ZkHosts(zks);
        SpoutConfig spoutConf = new SpoutConfig(brokerHosts, topic, zkRoot, id);
        spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
        spoutConf.zkServers = Arrays.asList(new String[] {"zk01", "zk02", "zk03"});
        spoutConf.zkPort = 2181;
        return spoutConf;
    }
}
