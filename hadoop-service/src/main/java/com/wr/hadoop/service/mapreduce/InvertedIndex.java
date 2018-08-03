package com.wr.hadoop.service.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;


/**
 * @author: Alice
 * @date: 2018/7/31.
 * @since: 1.0.0
 */
public class InvertedIndex {
    public static class InvertedIndexMapper extends Mapper<Object,Text, Text, IntWritable>{
        private Text keyInfo = new Text();
        private IntWritable valueInfo = new IntWritable(1);
        private FileSplit split;

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer iter = new StringTokenizer(value.toString());
            split = (FileSplit) context.getInputSplit();
            while (iter.hasMoreTokens()){
                //key值由单词和URI组成，如"MapReduce:1.txt"
                this.keyInfo.set(iter.nextToken() + ":" + split.getPath().toString());
                context.write(keyInfo,valueInfo);   //输出<key,value>---<MapReduce:1.txt,1>
            }
        }
    }
    public static class InvertedIndexCombiner extends Reducer<Text,IntWritable,Text,Text> {
        private Text keyInfo = new Text();
        private Text valueInfo = new Text();
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            String[] strs = key.toString().split(":");
            keyInfo.set(strs[0]);
            valueInfo.set(strs[1] + ":" + sum);
            context.write(keyInfo,valueInfo);
        }
    }

    public static class InvertedIndexReducer extends Reducer<Text,Text,Text,Text> {
        private Text valueInfo = new Text();
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder stringBuilder = new StringBuilder();
            for (Text value : values) {
                stringBuilder.append(value).append(";");
            }
            valueInfo.set(stringBuilder.toString());
            context.write(key,valueInfo);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration =  new Configuration();
        String[] otherArgs = (new GenericOptionsParser(configuration, args)).getRemainingArgs();
        if(otherArgs.length < 2) {
            System.err.println("Usage: InvertedIndex  <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(configuration,"InvertedIndex");
        job.setJarByClass(InvertedIndex.class);
        job.setMapperClass(InvertedIndexMapper.class);
        job.setCombinerClass(InvertedIndexCombiner.class);
        job.setReducerClass(InvertedIndexReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true)?0:1);
    }

}
