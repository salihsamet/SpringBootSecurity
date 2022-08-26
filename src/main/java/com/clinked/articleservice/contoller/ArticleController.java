package com.clinked.articleservice.contoller;

import com.clinked.articleservice.models.ApiResponse;
import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.service.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    public static final Logger LOG = LogManager.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity getArticles(@RequestParam(defaultValue = "1") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "true") boolean direction) throws Exception {
        try {
            List<Article> articleList = articleService.getArticles(pageNo - 1, pageSize, sortBy, direction);
            return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse(HttpStatus.OK.value(), articleList));
        } catch (Exception anyException){
            LOG.error("Error getting an articles.");
            throw new Exception("Error getting an articles.");
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity  createArticle(@Validated  @RequestBody Article article) throws Exception {
        try {
            articleService.createArticle(article);
            return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse(HttpStatus.OK.value()));
        } catch (Exception anyException){
            LOG.error(format("Error creating an article. Parameters: %s", article.toString()));
            throw new Exception("Error creating an article.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin/statistics")
    public ResponseEntity getStatistics() throws Exception {
        try {
            List<Statistics> statistics = articleService.getStatistics();
            return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse(HttpStatus.OK.value(), statistics));
        } catch (Exception anyException){
            LOG.error("Error getting article statistics.");
            throw new Exception("Error getting article statistics.");
        }
    }
}
