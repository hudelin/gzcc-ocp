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
import java.util.Date;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = -3843608801898748229L;

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

    @Column(columnDefinition = "text")
    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Long praise = Long.valueOf(0);

    @Getter
    @Setter
    private String praiseUserIdString = "";

    @Getter
    @Setter
    private Boolean deleted = false;

    @Getter
    @Setter
    private Long resourceId;


    @ManyToOne
    @Getter
    @Setter
    private User user;

}
