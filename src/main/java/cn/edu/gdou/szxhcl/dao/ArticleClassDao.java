package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.ArticleClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleClassDao extends CrudRepository<ArticleClass,String> {
    ArticleClass findFirstById(String id);
    List<ArticleClass> findAll();
    List<ArticleClass> findAllByIdIsNotNullOrderBySortDtDesc();
    Long countAllByClassNameAndIdNot(String className, String id);
}
