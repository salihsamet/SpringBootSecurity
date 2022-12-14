package com.clinked.articleservice.service;

import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService{

    @Autowired
    ArticleRepository articleRepository;

    @Value("${articleCountForDays}")
    private int articleCountForDays;

    public List<Article> getArticles(int pageNo, int pageSize, String sortBy, boolean direction){
        Pageable paging = PageRequest.of(pageNo, pageSize, direction ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<Article> pagedResult = articleRepository.findAll(paging);

        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<Article>();

    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public List<Statistics> getStatistics() {
        LocalDate daysAgo = LocalDate.now().minusDays(articleCountForDays);
        List<Statistics> statistics = articleRepository.getStatistics(daysAgo);
        return statistics;
    }

}
