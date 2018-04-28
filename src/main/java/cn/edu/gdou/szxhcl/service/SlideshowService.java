package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.Slideshow;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;

import java.util.List;

public interface SlideshowService {
    List<SlideshowVo> getList();
    SlideshowVo getOne(String id);
    SlideshowVo save(SlideshowVo slideshowVo);
    void delete(String... ids);
    void sort(String id);
    void changShow(String id);
}
