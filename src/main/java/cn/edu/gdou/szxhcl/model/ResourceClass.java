package cn.edu.gdou.szxhcl.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resource_class")
public class ResourceClass {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, length = 32)
    private String id;
    @Column(nullable = false)
    private String className;
    @OneToMany(cascade={CascadeType.REMOVE},mappedBy = "resClass",fetch = FetchType.LAZY)
    private List<Resource> resList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Resource> getResList() {
        return resList;
    }

    public void setResList(List<Resource> resList) {
        this.resList = resList;
    }
}
