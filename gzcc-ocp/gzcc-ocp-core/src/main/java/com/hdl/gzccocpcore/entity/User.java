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
public class User  implements Serializable{

    private static final long serialVersionUID = 6911727722566303411L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置自动增长
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    @Getter@Setter
    private Date createTime;

//    @CreatedBy
//    @Getter@Setter
//    protected Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @Getter@Setter
    private Date lastModifiedTime;

//    @LastModifiedBy
//    @Getter@Setter
//    protected Long lastModifiedBy;

    @Getter@Setter
    private String username;

    @Getter@Setter
    private String nickname;

    @JsonIgnore
    @Getter@Setter
    private String password;

    @Getter@Setter
    private String email;

    @Getter@Setter
    private String gender;

    @Getter@Setter
    private String avatar;

    @ManyToMany(mappedBy = "userList" ,fetch=FetchType.EAGER )
    @Cascade(value ={ org.hibernate.annotations.CascadeType.PERSIST,org.hibernate.annotations.CascadeType.MERGE})
    @Getter@Setter
    private List<Role> roleList=new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter@Setter
    private List<Reply> replyList=new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter@Setter
    private List<Note> noteList=new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter@Setter
    private List<Friend> friendList=new ArrayList<>();

}
