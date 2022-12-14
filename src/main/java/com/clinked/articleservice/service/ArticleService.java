package com.clinked.articleservice.service;

import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import java.util.List;

public interface ArticleService {
    public List<Article> getArticles(int pageNo, int pageSize, String sortBy, boolean direction);

    public Article createArticle(Article article);

    List<Statistics> getStatistics();
}
