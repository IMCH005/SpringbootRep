package com.ifhc.demo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.ifhc.entity.Article;
import com.ifhc.entity.ArticleName;
import com.ifhc.entity.ArticleWord;
import com.ifhc.entity.Vocabulary;
import com.ifhc.mapper.ArticleMapper;
import com.ifhc.service.ArticleService;
import com.ifhc.util.JBUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleService articleService;

    @Test
    public void searchArticleNameByTfIdf() {
        List<String> words= JBUtil.toWord("教育");
        System.out.println(words);
        System.out.println(articleMapper.searchArticleNameByTfIdf(words));
    }

    @Test
    public void viewId(){
//       List<Integer> ids=articleMapper.sameArticleWordById(1,3);
//        System.out.println(ids);
    }

    public Double hailow(ArticleName articleName){
        Double thisEnd=0d;
        for (ArticleWord articleWord : articleName.getArticleWords()) {
            thisEnd+=Math.pow(articleWord.getTfIdf(),2);
        }
        return thisEnd=Math.sqrt(thisEnd);
    }

    @Test
    public void testCore(){
        List<ArticleName> articleNames = articleService.ArticleRecommend(1);
        for (ArticleName articleName : articleNames) {
            System.out.println(articleName);
        }
    }

    @Test
    public void testids(){
        long l = System.currentTimeMillis();
        ArticleName thisArticleName=articleMapper.getByListDimension(36,3);
//        Double thisEnd=0d;
//        for (ArticleWord articleWord : thisArticleName.getArticleWords()) {
//            thisEnd+=Math.pow(articleWord.getTfIdf(),2);
//        }
//        thisEnd=Math.sqrt(thisEnd);
        List<String> list = articleMapper.wordByArticleId(36,3);
//        System.out.println(list);
        List<Integer> ids=articleMapper.articleIdByWordList(list,9);
//        System.out.println(ids);
        Map<Double, ArticleName> articleNames = new TreeMap<>(
                new Comparator<Double>() {
                    public int compare(Double obj1, Double obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                }
        );
        Map<Double, ArticleName> returnArticleNames = new TreeMap<>(
                new Comparator<Double>() {
                    public int compare(Double obj1, Double obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                }
        );
        ArrayList<Double> top = new ArrayList<>();
        Double cos=0d;
        for (Integer id : ids) {
            if (id != 36) {
                ArticleName articleName=articleMapper.getByListDimension(id,3);
                for (ArticleWord articleWord : articleName.getArticleWords()) {
                    for (ArticleWord thisarticleWord : thisArticleName.getArticleWords()) {
                        if(thisarticleWord.getWord().equals(articleWord.getWord())){
                            cos+=thisarticleWord.getTfIdf()*articleWord.getTfIdf();
                        }
                    }
                }
                if(cos!=0d){
                    cos=cos/(hailow(thisArticleName)*hailow(articleName));
                    articleNames.put(cos,articleName);
                    top.add(cos);
                }
                cos=0d;
            }

        }
        Collections.sort(top);
        Collections.reverse(top);
        System.out.println(top);

        int index=0;
        for (Map.Entry<Double, ArticleName> entry : articleNames.entrySet()) {
            if(index<10){
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                System.out.println("key=" + key + " value=" + value);
                returnArticleNames.put(entry.getKey(),entry.getValue());
                index++;
            }else{
                break;
            }
        }

//        System.out.println(articleNames);
//        System.out.println(articleNames.size());

    }
    @Test
    public void guigui() {
        List<String> words = JBUtil.toWord("教育公平");
        System.out.println(words);
        System.out.println(articleMapper.getArticleWord(words,213343));
    }

        @Test
    public void tfidf() {
        List<ArticleName> articles=articleMapper.searchArticleNameByTfIdf(JBUtil.toWord("我来到清华大学"));
        System.out.println(articles);
    }

    @Test
    public void testRun(){
        File fileContent = new File("C:\\Users\\16282\\Desktop\\article_content.txt");
        File fileTitle = new File("C:\\Users\\16282\\Desktop\\article_name.txt");

        Map<String, Integer> mapGlb = new HashMap<>();
        Map<String, Integer> mapTitle = new HashMap<>();
        Map<String, Integer> mapContent = new HashMap<>();
        HashMap<String, String> stopMap = new HashMap<>();

        BufferedReader brContent = null;
        BufferedReader brTitle = null;
        BufferedReader stopBr = null;

        List<ArticleWord> articleWords=new ArrayList<>();

        int i=1;
        long startTime = System.currentTimeMillis();
        try {
            String stopName = "C:\\Users\\16282\\Desktop\\stop_words.txt";
            stopBr = new BufferedReader(new FileReader(stopName));
            String s = "";
            while ((s = stopBr.readLine()) != null) {
                stopMap.put(s, null);
            }

            FileInputStream fisContent = new FileInputStream(new File(fileContent, ""));
            FileInputStream fisTitle = new FileInputStream(new File(fileTitle, ""));



            brContent = new BufferedReader(new InputStreamReader(fisContent));
            brTitle = new BufferedReader(new InputStreamReader(fisTitle));

            String strContent = brContent.readLine();
            String strTitle = brTitle.readLine();

            JiebaSegmenter segmenter = new JiebaSegmenter();

            while (strContent != null) {

                List<SegToken> tokenContents = segmenter.process(strContent, JiebaSegmenter.SegMode.SEARCH);
                List<SegToken> tokenTitles = segmenter.process(strTitle, JiebaSegmenter.SegMode.SEARCH);

                for(SegToken tokenTitle:tokenTitles){
                    if (tokenTitle.word.length()>=2&&tokenTitle.word.length()<20)
                    if(!stopMap.containsKey(tokenTitle.word)){
                        if(!mapGlb.containsKey(tokenTitle.word))
                            mapGlb.put(tokenTitle.word, 1);
                        else
                        {
                            int times = mapGlb.get(tokenTitle.word) + 1;
                            mapGlb.put(tokenTitle.word, times);
                        }

//                        if(!mapTitle.containsKey(tokenTitle.word))
//                            mapTitle.put(tokenTitle.word, 1);
//                        else
//                        {
//                            int times = mapTitle.get(tokenTitle.word) + 1;
//                            mapTitle.put(tokenTitle.word, times);
//                        }**
                    }

                }


                for(SegToken tokenContent:tokenContents){
                    if (tokenContent.word.length()>=2&&tokenContent.word.length()<20)
                    if(!stopMap.containsKey(tokenContent.word)){
                        if(!mapGlb.containsKey(tokenContent.word))
                        mapGlb.put(tokenContent.word, 1);
                        else
                        {
                            int times = mapGlb.get(tokenContent.word) + 1;
                            mapGlb.put(tokenContent.word, times);
                        }

//                        if(!mapContent.containsKey(tokenContent.word))
//                         mapContent.put(tokenContent.word, 1);
//                        else
//                        {
//                            int times = mapContent.get(tokenContent.word) + 1;
//                            mapContent.put(tokenContent.word, times);
//                        }
                    }


                }



//                for(Map.Entry<String, Integer> entry : mapGlb.entrySet()){
//                    ArticleWord articleWord = new ArticleWord();
//                    articleWord.setArticleId(i);
//                    articleWord.setWord(entry.getKey());
//                    articleWord.setFreqs(entry.getValue());
//                    if(!mapTitle.containsKey(entry.getKey())){
//                        articleWord.setFreqsContent(entry.getValue());
//                        articleWord.setFreqsTitle(0);
//                    }else{
//                        articleWord.setFreqsContent(entry.getValue()-mapTitle.get(entry.getKey()));
//                        articleWord.setFreqsTitle(mapTitle.get(entry.getKey()));
//                    }
//                    articleWords.add(articleWord);
////                    System.out.println("i是多少"+i);
//                }




                if(i%10000==0){
//                   articleMapper.addArticleWord(articleWords);
//                    System.out.println(articleWords);
                    System.out.println("---------小编带你跑代码--"+i+"---篇过去了-------------------------"+ (System.currentTimeMillis() - startTime) + "ms");
//                    articleWords.clear();
                }
                strContent = brContent.readLine();
                strTitle = brTitle.readLine();
//                System.out.println(mapTitle);
//                System.out.println(mapGlb);
//                System.out.println("__________________________________________________");
//                mapTitle.clear();
//                mapContent.clear();
//                mapGlb.clear();
                i++;
            }


            List<Vocabulary> vocabularies=new ArrayList<>();
            for(Map.Entry<String, Integer> entry : mapGlb.entrySet()){
//                System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue())
                vocabularies.add(new Vocabulary(entry.getKey(),entry.getValue()));
            }
            articleMapper.addVoc(vocabularies);
            System.out.println("插入成功");
//            System.out.println(map);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            articleMapper.addArticleWord(articleWords);
//            System.out.println("最后那点东西也塞进去了");


            if (brContent != null) {
                try {
                    brContent.close();
                } catch (IOException e2) {
                    //添加日志文件吧
                }
            }

            if (brTitle != null) {
                try {
                    brTitle.close();
                } catch (IOException e2) {
                    //添加日志文件吧
                }
            }
        }
    }

    /**
     * 庄某的test
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        long startTime = System.currentTimeMillis();
        int cnt = 0;
        BufferedReader stopBr = null;
        BufferedReader titleBr = null;
        BufferedReader contentBr = null;
        JiebaSegmenter segmenter = new JiebaSegmenter();
        HashMap<String, String> stopMap = new HashMap<>();
        HashMap<String, Integer> map = new HashMap<>();
        List<ArticleWord> articleWords = new ArrayList<>();
        HashMap<String, Integer> titleMap = new HashMap<>();
        HashMap<String, Integer> contentMap = new HashMap<>();
        HashSet<String> set = new LinkedHashSet<>();
//        List<String> strings = new List<>();
        try {
            //1.首先读取停用词文件   并存入map中
            String stopName = "C:\\Users\\zzq\\Desktop\\stop_words.txt";
            stopBr = new BufferedReader(new FileReader(stopName));
            String s = "";
            while ((s = stopBr.readLine()) != null) {
                stopMap.put(s, s);
            }


            //2.读取文章标题和内容
            String titleName = "C:\\Users\\zzq\\Desktop\\title.txt";
            titleBr = new BufferedReader(new FileReader(titleName));
            String contentName = "C:\\Users\\zzq\\Desktop\\content.txt";
            contentBr = new BufferedReader(new FileReader(contentName));
            String titleStr = "";

            titleBr.readLine(); //因为tex文件首行是title content
            contentBr.readLine();
            //标题和内容的数量是一样的
            while((titleStr = titleBr.readLine()) != null){
                cnt+=1;
//                if(cnt==1001) break;
                //对标题进行分词
                List<SegToken> process = segmenter.process(titleStr, JiebaSegmenter.SegMode.SEARCH);

                int tempCnt = 0; //用来保存word在title中出现的次数
                for (SegToken segToken : process) {
                    if(stopMap.get(segToken.word) == null){ //去除停用词 在停用词map中找不到
                        //统计
                        //1.统计word在所有文章中出现的次数
                        Integer integer = map.get(segToken.word);
                        Integer titleInt = titleMap.get(segToken.word);
                        if(integer==null) integer=0;
                        integer+=1;
                        if(titleInt==null) titleInt=0;
                        titleInt+=1;
                        map.put(segToken.word,integer);
                        //统计word在title中出现的次数 循环结束后要清空
                        titleMap.put(segToken.word,titleInt);
                        set.add(segToken.word);
                    }
                }

                //读取文章内容
                String contentStr = contentBr.readLine();
                //对内容进行分词
                process = segmenter.process(contentStr, JiebaSegmenter.SegMode.SEARCH);
                for (SegToken segToken : process) {
                    if(stopMap.get(segToken.word) == null){ //去除停用词 在停用词map中找不到
                        //统计
                        //1.统计word在所有文章中出现的次数
                        Integer integer = map.get(segToken.word);
                        Integer contentInt= contentMap.get(segToken.word);
                        if(integer==null) integer=0;
                        integer+=1;
                        if(contentInt==null) contentInt=0;
                        contentInt+=1;
                        map.put(segToken.word,integer);
                        //统计word在content中出现的次数 循环结束后要清空
                        contentMap.put(segToken.word,contentInt);
                        set.add(segToken.word);
                    }
                }
                for (String s1 : set) {
                    ArticleWord articleWord = new ArticleWord();
                    articleWord.setArticleId(cnt);
                    articleWord.setWord(s1);
                    Integer freqsTitle = titleMap.get(s1);
                    if(freqsTitle==null) freqsTitle=0;
                    Integer freqsContent = contentMap.get(s1);
                    if(freqsContent==null) freqsContent=0;
                    articleWord.setFreqsTitle(freqsTitle);
                    articleWord.setFreqsContent(freqsContent);
                    articleWord.setFreqs(freqsContent+freqsTitle);
                    articleWords.add(articleWord);
                }
                if(cnt%100==0){
                    System.out.println("---------"+cnt+"---篇过去了----------------------------"+ (System.currentTimeMillis() - startTime) + "毫秒");
                    //开始往article_word表中插入数据 数据在articleWords中

                }
//                iArticleDao.insertArticleWord(articleWords);
                set.clear();
                titleMap.clear();
                contentMap.clear();
            }
            //结束循环后开始往vocabulary插入数据  数据在map表中
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stopBr.close();
            titleBr.close();
            contentBr.close();
        }
    }



    @Test
    public void testRandomArticle(){
//        System.out.println(articleMapper);

        Random random = new Random();
        int i=random.nextInt(50000)+1;
        System.out.println(i);
        List<Article> articles=articleMapper.randomNews(i);

        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }

    @Test
    public void testSearchArticle(){
        List<ArticleName> articleNames=articleMapper.searchNews("中文");
        for (ArticleName articleName : articleNames) {
            System.out.println(articleName);
        }
    }

    @Test
    public void testListInsert(){
        List<ArticleWord> list=new ArrayList<>();
        ArticleWord articleWord = new ArticleWord();
        articleWord.setArticleId(1);
        articleWord.setWord("word");
        articleWord.setFreqs(888);
        articleWord.setFreqsTitle(999);
        articleWord.setFreqsContent(777);

        ArticleWord articleWord1 = new ArticleWord();
        articleWord1.setArticleId(1);
        articleWord1.setWord("word");
        articleWord1.setFreqs(888);
        articleWord1.setFreqsTitle(999);
        articleWord1.setFreqsContent(777);
        list.add(articleWord);
        list.add(articleWord1);
        articleMapper.addArticleWord(list);
    }


}
