package com.wr.hadoop.service.storm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.DelimitedRecordFormat;
import org.apache.storm.hdfs.bolt.format.FileNameFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.rotation.FileRotationPolicy;
import org.apache.storm.hdfs.bolt.rotation.TimedRotationPolicy;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/27.
 */
public class KafkaStormTopo {
    public static class StoreCollectionBolt extends BaseRichBolt {
        private static final Log LOG = LogFactory.getLog(StoreCollectionBolt.class);
        private OutputCollector collector;

        private static Map<String, Map<String, Integer>> pv;

        public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
            this.collector = outputCollector;
        }

        public void execute(Tuple tuple) {
            String msg = tuple.getString(0);
            LOG.info(Thread.currentThread().getName() + "RECV[kafka-->StoreCollectionBolt]: " + msg);
            String[] fields = msg.split("\\s+");
            if (fields.length == 7) {
                StoreInfo storeInfo = new StoreInfo();
                storeInfo.setName(fields[0]);
                storeInfo.setStarType(fields[1]);
                storeInfo.setTasteGrade(Float.parseFloat(fields[2]));
                storeInfo.setAtomsGrade(Float.parseFloat(fields[3]));
                storeInfo.setServiceGrade(Float.parseFloat(fields[4]));
                storeInfo.setAddress(fields[5]);
                storeInfo.setTelphone(fields[6]);
                collector.emit(tuple, new Values(fields[0]));
                collector.ack(tuple);
                return;
            } else {
                /*for(String word : fields) {
                    LOG.info("EMIT[splitter -> counter] " + word);
                    collector.emit(tuple, new Values(fields[0]));
                }*/
                collector.ack(tuple);
            }

        }

        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("storeInfo"));
        }
    }
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException, InterruptedException {
        String zks = "zk01:2181,zk02:2181,zk03:2181";
        String topic = "test-topic";
        String zkRoot = "/storm";
        String id = "dianping";

        BrokerHosts brokerHosts = new ZkHosts(zks);
        SpoutConfig spoutConf = new SpoutConfig(brokerHosts, topic, zkRoot, id);
        spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
        spoutConf.zkServers = Arrays.asList(new String[] {"zk01", "zk02", "zk03"});
        spoutConf.zkPort = 2181;

        FileNameFormat fileNameFormat = new DefaultFileNameFormat().withPath("/storm/")
                .withPrefix("app_").withExtension(".log");
        RecordFormat format = new DelimitedRecordFormat().withFieldDelimiter("\t");
        SyncPolicy syncPolicy = new CountSyncPolicy(1000);
        FileRotationPolicy rotationPolicy = new TimedRotationPolicy(1.0f, TimedRotationPolicy.TimeUnit.MINUTES);
        HdfsBolt hdfsBolt = new HdfsBolt().withFsUrl("hdfs://172.16.100.78:9000").withFileNameFormat(fileNameFormat)
                .withSyncPolicy(syncPolicy).withRotationPolicy(rotationPolicy).withRecordFormat(format);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka-reader", new KafkaSpout(spoutConf), 1);
        builder.setBolt("store-collect",new StoreCollectionBolt()).shuffleGrouping("kafka-reader");
        builder.setBolt("hdfs-store", hdfsBolt).shuffleGrouping("store-collect");
        Config conf = new Config();
        String name = KafkaStormTopo.class.getSimpleName();
        if (args != null && args.length > 0) {
            conf.put(Config.NIMBUS_HOST, args[0]);
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(name, conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(1);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(name, conf, builder.createTopology());
            Thread.sleep(60000);
            cluster.shutdown();
        }
    }
}
