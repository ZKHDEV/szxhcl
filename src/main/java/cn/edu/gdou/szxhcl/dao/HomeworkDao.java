package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Homework;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeworkDao extends CrudRepository<Homework,String>, JpaSpecificationExecutor<Homework> {
    Homework findFirstById(String id);

    @Query("SELECT a FROM Homework a" +
            " WHERE a.id NOT IN (" +
            " SELECT b.homework.id FROM HomeworkSubmit b" +
            " INNER JOIN User u" +
            " ON b.user.id = u.id" +
            " AND u.id = ?1" +
            " )" +
            " AND a.delFlag = false")
    List<Homework> findAllExceptSubmitUserId(String userId);

    @Query("SELECT a FROM Homework a" +
            " WHERE a.id IN (" +
            " SELECT b.homework.id FROM HomeworkSubmit b" +
            " INNER JOIN User u" +
            " ON b.user.id = u.id" +
            " AND u.id = ?1" +
            " )" +
            " AND a.delFlag = false")
    List<Homework> findAllBySubmitUserId(String userId);
}
