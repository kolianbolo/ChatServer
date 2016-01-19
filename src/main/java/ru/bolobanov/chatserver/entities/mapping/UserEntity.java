package ru.bolobanov.chatserver.entities.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Bolobanov Nikolay on 18.01.16.
 */
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;


    public void setId(Integer pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setPassword(String pPassword) {
        password = pPassword;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
