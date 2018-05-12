package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Link;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LinkDao extends CrudRepository<Link,String> {
    List<Link> findAllByIdIsNotNullOrderByTitle();
    Link findFirstById(String id);
}
