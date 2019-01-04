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
/**
* @Description:    帖子类
* @Author:         HuDeLin
* @CreateDate:     2018/12/24 11:49
* @UpdateUser:     HuDeLin
* @UpdateDate:     2018/12/24 11:49
* @UpdateRemark:   修改内容
*/
public class Note  implements Serializable {


    private static final long serialVersionUID = -7106333655949626799L;

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


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    @Getter@Setter
    private Date lastModifiedTime;

//    @LastModifiedBy
//    @Getter@Setter
//    protected Long lastModifiedBy;

    @Getter@Setter
    //标题
    private String title;

    @Column(columnDefinition="text")
    @Getter@Setter
    //内容
    private String content;

    @OneToMany(mappedBy = "note",fetch = FetchType.LAZY)
//    @Cascade(value = org.hibernate.annotations.CascadeType.PERSIST)
    @JsonIgnore
    @Getter@Setter
    //回复列表
    private List<Reply> replyList= new ArrayList<>();

    @Getter@Setter
    //是否可以回复 用BaseConstant的true和false
    private String canReply="1";

    @Getter@Setter
    //是否置顶 用BaseConstant的true和false
    private String isTop="1";


    @Getter@Setter
    //是否逻辑删除  用BaseConstant的true和false
    private String isDelete="0";


    @Getter@Setter
    //帖子的类型 用BaseConstant的帖子类型
    private String noteType;

    @ManyToOne
    @Getter@Setter
    private User user;

}
