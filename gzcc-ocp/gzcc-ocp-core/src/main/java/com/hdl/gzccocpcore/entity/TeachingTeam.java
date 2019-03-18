//package com.hdl.gzccocpcore.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
//@EntityListeners(AuditingEntityListener.class)
//@NoArgsConstructor
//@AllArgsConstructor
//public class TeachingTeam implements Serializable {
//    private static final long serialVersionUID = -8048491347295979125L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  //设置自动增长
//    @Column(name = "id")
//    @Getter
//    @Setter
//    private Long id;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @CreatedDate
//    @Getter
//    @Setter
//    private Date createTime;
//
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @LastModifiedDate
//    @Getter
//    @Setter
//    private Date lastModifiedTime;
//
//    @Getter
//    @Setter
//    private String name;
//
//    @Getter
//    @Setter
//    private Boolean deleted = false;
//
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @Getter
//    @Setter
//    private List<User> userList = new ArrayList<>();
//
//    @ManyToOne
//    @Getter
//    @Setter
//    private Major major;
//
//
//}
