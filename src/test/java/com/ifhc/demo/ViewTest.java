package com.ifhc.demo;

import com.ifhc.entity.View;
import com.ifhc.mapper.ViewMapper;
import com.ifhc.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.net.util.IPAddressUtil;

import javax.swing.*;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ViewTest {

    @Autowired
    ViewMapper viewMapper;

    @Autowired
    ArticleService articleService;

    @Test
    public void testSearch(){
        View one=new View();
        one.setArticleId(1);
        one.setUserId(2);
        View view=viewMapper.getByView(one);
        System.out.println(view);

        if(view!=null){
            view.setInterest(10);
            viewMapper.updateView(view);
        }
    }

    /**
     * 生成评分数据 10000篇
     */
    @Test
    public void autoFillRecord(){
        int total=101;
        View demo=new View();
        Random random = new Random();
        HashMap<Integer, List> articles = new HashMap<>();
        for(int i=1; i<total; i++){
            ArrayList<Double> mark = new ArrayList<>();
            demo.setArticleId(i);
            for(int j=1; j<5; j++){
                demo.setUserId(j);
                int tamp= random.nextInt(6);
                mark.add((double)tamp);
                demo.setInterest(tamp);
                demo.setQuality(random.nextInt(6));
               articleService.saveOrUpdate(demo);
//                System.out.println(demo);
            }
            System.out.println("文章i:"+i+"评分列表:"+mark+"==========================");
            articles.put(i,mark);
        }

        System.out.println("开始插入相似矩阵表❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤");
        for (int i=1; i<total; i++){
            for(int j=i; j<total; j++){
                if(i==j){
                    viewMapper.insertMatrix(i,j,1.0);
                    System.out.println(i+"和"+j+"的相似是===============1");
                }else{
                    viewMapper.insertMatrix(i,j,Ssimilarity(articles.get(i),articles.get(j)));
                    System.out.println(i+"和"+j+"的相似是==============="+Ssimilarity(articles.get(i),articles.get(j)));
                }
            }
        }

    }

    @Test
    public void autoMatrix(){
        viewMapper.insertMatrix(1,2,1.8888888);
    }

    public Double Ssimilarity(List<Double> a,List<Double> b){

        Double son=a.get(0)*b.get(0)+a.get(1)*b.get(1)+a.get(2)*b.get(2)+a.get(3)*b.get(3);
        Double mother=(Math.sqrt(a.get(0)*a.get(0)+a.get(1)*a.get(1)+a.get(2)*a.get(2)+a.get(3)*a.get(3)))*(Math.sqrt(b.get(0)*b.get(0)+b.get(1)*b.get(1)+b.get(2)*b.get(2)+b.get(3)*b.get(3)));
        if(mother==0.0d){
            return mother;
        }
        return son/mother;
    }
}
