//package com.hdl.gzccocpcore.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
//@MappedSuperclass
//@Data
//@EqualsAndHashCode(callSuper = false)
//@EntityListeners(AuditingEntityListener.class)
//public abstract class BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置自动增长
//    @Column(name = "id")
//    private Long id;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @CreatedDate
//    protected Date createTime;
//
//    @CreatedBy
//    protected Long createBy;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @LastModifiedDate
//    protected Date lastModifiedTime;
//
//    @LastModifiedBy
//    protected Long lastModifiedBy;
//
//}
