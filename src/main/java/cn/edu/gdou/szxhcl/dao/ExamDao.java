package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Exam;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExamDao extends CrudRepository<Exam,String>, JpaSpecificationExecutor<Exam> {
    Exam findFirstById(String id);
    Exam findFirstByIdAndUser_Id(String id, String userId);
    List<Exam> findAllByTitleLikeAndUser_NameLikeOrderByUpdateDtDesc(String title, String userName);
    List<Exam> findAllByTitleLikeAndUser_IdOrderByUpdateDtDesc(String title, String userId);
    void deleteByIdAndUser_Id(String id, String userId);
    List<Exam> findAllByIdIsNotNullOrderByCreateDtDesc();
}
