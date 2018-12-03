package com.hdl.gzccocpcore.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Serializable{

    private String username;

    private String password;

    private String email;

    private String gender;

    @ManyToMany(mappedBy = "userList" ,fetch=FetchType.EAGER )
    @Cascade(value ={ org.hibernate.annotations.CascadeType.PERSIST,org.hibernate.annotations.CascadeType.MERGE})
    private List<Role> roleList;

}
