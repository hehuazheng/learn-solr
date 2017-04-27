package com.hzz.util;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;

/**
 * Created by hejf on 2017/2/13.
 */
public class HbaseUtil {
    public static String makeBaseRowKeyStr(String id) {
        return MD5Hash.getMD5AsHex(Bytes.toBytes(id));
    }

    public static byte[] makeProductRowKey(String productId) {
        return Bytes.toBytes(makeBaseRowKeyStr(productId));
    }
}
