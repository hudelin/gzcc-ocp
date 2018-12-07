package com.hdl.gzccocpcore.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
public class User  implements Serializable{

    private static final long serialVersionUID = 6911727722566303411L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置自动增长
    @Column(name = "id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    protected Date createTime;

    @CreatedBy
    protected Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    protected Date lastModifiedDate;

    @LastModifiedBy
    protected Long lastModifiedBy;

    private String username;

    private String password;

    private String email;

    private String gender;

    @ManyToMany(mappedBy = "userList" ,fetch=FetchType.EAGER )
    @Cascade(value ={ org.hibernate.annotations.CascadeType.PERSIST,org.hibernate.annotations.CascadeType.MERGE})
    private List<Role> roleList;

}
