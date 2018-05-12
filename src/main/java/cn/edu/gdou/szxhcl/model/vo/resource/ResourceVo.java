package cn.edu.gdou.szxhcl.model.vo.resource;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class ResourceVo implements Comparable {
    private String id;
    @NotEmpty(message = "必填")
    private String fileName;
    @NotEmpty(message = "必填")
    private String type;
    @NotEmpty(message = "必填")
    private String url;
    private Integer downNum;
    private String size;
    @NotEmpty(message = "必填")
    private String uploader;
    private String uploadDt;
    private String remarks;
    @NotEmpty(message = "必填")
    private String classId;
    private String className;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDownNum() {
        return downNum;
    }

    public void setDownNum(Integer downNum) {
        this.downNum = downNum;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploadDt() {
        return uploadDt;
    }

    public void setUploadDt(String uploadDt) {
        this.uploadDt = uploadDt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int compareTo(Object o) {
        ResourceVo resourceVo = (ResourceVo)o;
        return resourceVo.getUploadDt().compareTo(this.getUploadDt());
    }
}
