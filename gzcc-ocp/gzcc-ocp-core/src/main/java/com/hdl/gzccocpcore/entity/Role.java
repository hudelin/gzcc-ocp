package com.hdl.gzccocpcore.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {


    private static final long serialVersionUID = 9033363582548011826L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置自动增长
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    @Getter
    @Setter
    private Date createTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @Getter
    @Setter
    private Date lastModifiedTime;


    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @ManyToMany(mappedBy = "roleList")
    @JsonIgnore
    @Getter
    @Setter
    private List<User> userList = new ArrayList<>();

    @Getter
    @Setter
    private Boolean deleted = false;
}
