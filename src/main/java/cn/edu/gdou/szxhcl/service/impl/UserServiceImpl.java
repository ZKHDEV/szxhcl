package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.UserDao;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.user.UserQueryVo;
import cn.edu.gdou.szxhcl.model.vo.user.UserVo;
import cn.edu.gdou.szxhcl.service.UserService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import cn.edu.gdou.szxhcl.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private UserVo parseUser(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setName(user.getName());
        userVo.setUsername(user.getUsername());
        userVo.setEmail(user.getEmail());
        userVo.setPhone(user.getPhone());
        userVo.setRole(user.getRole());
        userVo.setClasses(user.getClasses());
        userVo.setCollege(user.getCollege());
        userVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, user.getCreateDt()));
        userVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, user.getUpdateDt()));

        return userVo;
    }

    private List<UserVo> parseUserList(List<User> userList) {
        List<UserVo> userVoList = null;
        if(userList != null && userList.size() > 0) {
            userVoList = new ArrayList<>();
            UserVo userVo = null;
            for(User user : userList) {
                userVo = parseUser(user);
                userVoList.add(userVo);
            }
        }

        return userVoList;
    }

    @Override
    public List<UserVo> getUserList(UserQueryVo queryVo) {

        List<User> userList = userDao.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();

                String role = queryVo.getRole();
                if(!StringUtil.isEmpty(role)){
                    predicates.add(criteriaBuilder.equal(root.get("role"), role));
                }

                String college = queryVo.getCollege();
                if(!StringUtil.isEmpty(college)) {
                    predicates.add(criteriaBuilder.like(root.get("college"), StringUtil.surround(queryVo.getCollege(), "%")));
                }

                String classes = queryVo.getClasses();
                if(!StringUtil.isEmpty(classes)) {
                    predicates.add(criteriaBuilder.like(root.get("classes"), StringUtil.surround(queryVo.getClasses(), "%")));
                }

                predicates.add(criteriaBuilder.like(root.get("username"), StringUtil.surround(queryVo.getUsername(), "%")));
                predicates.add(criteriaBuilder.like(root.get("name"), StringUtil.surround(queryVo.getName(), "%")));

                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        });

        return parseUserList(userList);
    }

    @Override
    public UserVo getUser(String id) {
        User user = userDao.findFirstById(id);
        return parseUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userDao.findFirstByUsername(username);
        return user;
    }

    @Override
    public UserVo getUserVoByUsername(String username) {
        User user = userDao.findFirstByUsername(username);
        return parseUser(user);
    }

    @Override
    public Boolean isUsernameExisted(String id, String username) {
        if(StringUtils.isEmpty(id)) {
            return userDao.countAllByUsername(username) > 0;
        } else {
            return userDao.countAllByUsernameAndIdNot(username,id) > 0;
        }
    }

    @Override
    public UserVo save(UserVo userVo) {
        User user = null;
        String userId = userVo.getId();
        if(!StringUtils.isEmpty(userId)) {
            user = userDao.findFirstById(userId);
        }

        if(user == null) {
            user = new User();
            user.setCreateDt(new Date());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(userVo.getUsername()));
        }

        user.setName(userVo.getName());
        user.setEmail(userVo.getEmail());
        user.setPhone(userVo.getPhone());
        user.setUpdateDt(new Date());
        user.setRole(userVo.getRole());
        user.setClasses(userVo.getClasses());
        user.setCollege(userVo.getCollege());
        user.setUsername(userVo.getUsername());

        userDao.save(user);

        return parseUser(user);
    }

    @Override
    public void changePsd(String id, String password) {
        User user = userDao.findFirstById(id);
        if(user != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(password));
            user.setUpdateDt(new Date());
            userDao.save(user);
        }
    }

    @Override
    public void delete(String... ids) {
        for(String id : ids){
            userDao.deleteById(id);
        }
    }
}
