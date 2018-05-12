package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkQueryVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkSubmitVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkVo;

import java.util.List;

public interface HomeworkService {
    List<HomeworkVo> getHomeworkListByUser(HomeworkQueryVo queryVo, String userId);
    HomeworkVo getHomework(String id, Boolean withContent, Boolean withSubmit);
    HomeworkVo saveHomework(HomeworkVo homeworkVo, String userId);
    void deleteHomework(String... ids);
    List<HomeworkSubmitVo> getSubmitList(String id);
    void deleteHomeworkSubmit(String... ids);
    void score(String submitId, Integer score);
}
