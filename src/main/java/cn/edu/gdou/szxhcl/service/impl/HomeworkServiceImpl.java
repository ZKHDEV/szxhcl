package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.HomeworkDao;
import cn.edu.gdou.szxhcl.dao.HomeworkSubmitDao;
import cn.edu.gdou.szxhcl.dao.UserDao;
import cn.edu.gdou.szxhcl.model.Homework;
import cn.edu.gdou.szxhcl.model.HomeworkSubmit;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkQueryVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkSubmitVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkVo;
import cn.edu.gdou.szxhcl.model.vo.user.UserVo;
import cn.edu.gdou.szxhcl.service.HomeworkService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import cn.edu.gdou.szxhcl.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private HomeworkSubmitDao homeworkSubmitDao;

    @Autowired
    private UserDao userDao;

    private UserVo parseUser(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setName(user.getName());
        userVo.setClasses(user.getClasses());
        userVo.setCollege(user.getCollege());

        return userVo;
    }

    private HomeworkSubmitVo parseHomeworkSubmit(HomeworkSubmit homeworkSubmit) {
        HomeworkSubmitVo homeworkSubmitVo = new HomeworkSubmitVo();
        homeworkSubmitVo.setId(homeworkSubmit.getId());
        homeworkSubmitVo.setUrl(homeworkSubmit.getUrl());
        homeworkSubmitVo.setScore(homeworkSubmit.getScore());
        homeworkSubmitVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, homeworkSubmit.getCreateDt()));
        homeworkSubmitVo.setHomeworkId(homeworkSubmit.getHomework().getId());
        homeworkSubmitVo.setHomeworkTitle(homeworkSubmit.getHomework().getTitle());
        homeworkSubmitVo.setUser(parseUser(homeworkSubmit.getUser()));
        return homeworkSubmitVo;
    }

    private HomeworkVo parseHomework(Homework homework, Boolean withContent, Boolean withSubmit) {
        HomeworkVo homeworkVo = new HomeworkVo();
        homeworkVo.setId(homework.getId());
        homeworkVo.setTitle(homework.getTitle());
        homeworkVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, homework.getCreateDt()));
        homeworkVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, homework.getUpdateDt()));
        homeworkVo.setEndDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, homework.getEndDt()));
        homeworkVo.setTimeout(homework.getEndDt().getTime() < System.currentTimeMillis());
        homeworkVo.setSubmitNum(0);
        homeworkVo.setUser(parseUser(homework.getUser()));

        if(withContent) {
            homeworkVo.setContent(homework.getContent());
        }

        List<HomeworkSubmit> homeworkSubmitList = homework.getHomeworkSubmitList();
        if(homeworkSubmitList != null && homeworkSubmitList.size() > 0) {
            homeworkVo.setSubmitNum(homeworkSubmitList.size());

            if(withSubmit) {
                List<HomeworkSubmitVo> homeworkSubmitVoList = null;
                homeworkSubmitVoList = new ArrayList<>();
                for(HomeworkSubmit homeworkSubmit : homeworkSubmitList) {
                    homeworkSubmitVoList.add(parseHomeworkSubmit(homeworkSubmit));
                }
                homeworkVo.setHomeworkSubmitList(homeworkSubmitVoList);
            }
        }

        return homeworkVo;
    }

    private List<HomeworkVo> parseHomeworkList(List<Homework> homeworkList, Boolean withContent, Boolean withSubmit) {
        List<HomeworkVo> homeworkVoList = null;
        if(homeworkList != null && homeworkList.size() > 0) {
            homeworkVoList = new ArrayList<>();
            for(Homework homework : homeworkList) {
                homeworkVoList.add(parseHomework(homework,withContent,withSubmit));
            }
        }

        return homeworkVoList;
    }

    private List<HomeworkSubmitVo> parseHomeworkSubmitList(List<HomeworkSubmit> homeworkSubmitList) {
        List<HomeworkSubmitVo> homeworkSubmitVoList = null;
        if(homeworkSubmitList != null && homeworkSubmitList.size() > 0) {
            homeworkSubmitVoList = new ArrayList<>();
            for(HomeworkSubmit homeworkSubmit : homeworkSubmitList) {
                homeworkSubmitVoList.add(parseHomeworkSubmit(homeworkSubmit));
            }
        }

        return homeworkSubmitVoList;
    }

    @Override
    public List<HomeworkVo> getHomeworkListByUser(HomeworkQueryVo queryVo, String userId) {

        List<Homework> homeworkList = homeworkDao.findAll(new Specification<Homework>(){
            @Override
            public Predicate toPredicate(Root<Homework> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();

                String role = userDao.findFirstById(userId).getRole();
                if("ROLE_TEACHER".equals(role)) {
                    predicates.add(criteriaBuilder.equal(root.join("user").get("id"), userId));
                }

                Byte timeout = queryVo.getTimeout();
                if(timeout != null){
                    if(timeout.equals(1)) {
                        predicates.add(criteriaBuilder.lessThan(root.get("endDt"), new Date()));
                    } else {
                        predicates.add(criteriaBuilder.greaterThan(root.get("endDt"), new Date()));
                    }

                }

                predicates.add(criteriaBuilder.like(root.get("title"), StringUtil.surround(queryVo.getTitle(), "%")));
                predicates.add(criteriaBuilder.like(root.join("user").get("name"), StringUtil.surround(queryVo.getTeacher(), "%")));
                predicates.add(criteriaBuilder.isFalse(root.get("delFlag")));

                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        });

        return parseHomeworkList(homeworkList,false,false);
    }

    @Override
    public HomeworkVo getHomework(String id, Boolean withContent, Boolean withSubmit) {
        Homework homework = homeworkDao.findFirstById(id);

        HomeworkVo homeworkVo = null;
        if(homework != null) {
            homeworkVo = parseHomework(homework, withContent, withSubmit);
        }
        
        return homeworkVo;
    }

    @Override
    public HomeworkVo saveHomework(HomeworkVo homeworkVo, String userId) {
        Homework homework = null;
        String homeworkId = homeworkVo.getId();
        if(!StringUtils.isEmpty(homeworkId)){
            homework = homeworkDao.findFirstById(homeworkId);
        }

        if(homework == null){
            homework = new Homework();
            homework.setCreateDt(new Date());
            homework.setDelFlag(false);


            User user = userDao.findFirstById(userId);
            homework.setUser(user);
        }

        homework.setTitle(homeworkVo.getTitle());
        homework.setContent(homeworkVo.getContent());
        homework.setUpdateDt(new Date());
        homework.setEndDt(DateTimeUtil.stringToDate(DateTimeUtil.YMDHMS, homeworkVo.getEndDt()));

        homeworkDao.save(homework);

        return parseHomework(homework,true,false);
    }

    @Override
    public void deleteHomework(String... ids) {
        for(String id : ids){
            Homework homework = homeworkDao.findFirstById(id);
            if(homework != null) {
                homework.setDelFlag(true);
                homework.setUpdateDt(new Date());
                homeworkDao.save(homework);
            }
        }
    }

    @Override
    public List<HomeworkSubmitVo> getSubmitList(String id) {
        List<HomeworkSubmit> homeworkSubmitList = homeworkSubmitDao.findAllByHomework_IdAndDelFlagIsFalseOrderByCreateDtDesc(id);
        return parseHomeworkSubmitList(homeworkSubmitList);
    }

    @Override
    public void deleteHomeworkSubmit(String... ids) {
        for(String id : ids){
            HomeworkSubmit homeworkSubmit = homeworkSubmitDao.findFirstById(id);
            if(homeworkSubmit != null) {
                homeworkSubmit.setDelFlag(true);
                homeworkSubmitDao.save(homeworkSubmit);
            }
        }
    }

    @Override
    public void score(String submitId, Integer score) {
        HomeworkSubmit homeworkSubmit = homeworkSubmitDao.findFirstById(submitId);
        homeworkSubmit.setScore(score);
        homeworkSubmitDao.save(homeworkSubmit);
    }

}
