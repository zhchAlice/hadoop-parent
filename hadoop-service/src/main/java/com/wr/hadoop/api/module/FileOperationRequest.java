package com.wr.hadoop.api.module;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Administrator on 2016/8/10.
 */
public class FileOperationRequest {
    @JsonProperty("srcFilePath")
    private String srcFilePath;
    @JsonProperty("dstFilePath")
    private String dstFilePath;

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

    public String getDstFilePath() {
        return dstFilePath;
    }

    public void setDstFilePath(String dstFilePath) {
        this.dstFilePath = dstFilePath;
    }
}
