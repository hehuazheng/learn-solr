package com.hzz;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class ChineseFenci {
    public static void main(String[] args) throws IOException {
        ComplexAnalyzer analyzer = new ComplexAnalyzer();
        process(analyzer, "我来自上海蛋吧");
        ComplexAnalyzer analyzer2 = new ComplexAnalyzer("dics");
        process(analyzer2, "我来自上海蛋吧");
    }

    static void process(ComplexAnalyzer analyzer, String txt) throws IOException {
        String s = "我来自上海";
        TokenStream ts = analyzer.tokenStream(null, txt);
        if(ts != null) {
            ts.reset();
            CharTermAttribute cta = ts.addAttribute(CharTermAttribute.class);
            while(ts.incrementToken()) {
                String token = cta.toString();
                System.out.println("token : " + token);
            }
            ts.close();
        }
    }

    public static boolean isChinese(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        return sc == Character.UnicodeScript.HAN;
    }
}
