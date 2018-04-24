package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Admin;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDao extends CrudRepository<Admin,String> {
}
