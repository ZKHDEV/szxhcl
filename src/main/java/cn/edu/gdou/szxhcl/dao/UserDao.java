package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User,String>, JpaSpecificationExecutor<User> {
    List<User> findAll();
    User findFirstById(String id);
    User findFirstByUsername(String username);
    Long countAllByUsername(String username);
    Long countAllByUsernameAndIdNot(String username, String id);
}
