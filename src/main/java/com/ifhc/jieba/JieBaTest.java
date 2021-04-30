package com.ifhc.jieba;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JieBaTest {
    public static void main(String[] args) {
       new JieBaTest().grepAWord();
    }


    public void grepAWord(){
        File file = new File("C:\\Users\\16282\\Desktop\\article_content.txt");
        BufferedReader br = null;
        BufferedReader stopBr = null;
        int i=1;
        HashMap<String, String> stopMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
        try {

            String stopName = "C:\\Users\\16282\\Desktop\\stop_words.txt";
            stopBr = new BufferedReader(new FileReader(stopName));
            String s = "";
            while ((s = stopBr.readLine()) != null) {
                stopMap.put(s, null);
            }

            FileInputStream fis = new FileInputStream(new File(file, ""));
            Map<String, Integer> map = new HashMap<>();
            br = new BufferedReader(new InputStreamReader(fis));
            String str = br.readLine();
            JiebaSegmenter segmenter = new JiebaSegmenter();
            while (str != null) {      //***篇级
                List<SegToken> tokenList = segmenter.process(str, JiebaSegmenter.SegMode.SEARCH);
                for(SegToken tokenTitle:tokenList){
                    if (tokenTitle.word.length()>=2&&tokenTitle.word.length()<20){
                        if(!stopMap.containsKey(tokenTitle.word)){

                        }
                    }
                }

                if(i%10000==0){
                    System.out.println("---------"+i+"---篇过去了----------------------------"+ (System.currentTimeMillis() - startTime) + "毫秒");

                }
                s = br.readLine();
                i++;

            }


            System.out.println(map);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e2) {
                    //添加日志文件吧
                }
            }


        }


    }
}