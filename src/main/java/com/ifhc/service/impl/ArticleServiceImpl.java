package com.ifhc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifhc.entity.*;
import com.ifhc.mapper.ArticleMapper;
import com.ifhc.mapper.ViewMapper;
import com.ifhc.service.ArticleService;
import com.ifhc.util.JBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate<String,PageInfo> pageInfoRedisTemplate;

    @Autowired
    private ViewMapper viewMapper;

    @Value("${selfconfig.pageSize}")
    private int pageSize;



    @Override
    public PageInfo ArticleSearch(String word,Integer pageNum) {
        PageInfo<ArticleName> pageInfo=new PageInfo<>();
        System.out.println("++++++++++++++++++++++"+pageInfo==null);
        List<ArticleName> articleNames=new ArrayList<>();
        try{
            //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
            //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
            PageHelper.startPage(pageNum,pageSize);

            articleNames =articleMapper.searchNews(word);

            System.out.println("无参数的分页数据："+articleNames);
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            pageInfo = new PageInfo<ArticleName>(articleNames,pageSize);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }

    @Override
    public PageInfo ArticleSearch(String word,String inlineRadioOptions,Integer pageNum) {
        PageInfo<ArticleName> pageInfo=new PageInfo<>();
        List<ArticleName> articleNames=new ArrayList<>();
//        System.out.println("++++++++++++++++++++++"+pageInfo.size);

        try{
            //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
            //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
            PageHelper.startPage(pageNum,pageSize);
            if(inlineRadioOptions.equals("option1")){
                System.out.println("全文搜索");
                articleNames= articleMapper.searchNews(word);
            }else if(inlineRadioOptions.equals("option2")){
                System.out.println("分词搜索"+JBUtil.toWord(word));
                articleNames= articleMapper.searchArticleNameByTfIdf(JBUtil.toWord(word));
            }else if(inlineRadioOptions.equals("option3")){
                System.out.println("like搜索");
                articleNames= articleMapper.searchNewsLike(word);
            }else if(inlineRadioOptions.equals("option4")){
                System.out.println(inlineRadioOptions);
                articleNames=articleMapper.searchArticleNameByBayes(JBUtil.toWord(word));
            }
            System.out.println(articleNames+"******************");
            for (ArticleName articleName : articleNames) {
                articleName.setArticleWords(articleMapper.getArticleWord(JBUtil.toWord(word),articleName.getId()));
                System.out.println(articleName+"+++++++++");
            }
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            pageInfo = new PageInfo<ArticleName>(articleNames,pageSize);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            PageHelper.clearPage();
        }
        return pageInfo;

    }

    @Override
    public PageInfo articleSearchcCache(String word, String inlineRadioOptions, Integer pageNum) {
        PageInfo<ArticleName> agent=pageInfoRedisTemplate.opsForValue().get(word);
        if(agent==null){
            PageInfo<ArticleName> pageInfo=ArticleSearch(word, inlineRadioOptions, pageNum);
            pageInfoRedisTemplate.opsForValue().set(word,pageInfo);
            return pageInfo;
        }else{
            System.out.println("ArticleServiceImpl:95❤❤❤❤❤❤❤❤走了缓存❤❤❤❤❤❤❤");
            pageInfoRedisTemplate.opsForValue().get(word);
            return agent;
        }
    }

    @Override
    public Article ArticleView(String docno) {
        return articleMapper.viewNews(docno);
    }

    @Override
    public Article ArticleViewById(Integer id) {
        return articleMapper.viewNewsById(id);
    }

    @Override
    public List<ArticleName> ArticleRecommendByRandom() {
        Random random=new Random();
        int randId=random.nextInt(50000)+1;
        List<ArticleName> articleNames=articleMapper.randomArticleName(randId);
        return articleNames;
    }

    @Override
    public List<ArticleName> ArticleNameRandom() {
        Random random=new Random();
        int randId=random.nextInt(50000)+1;
        List<ArticleName> articleNames=articleMapper.randomArticleName(randId);
        return articleNames;
    }

    /**
     * 核心推荐业务代码
     * @param id
     * @return
     */
    @Override
    public List<ArticleName> ArticleRecommend(Integer id) {
        View view=viewMapper.getAHighPointArticle(id);
        // 若近期没有高评评分的文章则随机推荐
        List<ArticleName> articleNames=new ArrayList<>();
        if (view == null) {
            return ArticleNameRandom();
        }else{
            List<SimilarityMatrix> recommendArticleView=viewMapper.Recommend(view.getArticleId());
            for (SimilarityMatrix similarityMatrix : recommendArticleView) {
                if(similarityMatrix.getArticleIdOne()!=view.getArticleId()){
                    articleNames.add(articleMapper.getArticleNameById(similarityMatrix.getArticleIdOne()));
                }else{
                    articleNames.add(articleMapper.getArticleNameById(similarityMatrix.getArticleIdAnother()));
                }
            }
            System.out.println("==========走了推荐=================");
        }
        return articleNames;

    }

    @Override
    public TreeMap<Double, ArticleName> ArticleSimilar(Integer id) {
        long l = System.currentTimeMillis();
        ArticleName thisArticleName=articleMapper.getByListDimension(id,3);
        System.out.println(thisArticleName+"=================耗时"+(System.currentTimeMillis()-l));
        List<String> list = articleMapper.wordByArticleId(id,3);
        System.out.println("list=================耗时"+(System.currentTimeMillis()-l));
        List<Integer> ids=articleMapper.articleIdByWordList(list,id);
        System.out.println("ids=================耗时"+ids.size()+"++++++"+(System.currentTimeMillis()-l));

        TreeMap<Double, ArticleName> articleNames = new TreeMap<>(
                new Comparator<Double>() {
                    public int compare(Double obj1, Double obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                }
        );
        TreeMap<Double, ArticleName> returnArticleNames = new TreeMap<>(
                new Comparator<Double>() {
                    public int compare(Double obj1, Double obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                }
        );
        ArrayList<Double> top = new ArrayList<>();
        Double cos=0.0d;
        for (Integer iid : ids) {
            cos=0.0d;

                ArticleName articleName=articleMapper.getByListDimension(iid,3);
                for (ArticleWord articleWord : articleName.getArticleWords()) {
                    for (ArticleWord thisarticleWord : thisArticleName.getArticleWords()) {
                        if(thisarticleWord.getWord().equals(articleWord.getWord())){
                            cos+=thisarticleWord.getTfIdf()*articleWord.getTfIdf();
                        }
                    }
                }
                if(cos!=0.0d){
                    cos=cos/(hailow(thisArticleName)*hailow(articleName));
                    if(cos<0.98d)
                    articleNames.put(cos,articleName);
//                    System.out.println(cos);
                }

            cos=0.0d;

        }

        System.out.println("处理完用时+++++++++++++++++"+(System.currentTimeMillis()-l));

        int index=0;
        for (Map.Entry<Double, ArticleName> entry : articleNames.entrySet()) {
            if(index<10){
//                String key = entry.getKey().toString();
//                String value = entry.getValue().toString();
                ArticleName art=entry.getValue();
                art.setTitle(articleMapper.getTitleById(entry.getValue().getId()));
//                System.out.println(art);
                returnArticleNames.put(entry.getKey(),art);
                index++;

            }else{
                break;
            }
        }

        return returnArticleNames;
    }

    @Override
    public int saveOrUpdate(View view) {
        View view1=viewMapper.getByView(view);
        if(view1==null){
            return viewMapper.insertView(view);
        }else{
            return viewMapper.updateView(view);
        }
    }

    public Double hailow(ArticleName articleName){
        Double thisEnd=0d;
        for (ArticleWord articleWord : articleName.getArticleWords()) {
            thisEnd+=Math.pow(articleWord.getTfIdf(),2);
        }
        return thisEnd=Math.sqrt(thisEnd);
    }
}
