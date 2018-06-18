package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Choice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChoiceDao extends CrudRepository<Choice,String>, JpaSpecificationExecutor<Choice> {
    Choice findFirstById(String id);
    Choice findFirstByIdAndUser_Id(String id, String userId);
    List<Choice> findAllByTitleLikeAndExam_IdAndUser_IdOrderByUpdateDtDesc(String title, String examId, String userId);
    List<Choice> findAllByTitleLikeAndExam_IdAndUser_NameLikeOrderByUpdateDtDesc(String title, String examId, String username);
    void deleteByIdAndUser_Id(String id, String userId);
}
