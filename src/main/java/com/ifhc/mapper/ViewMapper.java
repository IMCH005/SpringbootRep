package com.ifhc.mapper;

import com.ifhc.entity.ArticleName;
import com.ifhc.entity.SimilarityMatrix;
import com.ifhc.entity.View;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewMapper {
    public View getByView(View view);
    public int updateView(View view);
    public int insertView(View view);
    public int insertMatrix(Integer idA,Integer idB,Double similarity);
    public View getAHighPointArticle(Integer userId);
    public List<SimilarityMatrix> Recommend(Integer articleId);
}
