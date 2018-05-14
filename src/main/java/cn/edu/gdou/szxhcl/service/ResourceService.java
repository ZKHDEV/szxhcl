package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.vo.resource.ResClassVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResQueryVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResourceVo;

import java.util.List;

public interface ResourceService {
    List<ResourceVo> getResourceList(ResQueryVo queryVo);
    ResourceVo getResource(String id);
    ResourceVo saveResource(ResourceVo resourceVo);
    void deleteResource(String... ids);
    void addDownNum(String id);

    List<ResClassVo> getResClassList();
    ResClassVo getResClass(String id);
    Boolean isResClassNameExisted(String id, String className);
    ResClassVo saveResClass(ResClassVo resClassVo);
    void deleteResClass(String... ids);
}
