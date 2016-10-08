package com.wr.hadoop.service.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;

/**
 * Created by Administrator on 2016/8/9.
 */
@Component
public class HdfsOperation {

    private static final String HADOOP_URI = "hdfs://172.16.100.78:9000";

    /**
     * 使用hadoop内置的FileUtil模块实现文件上传
     * @param srcPath
     * @param dstPath
     * @throws IOException
     */
    public static void uploadFileWithFileUtil(String srcPath, String dstPath) throws IOException {
        //System.setProperty("hadoop.home.dir", "D:\\Code\\Hadoop\\hadoop-2.6.4");
        String dstFile = dstPath.substring(0,dstPath.lastIndexOf("/"));
        if (!isFileExist(dstFile)) {
            createFile(dstFile);
        }
        if (isFileExist(dstPath)) {
            removeFile(dstPath);
        }
        File input = new File(srcPath);
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HADOOP_URI), conf);
        FileUtil.copy(input,fs, new Path(dstPath), false, conf);
    }

    /**
     * 使用hadoop内置的IOUtils模块实现文件上传
     * @param srcPath
     * @param dstPath
     * @throws IOException
     */
    public static void uoloadFile(String srcPath, String dstPath) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(srcPath));
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dstPath), conf);
        OutputStream out = fs.create(new Path(dstPath), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in, out, 4096, true);
    }

    private static boolean isFileExist(String path) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HADOOP_URI), conf);
        return fs.exists(new Path(path));
    }

    public static void createFile(String path) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HADOOP_URI), conf);
        if(!fs.mkdirs(new Path(path))) {
            throw new IOException(String.format("mkdir [%s] fail", path));
        }
    }

    public static void removeFile(String path) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(HADOOP_URI), conf);
        fs.delete(new Path(path),true);
    }
}
