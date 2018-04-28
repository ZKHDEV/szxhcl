package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Article;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDao extends CrudRepository<Article,String>, JpaSpecificationExecutor<Article> {
    Article findFirstById(String id);
    Long countAllByTitleAndIdNot(String title, String id);
}
