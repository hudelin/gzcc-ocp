package com.hdl.gzccocpcore.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cascade;
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
public class User implements Serializable {

    private static final long serialVersionUID = 6911727722566303411L;
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
    private Boolean deleted = false;

    @Getter
    @Setter
    private String account;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String realName;

    @JsonIgnore
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    private String avatar;

    @Getter
    @Setter
    private String sign="";

    @Getter
    @Setter
    private Boolean enabled=true;


    @ManyToMany(fetch = FetchType.EAGER)
    @Getter
    @Setter
    //@JoinTable(name="user_role",joinColumns={@JoinColumn(name="user_id")},inverseJoinColumns={@JoinColumn(name="role_id")})
    //    @Cascade(value ={ org.hibernate.annotations.CascadeType.PERSIST,org.hibernate.annotations.CascadeType.MERGE})
    private List<Role> roleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private List<Reply> replyList = new ArrayList<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private List<Note> noteList = new ArrayList<>();

    @Getter
    @Setter
    private String collectNote;

    @ManyToMany(mappedBy = "list", fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private List<Friend> friendList = new ArrayList<>();

    @ManyToMany(mappedBy = "teacherList", fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private List<Major> majorList = new ArrayList<>();

    @ManyToMany(mappedBy = "userList", fetch = FetchType.LAZY)
    @JsonIgnore
    @Getter
    @Setter
    private List<GroupChat> groupChatList = new ArrayList<>();

    @Getter
    @Setter
    @Column(columnDefinition="text")
    private String introduction;

}
