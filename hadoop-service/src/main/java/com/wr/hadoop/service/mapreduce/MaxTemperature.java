package com.wr.hadoop.service.mapreduce;

import com.wr.hadoop.common.MapReduceException;
import com.wr.hadoop.service.hdfs.HdfsOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Administrator on 2016/8/11.
 */
@Component
public class MaxTemperature {
    public static void mapreduce(String input, String output) throws MapReduceException {
        if (StringUtils.isEmpty(input) || StringUtils.isEmpty(output)) {
            throw new MapReduceException("invalid input for a mapreduce service");
        }

        Job job = null;
        try {
            job = Job.getInstance();
            job.setJobName("hadoop mapreduce MaxTemperature");
            //配置作业各个类
            job.setJarByClass(MaxTemperature.class);
            job.setMapperClass(MaxTemperatureMapper.class);
            job.setCombinerClass(MaxTemperatureReducer.class);
            job.setReducerClass(MaxTemperatureReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath((JobConf) job.getConfiguration(), new Path(input));
            FileOutputFormat.setOutputPath((JobConf) job.getConfiguration(), new Path(output));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*JobConf conf = new JobConf(MaxTemperature.class);
        conf.set("mapreduce.job.jar" , "D:\\Code\\Hadoop\\hadoop-parent\\hadoop-service\\target\\" +
                "hadoop-service-1.0-SNAPSHOT.jar");
        conf.setJobName("Max temperature");
        conf.set("fs.default.name", "hdfs://172.16.100.78:9000");
        conf.set("mapred.job.tracker", "172.16.100.78:9001");

        FileInputFormat.addInputPath(conf, new Path(input));
        FileOutputFormat.setOutputPath(conf, new Path(output));
        conf.setMapperClass(MaxTemperatureMapper.class);
        conf.setReducerClass(MaxTemperatureReducer.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        JobClient.runJob(conf);*/
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException,
            MapReduceException {
        HdfsOperation.removeFile(args[1]);
        mapreduce(args[0], args[1]);
    }
}
