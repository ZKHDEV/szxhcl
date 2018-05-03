package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentDao extends CrudRepository<Comment,String> {
    List<Comment> findAll();
}
