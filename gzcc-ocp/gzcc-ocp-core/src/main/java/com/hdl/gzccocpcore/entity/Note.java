package com.hdl.gzccocpcore.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
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
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Note  implements Serializable {

    private static final long serialVersionUID = 2005794687566181633L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置自动增长
    @Column(name = "id")
    @Getter
    @Setter
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

    @Getter@Setter
    private String title;

    @Column(columnDefinition="text")
    @Getter@Setter
    private String content;

    @OneToMany(mappedBy = "note",fetch = FetchType.LAZY)
//    @Cascade(value = org.hibernate.annotations.CascadeType.PERSIST)
    @JsonIgnore
    @Getter@Setter
    private List<Reply> replyList= new ArrayList<>();

    @Getter@Setter
    private Boolean canReply=true;

    @Getter@Setter
    private String noteType;

    @ManyToOne
    @Getter@Setter
    private User user;









}
