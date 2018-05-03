package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Introduces;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IntroducesDao extends CrudRepository<Introduces,String> {
    List<Introduces> findAll();
}
