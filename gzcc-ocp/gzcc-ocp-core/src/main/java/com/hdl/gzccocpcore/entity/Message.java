package com.hdl.gzccocpcore.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {


    private static final long serialVersionUID = 20393427563199288L;
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
    private Long formUserId;
    @Getter
    @Setter
    private Long toUserId;

    @Getter
    @Setter
    private Long groupId;


    @Column(columnDefinition = "text")
    @Getter
    @Setter
    private String content;

    @Column(columnDefinition = "text")
    @Getter
    @Setter
    private String remark;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Boolean mine = false;

    @Getter
    @Setter
    private Boolean system = false;

    @Getter
    @Setter
    private Boolean readStatus = false;

    @Getter
    @Setter
    private Boolean deleted = false;

    @Getter
    @Setter
    private String status;

    @Transient
    @Getter
    @Setter
    private User user;


}
