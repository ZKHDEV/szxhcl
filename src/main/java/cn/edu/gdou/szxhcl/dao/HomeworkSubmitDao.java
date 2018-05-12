package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.HomeworkSubmit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeworkSubmitDao extends CrudRepository<HomeworkSubmit,String> {
    List<HomeworkSubmit> findAllByHomework_IdAndDelFlagIsFalseOrderByCreateDtDesc(String homeworkId);
    HomeworkSubmit findFirstById(String id);
}
