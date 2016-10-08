package com.wr.hadoop.service.spark;

import com.wr.hadoop.common.ReviewInfo;
import org.apache.spark.ml.feature.Word2Vec;
import org.apache.spark.ml.feature.Word2VecModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/29.
 */
public class ReviewHandler {
    private static String jsonFilePath = "/spark/testfile/musical_instruments_1.json";
    public static void main(String[] args){
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
        List<Row> data = reviewInfo.select("reviewText").collectAsList();
        StructType schema = new StructType(new StructField[]{
                new StructField("text", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        });

        List<Row> data2 = new ArrayList<Row>();
        for (Row row : data) {
            data2.add(RowFactory.create(Arrays.asList(row.getString(0).split(" "))));
        }
        Dataset<Row> documentDF = spark.createDataFrame(data2, schema);

       /* Dataset<Row> test = reviewInfo.map(new MapFunction<ReviewInfo, Row>() {
            public Row call(ReviewInfo reviewInfo) throws Exception {
                String[] texts =  reviewInfo.getReviewText().split(" ");
                return RowFactory.create(texts);
            }
        }, null);*/
        Word2Vec word2Vec = new Word2Vec().setInputCol("text").setVectorSize(10)
                .setOutputCol("result");
        Word2VecModel model = word2Vec.fit(documentDF);

        Dataset<Row> result = model.transform(documentDF);
        for (Row row : result.collectAsList()) {
            List<String> text = row.getList(0);
            Vector vector = (Vector) row.get(1);
            System.out.println("Text: " + text + " => \nVector: " + vector + "\n");
        }
        spark.stop();
    }

}
