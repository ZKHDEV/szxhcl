package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Slideshow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SlideshowDao extends CrudRepository<Slideshow,String> {
    List<Slideshow> findAll();
    List<Slideshow> findAllByShowedIsTrueOrderBySortDtDesc();
    Slideshow findFirstById(String id);
}
