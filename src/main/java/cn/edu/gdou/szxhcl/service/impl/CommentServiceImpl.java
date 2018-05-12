package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.CommentDao;
import cn.edu.gdou.szxhcl.model.Comment;
import cn.edu.gdou.szxhcl.model.vo.comment.CommentVo;
import cn.edu.gdou.szxhcl.service.CommentService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentDao commentDao;
    
    private CommentVo parseComment(Comment comment, Boolean withChiComm) {
        CommentVo commentVo = new CommentVo();
        commentVo.setId(comment.getId());
        commentVo.setAuthor(comment.getAuthor());
        commentVo.setEmail(comment.getEmail());
        commentVo.setContent(comment.getContent());
        commentVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, comment.getCreateDt()));
        commentVo.setTitle(comment.getTitle());
        commentVo.setTop(comment.getTop());

        if(commentVo.getTop()) {
            Integer replyNum = 0;
            List<Comment> commentList = comment.getCommentList();
            if(commentList != null && commentList.size() > 0) {
                replyNum = commentList.size();
                if(withChiComm) {
                    List<CommentVo> commentVoList = new ArrayList<>();
                    for(Comment comm : commentList) {
                        commentVoList.add(parseComment(comm,false));
                    }
                    Collections.sort(commentVoList);
                    commentVo.setChiCommList(commentVoList);
                }
            }
            commentVo.setReplyNum(replyNum);

            Comment lastComment = commentDao.findFirstByParComment_IdOrderByCreateDtDesc(commentVo.getId());
            if(lastComment != null) {
                commentVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, lastComment.getCreateDt()));
            }
        }
        
        return commentVo;
    }

    private List<CommentVo> parseCommentList(List<Comment> commentList, Boolean withChiComm) {
        List<CommentVo> commentVoList = null;
        if(commentList != null && commentList.size() > 0){
            commentVoList = new ArrayList<>();
            CommentVo commentVo = null;
            for(Comment comment : commentList){
                commentVo = parseComment(comment,withChiComm);
                commentVoList.add(commentVo);
            }
        }

        return commentVoList;
    }

    @Override
    public void deleteComment(String... ids) {
        for(String id : ids){
            commentDao.deleteById(id);
        }
    }

    @Override
    public List<CommentVo> getTopCommentList(Boolean withChiComm) {
        List<Comment> commentList = commentDao.findAllByTopIsTrueOrderByCreateDtDesc();
        return parseCommentList(commentList,withChiComm);
    }

    @Override
    public List<CommentVo> getReplyList(String id) {
        List<Comment> commentList = commentDao.findAllByParComment_IdOrderByCreateDt(id);
        return parseCommentList(commentList,false);
    }

    @Override
    public CommentVo getComment(String id) {
        Comment comment = commentDao.findFirstById(id);
        return parseComment(comment,true);
    }

    @Override
    public CommentVo save(String parCommId, CommentVo commentVo) {


        Comment comment =  new Comment();

        comment.setTitle(commentVo.getTitle());
        comment.setAuthor(commentVo.getAuthor());
        comment.setContent(commentVo.getContent());
        comment.setCreateDt(new Date());
        comment.setEmail(commentVo.getEmail());
        comment.setTop(commentVo.getTop());

        Comment parComment = commentDao.findFirstById(parCommId);
        if(parComment != null) {
            comment.setParComment(parComment);
        }

        commentDao.save(comment);

        return parseComment(comment,false);
    }
}
