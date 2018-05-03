package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.vo.link.LinkVo;

import java.util.List;

public interface LinkService {
    List<LinkVo> getLinkList();
    LinkVo getLink(String id);
    LinkVo save(LinkVo linkVo);
    void delete(String... ids);
}
