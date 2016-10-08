package com.wr.hadoop.service.storm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wr.hadoop.service.zk.ZKUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 读取原始的评价文本，去停用词并将新单词注册到ZK上
 * 同时以(评价人, 商品, 评分, 去通用词后评价文本)向后一个Bolt传递数据流
 */
public class StopWordsFilterBolt extends BaseRichBolt {
    private static final Log LOG = LogFactory.getLog(StopWordsFilterBolt.class);

    private OutputCollector collector;

    private static CuratorFramework zkClient = ZKUtil.getInstance().getZkclient();
    private static List<String> stopWords = null;
    private static final String stopWordsFile = "stop-words.txt";
    private static final String ZK_WORDS_ROOT_PATH = "/words/";

    /**
     * 读取配置文件的停用词到内存中
     * @throws IOException
     */
    private void initStopWords() throws IOException {
        if (stopWords == null) {
            File file = new File(this.getClass().getResource("/").getPath() + "/resources/" + stopWordsFile);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    stopWords.add(lineTxt);
                }
                read.close();
            }
        }
    }


    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        try {
            initStopWords();
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }

    public void execute(Tuple tuple) {
        String originMessage = tuple.getString(0);
        JsonObject jsonMessage = new JsonParser().parse(originMessage).getAsJsonObject();
        String user = jsonMessage.get("reviewerID").getAsString();//获取评价人
        String item = jsonMessage.get("asin").getAsString();//获取评价商品
        String rating = jsonMessage.get("overall").getAsString();//获取评分
        String reviewText = jsonMessage.get("reviewText").getAsString();//获取评价信息
        try {
            String words = handleOneReviewText(reviewText);
            if (!StringUtils.isEmpty(words)) {
                //将(user, item, rating, words)发射到"review-topic" Bolt中
                collector.emit("user-review", tuple, new Values(user, item, rating,words));
                this.collector.ack(tuple);
            }
        } catch (Exception e) {
            LOG.error(String.format("handle reviewText [%s] fail: ",reviewText,e.getLocalizedMessage()));
        }

    }


    /**
     * 定义输出流"user-review",只发射我们感兴趣的字段（user, item, rating, words）
     * @param outputFieldsDeclarer
     */
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream("user-review", new Fields("user", "item","rating", "words"));
    }

    /**
     * 在zk的${rootPath}节点上为每个单词word（非停用词）创建子节点，由ZKPathListener为该节点编号
     * @param reviewText
     * @throws Exception
     */
    private String handleOneReviewText(String reviewText) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        String[] words = reviewText.split("\\s+");
        for (String word : words) {
            String childPath = ZK_WORDS_ROOT_PATH + word;
            if (stopWords.contains(word)) {
                continue;  //去除停用词
            } else {
                if (zkClient.checkExists().forPath(childPath) == null) {
                    zkClient.create().forPath(ZK_WORDS_ROOT_PATH + word);
                    stringBuffer.append(word).append(" ");
                } else {
                    stringBuffer.append(word).append(" ");
                }
            }
        }
        return stringBuffer.toString();
    }
}
