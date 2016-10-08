package com.wr.hadoop.api.module;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Created by Administrator on 2016/8/9.
 */
@JsonRootName("result")
public class RestReturn<T> {
    @JsonProperty("status")
    private String status;
    @JsonProperty("errMsg")
    private String errMsg;
    @JsonProperty("model")
    private T model;

    public RestReturn() {
        status = "success";
    }

    public void addError(String errMsg) {
        status = "fail";
        if (this.errMsg == null) {
            this.errMsg = errMsg;
        } else {
            this.errMsg.concat(errMsg);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
