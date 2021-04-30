package com.ifhc.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.ArrayList;
import java.util.List;

public class JBUtil {
    public static List<String> toWord(String input) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        input=input.replace(" ","");
        List<SegToken> tokenList = segmenter.process(input, JiebaSegmenter.SegMode.SEARCH);
        List<String> token = new ArrayList<>();
        for(SegToken tokenTitle : tokenList){
            token.add(tokenTitle.word);
        }
        return token;
    }
}
