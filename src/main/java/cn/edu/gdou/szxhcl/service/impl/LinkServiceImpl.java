package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.LinkDao;
import cn.edu.gdou.szxhcl.model.Link;
import cn.edu.gdou.szxhcl.model.vo.link.LinkVo;
import cn.edu.gdou.szxhcl.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkDao linkDao;

    private LinkVo parseLink(Link link) {
        LinkVo linkVo = new LinkVo();
        linkVo.setId(link.getId());
        linkVo.setTitle(link.getTitle());
        linkVo.setUrl(link.getUrl());
        return linkVo;
    }

    @Override
    public List<LinkVo> getLinkList() {
        List<Link> linkList = linkDao.findAllByIdIsNotNullOrderByTitle();
        List<LinkVo> linkVoList = null;
        if(linkList != null && linkList.size() > 0){
            linkVoList = new ArrayList<>();
            LinkVo linkVo = null;
            for (Link link : linkList){
                linkVoList.add(parseLink(link));
            }
        }
        
        return linkVoList;
    }

    @Override
    public LinkVo getLink(String id) {
        Link link = linkDao.findFirstById(id);
        
        LinkVo linkVo = null;
        if(link != null){
            linkVo = parseLink(link);
        }
        
        return linkVo;
    }

    @Override
    public LinkVo save(LinkVo linkVo) {
        Link link = null;
        String linkId = linkVo.getId();
        if(!StringUtils.isEmpty(linkId)){
            link = linkDao.findFirstById(linkId);
        }

        if(link == null){
            link = new Link();
        }


        link.setTitle(linkVo.getTitle());
        link.setUrl(linkVo.getUrl());

        linkDao.save(link);

        return parseLink(link);
    }

    @Override
    public void delete(String... ids) {
        for(String id : ids){
            linkDao.deleteById(id);
        }
    }
}
