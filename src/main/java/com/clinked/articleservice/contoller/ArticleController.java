package com.clinked.articleservice.contoller;

import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> getArticles(@RequestParam(defaultValue = "1") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "true") boolean direction) throws Exception {
        try {
            List<Article> articleList = articleService.getArticles(pageNo-1, pageSize, sortBy, direction);
            return new ResponseEntity<List<Article>>(articleList, HttpStatus.OK);
        } catch (Exception anyException){
            throw new Exception("Error getting an articles.");
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Article>  createArticle(@Validated  @RequestBody Article article) throws Exception {
        try {
            Article createdArticle = articleService.createArticle(article);
            return new ResponseEntity<Article>(createdArticle, HttpStatus.OK);
        } catch (Exception anyException){
            throw new Exception("Error creating an article. Parameters: " + article.toString());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin/statistics")
    public ResponseEntity<List<Statistics>> getStatistics() throws Exception {
        try {
            List<Statistics> statistics = articleService.getStatistics();
            return new ResponseEntity<List<Statistics>>(statistics, HttpStatus.OK);
        } catch (Exception anyException){
            throw new Exception("Error getting an article statistics.");
        }
    }
}
