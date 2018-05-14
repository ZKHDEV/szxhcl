package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.ResourceClassDao;
import cn.edu.gdou.szxhcl.dao.ResourceDao;
import cn.edu.gdou.szxhcl.dao.UserDao;
import cn.edu.gdou.szxhcl.model.Resource;
import cn.edu.gdou.szxhcl.model.ResourceClass;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.resource.ResClassVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResQueryVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResourceVo;
import cn.edu.gdou.szxhcl.service.ResourceService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import cn.edu.gdou.szxhcl.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ResourceClassDao resourceClassDao;
    @Autowired
    private UserDao userDao;

    private ResourceVo parseResource(Resource resource){
        ResourceVo resourceVo = new ResourceVo();
        resourceVo.setId(resource.getId());
        resourceVo.setFileName(resource.getFileName());
        resourceVo.setType(resource.getType());
        resourceVo.setUrl(resource.getUrl());
        resourceVo.setDownNum(resource.getDownNum());
        resourceVo.setSize(resource.getSize());
        resourceVo.setUploader(resource.getUploader());
        resourceVo.setUploadDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, resource.getUploadDt()));
        resourceVo.setRemarks(resource.getRemarks());
        resourceVo.setClassId(resource.getResClass().getId());
        resourceVo.setClassName(resource.getResClass().getClassName());

        return resourceVo;
    }

    private List<ResourceVo> parseResourceList(List<Resource> resourceList) {
        List<ResourceVo> resourceVoList = null;
        if(resourceList != null && resourceList.size() > 0){
            resourceVoList = new ArrayList<>();
            ResourceVo resourceVo = null;
            for(Resource resource : resourceList){
                resourceVo = parseResource(resource);
                resourceVoList.add(resourceVo);
            }
        }

        return resourceVoList;
    }

    private ResClassVo parseResourceClass(ResourceClass resourceClass){
        ResClassVo resClassVo = new ResClassVo();
        resClassVo.setId(resourceClass.getId());
        resClassVo.setClassName(resourceClass.getClassName());

        List<ResourceVo> resourceVoList = null;
        List<Resource> resourceList = resourceClass.getResList();
        if(resourceList != null && resourceList.size() > 0) {
            resourceVoList = new ArrayList<>();
            for(Resource resource : resourceList) {
                resourceVoList.add(parseResource(resource));
            }
            Collections.sort(resourceVoList);
            resClassVo.setResList(resourceVoList);
        }

        return resClassVo;
    }

    private List<ResClassVo> parseResClassList(List<ResourceClass> resourceClassList) {
        List<ResClassVo> resourceClassVoList = null;

        if(resourceClassList != null && resourceClassList.size() > 0){
            resourceClassVoList = new ArrayList<>();
            for(ResourceClass resourceClass : resourceClassList){
                resourceClassVoList.add(parseResourceClass(resourceClass));
            }
        }

        return resourceClassVoList;
    }
    
    @Override
    public List<ResourceVo> getResourceList(ResQueryVo queryVo) {
        List<User> userList1 = userDao.findAll();
        List<Resource> resourceList = resourceDao.findAll(new Specification<Resource>(){
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();

                if(!StringUtil.isEmpty(queryVo.getClassId())){
                    predicates.add(criteriaBuilder.equal(root.join("resClass").get("id"), queryVo.getClassId()));
                }
                if(!StringUtil.isEmpty(queryVo.getType())){
                    predicates.add(criteriaBuilder.equal(root.get("type"), queryVo.getType()));
                }

                predicates.add(criteriaBuilder.like(root.get("fileName"), StringUtil.surround(queryVo.getFileName(), "%")));
                predicates.add(criteriaBuilder.like(root.get("uploader"), StringUtil.surround(queryVo.getUploader(), "%")));

                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        });

        return parseResourceList(resourceList);
    }

    @Override
    public ResourceVo getResource(String id) {
        Resource resource = resourceDao.findFirstById(id);

        ResourceVo resourceVo = null;
        if(resource != null){
            resourceVo = parseResource(resource);
        }

        return resourceVo;
    }

    @Override
    public ResourceVo saveResource(ResourceVo resourceVo) {
        Resource resource = null;
        String resourceId = resourceVo.getId();
        if(!StringUtils.isEmpty(resourceId)){
            resource = resourceDao.findFirstById(resourceId);
        }

        if(resource == null){
            resource = new Resource();
            resource.setDownNum(0);
            resource.setUploadDt(new Date());
        }

        ResourceClass resourceClass = resourceClassDao.findFirstById(resourceVo.getClassId());

        resource.setFileName(resourceVo.getFileName());
        resource.setType(resourceVo.getType());
        resource.setUrl(resourceVo.getUrl());
        resource.setSize(resourceVo.getSize());
        resource.setUploader(resourceVo.getUploader());
        resource.setRemarks(resourceVo.getRemarks());
        resource.setResClass(resourceClass);

        resourceDao.save(resource);

        return parseResource(resource);
    }

    @Override
    public void deleteResource(String... ids) {
        for(String id : ids){
            resourceDao.deleteById(id);
        }
    }

    @Override
    public void addDownNum(String id) {
        Resource resource = resourceDao.findFirstById(id);
        if(resource != null) {
            resource.setDownNum(resource.getDownNum() + 1);
            resourceDao.save(resource);
        }
    }

    @Override
    public List<ResClassVo> getResClassList() {
        List<ResourceClass> resClassList = resourceClassDao.findAll();
        return parseResClassList(resClassList);
    }

    @Override
    public ResClassVo getResClass(String id) {
        ResourceClass resourceClass = resourceClassDao.findFirstById(id);
        ResClassVo resClassVo = null;

        if(resourceClass != null) {
            resClassVo = parseResourceClass(resourceClass);
        }

        return resClassVo;
    }

    @Override
    public Boolean isResClassNameExisted(String id, String className) {
        return resourceClassDao.countAllByClassNameAndIdNot(className, id) > 0;
    }

    @Override
    public ResClassVo saveResClass(ResClassVo resClassVo) {
        ResourceClass resourceClass = null;
        String resourceClassId = resClassVo.getId();
        if(!StringUtils.isEmpty(resourceClassId)){
            resourceClass = resourceClassDao.findFirstById(resourceClassId);
        }

        if(resourceClass == null){
            resourceClass = new ResourceClass();
        }

        resourceClass.setClassName(resClassVo.getClassName());

        resourceClassDao.save(resourceClass);

        return parseResourceClass(resourceClass);
    }

    @Override
    public void deleteResClass(String... ids) {
        for(String id : ids){
            resourceClassDao.deleteById(id);
        }
    }
}
