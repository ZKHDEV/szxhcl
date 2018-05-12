package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Homework;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeworkDao extends CrudRepository<Homework,String>, JpaSpecificationExecutor<Homework> {
    List<Homework> getAllByDelFlagIsFalseAndUser_RoleOrderByUpdateDtDesc(String role);
    Homework findFirstById(String id);
}
