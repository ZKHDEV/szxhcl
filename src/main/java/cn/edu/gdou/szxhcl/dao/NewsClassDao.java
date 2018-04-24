package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.NewsClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsClassDao extends CrudRepository<NewsClass,String> {
    NewsClass findFirstById(String id);
    List<NewsClass> findAll();
    Long countAllByClassNameAndIdNot(String className, String id);
}
