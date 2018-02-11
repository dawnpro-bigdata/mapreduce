package com.dawnpro.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class TripTagger {
    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, Text> {

        private Text VIN = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String[] itr = value.toString().split("\\t");
            VIN.set(itr[0]);
            context.write(VIN, value);
        }
    }

    public static class TagReducer
            extends Reducer<Text,Text,Text,Text> {

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {
            List<TboxRecord> allRecords = new ArrayList<TboxRecord>();
            for (Text val : values) {
                allRecords.add(new TboxRecord(val.toString()));
            }
            Collections.sort(allRecords);
            for (TboxRecord item : allRecords) {
                context.write(key, new Text(item.toString()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: TripTagger <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "TripTagger");
        job.setJarByClass(TripTagger.class);
        job.setMapperClass(TripTagger.TokenizerMapper.class);
        job.setReducerClass(TripTagger.TagReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job,
                new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
