package com.hzz;

import org.nlpcn.commons.lang.jianfan.JianFan;

public class JianFanDemo {
    public static void main(String[] args) {
        String s = JianFan.j2f("中国");
        System.out.println(s);
        System.out.println(JianFan.f2j(s));
    }
}
