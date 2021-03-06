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
import java.util.Date;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Reply implements Serializable {

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


    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Note note;

    @Getter
    @Setter
    private Boolean accepted = false;

    @ManyToOne
    @Getter
    @Setter
    private User user;

}
