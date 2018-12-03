package com.hdl.gzccocpcore.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
//@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Role extends BaseEntity implements Serializable {

    private String name;

    private String description;

    @ManyToMany
    @JsonIgnore
    private List<User> userList;
}
