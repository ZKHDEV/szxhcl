package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.ResourceClass;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResourceClassDao extends CrudRepository<ResourceClass,String>, JpaSpecificationExecutor<ResourceClass> {
    ResourceClass findFirstById(String id);
    List<ResourceClass> findAll();
    Long countAllByClassNameAndIdNot(String className, String id);
}
