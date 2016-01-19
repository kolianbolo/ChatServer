package ru.bolobanov.chatserver.entities.mapping;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bolobanov Nikolay on 17.01.16.
 */
@Entity
@Table(name = "sessions")
public class SessionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user")
    private Integer user;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "timestamp")
    private Date timestamp;

    @OneToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "user", insertable = false, updatable = false )
    private UserEntity userEntity;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(Integer pId) {
        id = pId;
    }

    public void setUuid(String pUuid) {
        uuid = pUuid;
    }

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}

