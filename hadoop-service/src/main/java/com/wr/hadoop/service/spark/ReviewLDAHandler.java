package com.wr.hadoop.service.spark;

import com.wr.hadoop.common.ReviewInfo;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.LDA;
import org.apache.spark.mllib.clustering.LDAModel;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.sql.*;
import scala.Tuple2;

import java.util.Arrays;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/10/6.
 */
public class ReviewLDAHandler {
    private static String jsonFilePath = "/spark/testfile/musical_instruments_1.json";
    private static final String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    private static final HashingTF hashingTF = new HashingTF(50);
    public static void main(String[] args) {
        //1.create a SparkSession
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("ReviewHandler")
                .config("spark.sql.warehouse.dir", "file:///")
                .getOrCreate();

        //2. create a Encoder for javaBean-->ReviewInfo
        Encoder<ReviewInfo> reviewInfoEncoder = Encoders.bean(ReviewInfo.class);


        //3. convert DataFrame to DataSet by providing a class, Mapping based on name
        Dataset<ReviewInfo> reviewInfo = spark.read().json(jsonFilePath).as(reviewInfoEncoder);

        JavaRDD<Row> test = reviewInfo.select("reviewText").toJavaRDD();
        JavaRDD<Vector> parsedData = test.map(new Function<Row, Vector>() {
            public Vector call(Row row) throws Exception {
                String rowString = row.getString(0);
                rowString = rowString.replaceAll(regEx," ");
                return hashingTF.transform(Arrays.asList(rowString.split(" "))).toDense();
            }
        });

        JavaPairRDD<Long, Vector> corpus = JavaPairRDD.fromJavaRDD(parsedData.zipWithIndex().map(
                new Function<Tuple2<Vector, Long>, Tuple2<Long, Vector>>() {
                    public Tuple2<Long, Vector> call(Tuple2<Vector, Long> vectorLongTuple2) throws Exception {
                        return vectorLongTuple2.swap();
            }
        }));
        corpus.cache();

        LDAModel ldaModel = new LDA().setK(3).run(corpus);
        System.out.println("Learned topics (as distributions over vocab of " + ldaModel.vocabSize()
                + " words):");
        Matrix topics = ldaModel.topicsMatrix();
        for (int topic = 0; topic < 3; topic++) {
            System.out.print("Topic " + topic + ":");
            for (int word = 0; word < ldaModel.vocabSize(); word++) {
                System.out.print(" " + topics.apply(word, topic));
            }
            System.out.println();
        }
    }

}
