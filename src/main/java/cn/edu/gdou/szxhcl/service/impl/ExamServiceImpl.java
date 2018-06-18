package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.ChoiceDao;
import cn.edu.gdou.szxhcl.dao.ExamDao;
import cn.edu.gdou.szxhcl.dao.UserDao;
import cn.edu.gdou.szxhcl.model.Choice;
import cn.edu.gdou.szxhcl.model.Exam;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.exam.ChoiceQueryVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ChoiceVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ExamQueryVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ExamVo;
import cn.edu.gdou.szxhcl.service.ExamService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import cn.edu.gdou.szxhcl.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private ChoiceDao choiceDao;
    @Autowired
    private UserDao userDao;

    private ExamVo parseExam(Exam exam, Boolean withChoice) {
        ExamVo examVo = new ExamVo();
        examVo.setId(exam.getId());
        examVo.setTitle(exam.getTitle());
        examVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, exam.getCreateDt()));
        examVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, exam.getUpdateDt()));
        examVo.setRemarks(exam.getRemarks());
        examVo.setUserId(exam.getUser().getId());
        examVo.setUsername(exam.getUser().getName());

        int total = 0;
        List<Choice> choiceList = exam.getChoiceList();
        if(choiceList != null) {
            total = choiceList.size();
        }
        examVo.setTotal(total);

        if(withChoice) {
            Collections.sort(choiceList);
            examVo.setChoiceList(parseChoiceList(choiceList));
        }

        return examVo;
    }

    private ChoiceVo parseChoice(Choice choice) {
        ChoiceVo choiceVo = new ChoiceVo();
        choiceVo.setId(choice.getId());
        choiceVo.setTitle(choice.getTitle());
        choiceVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, choice.getCreateDt()));
        choiceVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, choice.getUpdateDt()));
        choiceVo.setSortDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, choice.getSortDt()));
        choiceVo.setRemarks(choice.getRemarks());
        choiceVo.setUserId(choice.getUser().getId());
        choiceVo.setUsername(choice.getUser().getName());
        choiceVo.setOptionA(choice.getOptionA());
        choiceVo.setOptionB(choice.getOptionB());
        choiceVo.setOptionC(choice.getOptionC());
        choiceVo.setOptionD(choice.getOptionD());
        choiceVo.setAnswer(choice.getAnswer());
        choiceVo.setNote(choice.getNote());
        choiceVo.setExamId(choice.getExam().getId());

        return choiceVo;
    }

    private List<ExamVo> parseExamList(List<Exam> examList, Boolean withChoice) {
        List<ExamVo> examVoList = null;
        if(examList != null && examList.size() > 0) {
            examVoList = new ArrayList<>();
            for(Exam exam : examList) {
                examVoList.add(parseExam(exam, withChoice));
            }
        }

        return examVoList;
    }

    private List<ChoiceVo> parseChoiceList(List<Choice> choiceList) {
        List<ChoiceVo> choiceVoList = null;
        if(choiceList != null && choiceList.size() > 0) {
            choiceVoList = new ArrayList<>();
            int no = 1;
            for(Choice choice : choiceList) {
                ChoiceVo choiceVo = parseChoice(choice);
                choiceVo.setNo(no++);
                choiceVoList.add(choiceVo);
            }
        }

        return choiceVoList;
    }


    @Override
    public ExamVo saveExam(ExamVo examVo, String userId, Boolean isAdmin) {
        Exam exam = null;
        String examId = examVo.getId();
        if(!StringUtil.isEmpty(examId)) {
            if(isAdmin){
                exam = examDao.findFirstById(examId);
            } else {
                exam = examDao.findFirstByIdAndUser_Id(examId, examVo.getUserId());
            }
        }

        if(exam == null) {
            exam = new Exam();
            exam.setCreateDt(new Date());
            User user = userDao.findFirstById(userId);
            if(user == null) {
                return null;
            }
            exam.setUser(user);
        }

        exam.setRemarks(examVo.getRemarks());
        exam.setTitle(examVo.getTitle());
        exam.setUpdateDt(new Date());

        examDao.save(exam);

        return parseExam(exam , false);
    }

    @Override
    public ExamVo getExam(String id, String userId, Boolean isAdmin) {
        Exam exam = null;
        if(isAdmin) {
            exam = examDao.findFirstById(id);
        } else {
            exam = examDao.findFirstByIdAndUser_Id(id, userId);
        }

        ExamVo examVo = null;
        if(exam != null) {
            examVo = parseExam(exam, false);
        }

        return examVo;
    }

    @Override
    public List<ExamVo> getExamList(ExamQueryVo queryVo, String userId, Boolean isAdmin) {
        List<Exam> examList = null;
        if(isAdmin) {
            examList = examDao.findAllByTitleLikeAndUser_NameLikeOrderByUpdateDtDesc(StringUtil.surround(queryVo.getTitle(),"%"), StringUtil.surround(queryVo.getUsername(),"%"));
        } else {
            examList = examDao.findAllByTitleLikeAndUser_IdOrderByUpdateDtDesc(StringUtil.surround(queryVo.getTitle(),"%"), userId);
        }
        return parseExamList(examList, false);
    }

    @Override
    public void deleteExam(String userId, Boolean isAdmin, String... ids) {
        if(isAdmin) {
            for(String id : ids) {
                examDao.deleteById(id);
            }
        } else {
            for(String id : ids) {
                examDao.deleteByIdAndUser_Id(id, userId);
            }
        }
    }

    @Override
    public ChoiceVo saveChoice(ChoiceVo choiceVo, String userId, Boolean isAdmin) {
        Choice choice = null;
        String choiceId = choiceVo.getId();
        if(!StringUtil.isEmpty(choiceId)) {
            if(isAdmin) {
                choice = choiceDao.findFirstById(choiceId);
            } else {
                choice = choiceDao.findFirstByIdAndUser_Id(choiceId, choiceVo.getUserId());
            }
        }

        if(choice == null) {
            choice = new Choice();
            choice.setCreateDt(new Date());
            choice.setSortDt(new Date());
            Exam exam = examDao.findFirstById(choiceVo.getExamId());
            User user = userDao.findFirstById(userId);
            if(exam == null || user == null) {
                return null;
            }

            choice.setExam(exam);
            choice.setUser(user);
        }

        choice.setAnswer(choiceVo.getAnswer());
        choice.setOptionA(choiceVo.getOptionA());
        choice.setOptionB(choiceVo.getOptionB());
        choice.setOptionC(choiceVo.getOptionC());
        choice.setOptionD(choiceVo.getOptionD());
        choice.setRemarks(choiceVo.getRemarks());
        choice.setUpdateDt(new Date());
        choice.setTitle(choiceVo.getTitle());
        choice.setNote(choiceVo.getNote());

        choiceDao.save(choice);

        return parseChoice(choice);
    }

    @Override
    public ChoiceVo getChoice(String id, String userId, Boolean isAdmin) {
        Choice choice = null;
        if(isAdmin) {
            choice = choiceDao.findFirstById(id);
        } else {
            choice = choiceDao.findFirstByIdAndUser_Id(id ,userId);
        }

        ChoiceVo choiceVo = null;
        if(choice != null) {
            choiceVo = parseChoice(choice);
        }

        return choiceVo;
    }

    @Override
    public List<ChoiceVo> getChoiceList(ChoiceQueryVo queryVo, String examId, String userId, Boolean isAdmin) {
        List<Choice> choiceList = null;
        if(isAdmin) {
            choiceList = choiceDao.findAllByTitleLikeAndExam_IdAndUser_NameLikeOrderByUpdateDtDesc(
                    StringUtil.surround(queryVo.getTitle(), "%"),
                    examId,
                    StringUtil.surround(queryVo.getUsername(), "%")
            );
        } else {
            choiceList = choiceDao.findAllByTitleLikeAndExam_IdAndUser_IdOrderByUpdateDtDesc(
                    StringUtil.surround(queryVo.getTitle(), "%"),
                    examId,
                    userId
            );
        }

        return parseChoiceList(choiceList);
    }

    @Override
    public void deleteChoice(String userId, Boolean isAdmin, String... ids) {
        if(isAdmin) {
            for(String id : ids) {
                choiceDao.deleteById(id);
            }
        } else {
            for(String id : ids) {
                choiceDao.deleteByIdAndUser_Id(id, userId);
            }
        }
    }

    @Override
    public void sortChoice(String id, String userId, Boolean isAdmin) {
        Choice choice = null;
        if(isAdmin) {
            choice = choiceDao.findFirstById(id);
        } else {
            choice = choiceDao.findFirstByIdAndUser_Id(id, userId);
        }

        if(choice != null) {
            choice.setSortDt(new Date());
            choiceDao.save(choice);
        }
    }

    @Override
    public List<ExamVo> getExamList() {
        return parseExamList(examDao.findAllByIdIsNotNullOrderByCreateDtDesc(), true);
    }

    @Override
    public ExamVo getExam(String id, Boolean withChoice) {
        return parseExam(examDao.findFirstById(id), withChoice);
    }

}
