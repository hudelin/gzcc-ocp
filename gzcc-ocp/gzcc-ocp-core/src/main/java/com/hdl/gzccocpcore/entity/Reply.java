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
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Reply  implements Serializable {

    private static final long serialVersionUID = -3843608801898748229L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置自动增长
    @Column(name = "id")
    @Getter@Setter
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    @Getter@Setter
    protected Date createTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @Getter@Setter
    protected Date lastModifiedTime;

    @LastModifiedBy
    @Getter@Setter
    protected Long lastModifiedBy;


    @Column(columnDefinition="text")
    @Getter@Setter
    private String content;

    @Getter@Setter
    private Integer praise=0;

    @Getter@Setter
    private Boolean isDelete=false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter@Setter
    private Note note;

    @Getter@Setter
    private Boolean isAccept=false;

    @ManyToOne
    @Getter@Setter
    private User user;


}
