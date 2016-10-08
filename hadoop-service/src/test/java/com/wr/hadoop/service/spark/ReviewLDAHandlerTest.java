package com.wr.hadoop.service.spark;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/10/6.
 */
public class ReviewLDAHandlerTest {

    private static final String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    /*@Test
    public void test1() {
        String str = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*" +
                ".中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        System.out.println( m.replaceAll("").trim());
    }*/

    @Test
    public void test2() {
        String str = "Not much to write about here, but it does exactly what it's supposed to. filters out the pop sounds. now my recordings are much more crisp. it is one of the lowest prices pop filters on amazon so might as well buy it, they honestly work the same despite their pricing,";

        System.out.println(str.replaceAll(regEx," "));
    }

}