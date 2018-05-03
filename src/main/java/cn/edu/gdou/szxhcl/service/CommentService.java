package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.vo.comment.CommentVo;

import java.util.List;

public interface CommentService {
    List<CommentVo> getCommentList();
    void deleteComment(String... ids);
}
