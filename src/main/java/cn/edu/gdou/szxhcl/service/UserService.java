package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.user.UserQueryVo;
import cn.edu.gdou.szxhcl.model.vo.user.UserVo;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {
    List<UserVo> getUserList(UserQueryVo queryVo);
    UserVo getUser(String id);
    User getUserByUsername(String username);
    UserVo getUserVoByUsername(String username);
    Boolean isUsernameExisted(String id, String username);
    UserVo save(UserVo userVo);
    void changePsd(String id, String password);
    void delete(String... ids);
}
