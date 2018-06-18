package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.vo.exam.ChoiceQueryVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ChoiceVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ExamQueryVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ExamVo;

import java.util.List;

public interface ExamService {
    ExamVo saveExam(ExamVo examVo, String userId, Boolean isAdmin);
    ExamVo getExam(String id, String userId, Boolean isAdmin);
    List<ExamVo> getExamList(ExamQueryVo queryVo, String userId, Boolean isAdmin);
    void deleteExam(String userId, Boolean isAdmin, String... ids);
    ChoiceVo saveChoice(ChoiceVo choiceVo, String userId, Boolean isAdmin);
    ChoiceVo getChoice(String id, String userId, Boolean isAdmin);
    List<ChoiceVo> getChoiceList(ChoiceQueryVo queryVo, String examId, String userId, Boolean isAdmin);
    void deleteChoice(String userId, Boolean isAdmin, String... ids);
    void sortChoice(String id, String userId, Boolean isAdmin);

    List<ExamVo> getExamList();
    ExamVo getExam(String id, Boolean withChoice);
}
