package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.News;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsDao extends CrudRepository<News,String> {
    List<News> findAllByTitleLike(String title);
    List<News> findAllByTitleLikeAndNewsClass_Id(String title, String classId);
    News findFirstById(String id);
    Long countAllByTitleAndIdNot(String title, String id);
}
