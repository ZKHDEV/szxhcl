package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.CommentDao;
import cn.edu.gdou.szxhcl.model.Comment;
import cn.edu.gdou.szxhcl.model.vo.comment.CommentVo;
import cn.edu.gdou.szxhcl.service.CommentService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentDao commentDao;
    
    private CommentVo parseComment(Comment comment) {
        CommentVo commentVo = new CommentVo();
        commentVo.setId(comment.getId());
        commentVo.setAuthor(comment.getAuthor());
        commentVo.setContent(comment.getContent());
        commentVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, comment.getCreateDt()));
        commentVo.setTitle(comment.getTitle());
        commentVo.setTop(comment.getTop());
        
        return commentVo;
    }
    
    @Override
    public List<CommentVo> getCommentList() {
        List<Comment> CommentList = commentDao.findAll();
        List<CommentVo> CommentVoList = null;
        if(CommentList != null && CommentList.size() > 0){
            CommentVoList = new ArrayList<>();
            CommentVo CommentVo = null;
            for(Comment Comment : CommentList){
                CommentVo = parseComment(Comment);
                CommentVoList.add(CommentVo);
            }
        }

        return CommentVoList;
    }

    @Override
    public void deleteComment(String... ids) {
        for(String id : ids){
            commentDao.deleteById(id);
        }
    }
}
