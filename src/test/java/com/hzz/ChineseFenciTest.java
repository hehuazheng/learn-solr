package com.hzz;

import org.junit.Assert;
import org.junit.Test;

/**
* ChineseFenci Tester. 
* 
* @author <Authors name> 
*/ 
public class ChineseFenciTest { 
    @Test
    public void testIsChinese() {
        Assert.assertTrue(ChineseFenci.isChinese('我'));
        Assert.assertFalse(ChineseFenci.isChinese('a'));
        Assert.assertFalse(ChineseFenci.isChinese('1'));
    }

    @Test
    public void tt() {
        double score = 10.0;
        System.out.println(String.format("+productName:(男士 男款 男型 男用 女式 男^0.5)", score));
    }

    @Test
    public void tt2() {
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        sb.insert(0, "(").append(")");
        System.out.println(sb.toString());
    }
}
