package com.wr.hadoop.api;

import com.wr.hadoop.api.module.FileOperationRequest;
import com.wr.hadoop.api.module.RestReturn;
import com.wr.hadoop.common.MapReduceException;
import com.wr.hadoop.service.hdfs.HdfsOperation;
import com.wr.hadoop.service.mapreduce.MaxTemperature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by Administrator on 2016/8/9.
 */
@Controller
@RequestMapping(value="/rest")
public class HadoopApi {

    private static Log log = LogFactory.getLog("service");

    @Autowired
    private HdfsOperation hdfsOperation;

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    @ResponseBody
    public RestReturn<String> uploadFile(@RequestBody FileOperationRequest request) {
        log.info("start upload file");
        RestReturn<String> result = new RestReturn<String>();
        if (request == null || request.getSrcFilePath() == null || request.getDstFilePath() == null) {
            result.addError("invalid request params");
            return result;
        }
        try {
            hdfsOperation.uploadFileWithFileUtil(request.getSrcFilePath(), request.getDstFilePath());
        } catch (IOException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="/remove",method= RequestMethod.POST)
    @ResponseBody
    public RestReturn<String> deleteFile(@RequestBody FileOperationRequest request) {
        log.info("start delete file");
        RestReturn<String> result = new RestReturn<String>();
        if (request == null || request.getSrcFilePath() == null) {
            result.addError("invalid request params");
            return result;
        }
        try {
            hdfsOperation.removeFile(request.getSrcFilePath());
        } catch (IOException e) {
            result.addError(e.getMessage());
        }
        return result;
    }

    /*@RequestMapping(value="/submit",method= RequestMethod.POST)
    @ResponseBody
    public RestReturn<String> submitMapReduceTask(@RequestBody FileOperationRequest request) {
        RestReturn<String> result = new RestReturn<String>();
        if (request == null || request.getSrcFilePath() == null || request.getDstFilePath() == null) {
            result.addError("invalid request params");
            return result;
        }
        try {
            MaxTemperature.mapreduce(request.getSrcFilePath(), request.getDstFilePath());
        } catch (MapReduceException e) {
            result.addError(e.getMessage());
        }
        return  result;
    }*/
}
