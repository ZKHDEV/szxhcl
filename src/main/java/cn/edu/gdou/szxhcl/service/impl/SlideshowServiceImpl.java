package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.SlideshowDao;
import cn.edu.gdou.szxhcl.model.Slideshow;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import cn.edu.gdou.szxhcl.service.SlideshowService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SlideshowServiceImpl implements SlideshowService {
    @Autowired
    private SlideshowDao slideshowDao;

    private SlideshowVo parseSlideshow(Slideshow slideshow){
        SlideshowVo slideshowVo = new SlideshowVo();
        slideshowVo.setId(slideshow.getId());
        slideshowVo.setTitle(slideshow.getTitle());
        slideshowVo.setShowed(slideshow.getShowed());
        slideshowVo.setUrl(slideshow.getUrl());
        slideshowVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, slideshow.getCreateDt()));
        slideshowVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, slideshow.getUpdateDt()));
        slideshowVo.setSortDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, slideshow.getSortDt()));

        return slideshowVo;
    }

    @Override
    public List<SlideshowVo> getList() {
        List<Slideshow> slideshowList = slideshowDao.findAll();
        List<SlideshowVo> slideshowVoList = null;
        if(slideshowList != null && slideshowList.size() > 0){
            slideshowVoList = new ArrayList<>();
            SlideshowVo slideshowVo = null;
            for(Slideshow slideshow : slideshowList){
                slideshowVo = parseSlideshow(slideshow);
                slideshowVoList.add(slideshowVo);
            }
        }

        return slideshowVoList;
    }

    @Override
    public SlideshowVo getOne(String id) {
        Slideshow slideshow = slideshowDao.findFirstById(id);

        SlideshowVo slideshowVo = null;
        if(slideshow != null){
            slideshowVo = parseSlideshow(slideshow);
        }

        return slideshowVo;
    }

    @Override
    public SlideshowVo save(SlideshowVo slideshowVo) {
        Slideshow slideshow = null;
        String id = slideshowVo.getId();
        if(!StringUtils.isEmpty(id)){
            slideshow = slideshowDao.findFirstById(id);
        }

        if(slideshow == null){
            slideshow = new Slideshow();
            slideshow.setCreateDt(new Date());
            slideshow.setSortDt(new Date());
            slideshow.setShowed(true);
        }

        slideshow.setTitle(slideshowVo.getTitle());
        slideshow.setUpdateDt(new Date());
        slideshow.setUrl(slideshowVo.getUrl());

        slideshowDao.save(slideshow);

        return parseSlideshow(slideshow);
    }

    @Override
    public void delete(String... ids) {
        for(String id : ids){
            slideshowDao.deleteById(id);
        }
    }

    @Override
    public void sort(String id) {
        Slideshow slideshow = slideshowDao.findFirstById(id);
        slideshow.setSortDt(new Date());
        slideshowDao.save(slideshow);
    }

    @Override
    public void changShow(String id) {
        Slideshow slideshow = slideshowDao.findFirstById(id);
        slideshow.setShowed(!slideshow.getShowed());
        slideshowDao.save(slideshow);
    }
}
