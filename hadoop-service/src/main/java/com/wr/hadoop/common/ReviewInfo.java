package com.wr.hadoop.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/9/29.
 */
public class ReviewInfo implements Serializable {
    String reviewerID;
    String asin;
    String reviewerName;
    List<Integer> helpful;
    String reviewText;
    Float overall;
    String summary;
    Long unixReviewTime;
    String reviewTime;

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

   public List<Integer> getHelpful() {
        return helpful;
    }

    public void setHelpful(List<Integer> helpful) {
        this.helpful = helpful;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Float getOverall() {
        return overall;
    }

    public void setOverall(Float overall) {
        this.overall = overall;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getUnixReviewTime() {
        return unixReviewTime;
    }

    public void setUnixReviewTime(Long unixReviewTime) {
        this.unixReviewTime = unixReviewTime;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }
}
