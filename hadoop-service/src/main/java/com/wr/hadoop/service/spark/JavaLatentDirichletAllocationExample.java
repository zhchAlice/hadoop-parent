package com.wr.hadoop.service.spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.DistributedLDAModel;
import org.apache.spark.mllib.clustering.LDA;
import org.apache.spark.mllib.clustering.LDAModel;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/11/21.
 */
public class JavaLatentDirichletAllocationExample {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("JavaKLatentDirichletAllocationExample")
                .config("spark.sql.warehouse.dir", "file:///")
                .getOrCreate();


        // $example on$
        // Load and parse the data
        String path = "/spark/testfile/sample_lda_data.txt";
        JavaRDD<String> data = spark.sparkContext().textFile(path,2).toJavaRDD();
        JavaRDD<Vector> parsedData = data.map(
                new Function<String, Vector>() {
                    public Vector call(String s) {
                        String[] sarray = s.trim().split(" ");
                        double[] values = new double[sarray.length];
                        for (int i = 0; i < sarray.length; i++) {
                            values[i] = Double.parseDouble(sarray[i]);
                        }
                        return Vectors.dense(values);
                    }
                }
        );
        // Index documents with unique IDs
        JavaPairRDD<Long, Vector> corpus =
                JavaPairRDD.fromJavaRDD(parsedData.zipWithIndex().map(
                        new Function<Tuple2<Vector, Long>, Tuple2<Long, Vector>>() {
                            public Tuple2<Long, Vector> call(Tuple2<Vector, Long> doc_id) {
                                return doc_id.swap();
                            }
                        }
                        )
                );
        corpus.cache();

        // Cluster the documents into three topics using LDA
        LDAModel ldaModel = new LDA().setK(3).run(corpus);

        // Output topics. Each is a distribution over words (matching word count vectors)
        System.out.println("Learned topics (as distributions over vocab of " + ldaModel.vocabSize() + " words):");
        Matrix topics = ldaModel.topicsMatrix();
        for (int topic = 0; topic < 3; topic++) {
            System.out.print("Topic " + topic + ":");
            for (int word = 0; word < ldaModel.vocabSize(); word++) {
                System.out.print(" " + topics.apply(word, topic));
            }
            System.out.println();
        }

        /*ldaModel.save(spark.sparkContext(),
                "target/org/apache/spark/JavaLatentDirichletAllocationExample/LDAModel");
        DistributedLDAModel sameModel = DistributedLDAModel.load(spark.sparkContext(),
                "target/org/apache/spark/JavaLatentDirichletAllocationExample/LDAModel");
        // $example off$*/

        spark.stop();
    }
}
