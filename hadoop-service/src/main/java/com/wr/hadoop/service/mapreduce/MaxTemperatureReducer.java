package com.wr.hadoop.service.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MaxTemperatureReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    public void reduce(Text key,Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
        int maxValue = Integer.MIN_VALUE;
        for(IntWritable val:values){
            maxValue = Math.max(maxValue, val.get());
        }
        context.write(key, new IntWritable(maxValue));
    }
    /*public void reduce(Text text, Iterator<IntWritable> iterator, OutputCollector<Text, IntWritable> outputCollector,
                       Reporter reporter) throws IOException {
        int maxValue = Integer.MIN_VALUE;
        while (iterator.hasNext()) {
            maxValue = Math.max(maxValue, iterator.next().get());
        }
        outputCollector.collect(text, new IntWritable(maxValue));
    }*/
}
