package cn.edu.gdou.szxhcl.dao;

import cn.edu.gdou.szxhcl.model.Resource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ResourceDao extends CrudRepository<Resource,String>, JpaSpecificationExecutor<Resource> {
    Resource findFirstById(String id);
}
