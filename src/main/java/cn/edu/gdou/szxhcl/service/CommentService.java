package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.vo.comment.CommentVo;

import java.util.List;

public interface CommentService {
    void deleteComment(String... ids);
    List<CommentVo> getTopCommentList(Boolean withChiComm);
    CommentVo getComment(String id);
    CommentVo save(String parCommId, CommentVo commentVo);
    List<CommentVo> getReplyList(String id);
}
