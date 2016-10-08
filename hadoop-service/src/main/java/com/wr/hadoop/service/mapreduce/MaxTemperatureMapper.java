package com.wr.hadoop.service.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MaxTemperatureMapper  extends Mapper<Object,Text,Text,IntWritable> {

    public void map(Object key,Text text,Context context) throws IOException, InterruptedException {
        String line = text.toString();
        String year = line.substring(15, 19);
        int airTemperature;
        if (line.charAt(25) == '+') {
            airTemperature = Integer.parseInt(line.substring(26, 30));
        } else {
            airTemperature = Integer.parseInt(line.substring(25, 30));
        }
        context.write(new Text(year), new IntWritable(airTemperature));
    }
    /*public void map(LongWritable longWritable, Text text, OutputCollector<Text, IntWritable> outputCollector,
                    Reporter reporter) throws IOException {
        String line = text.toString();
        String year = line.substring(15, 19);
        int airTemperature;
        if (line.charAt(25) == '+') {
            airTemperature = Integer.parseInt(line.substring(26, 30));
        } else {
            airTemperature = Integer.parseInt(line.substring(25, 30));
        }
        outputCollector.collect(new Text(year), new IntWritable(airTemperature));
    }*/
}
