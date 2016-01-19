package ru.bolobanov.chatserver.entities.mapping;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bolobanov Nikolay on 18.01.16.
 */
@Entity
@Table(name = "messages")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final short STATUS_NOT_SENDED = 3;
    public static final short STATUS_SENDED = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sender")
    private Integer sender;

    @Column(name = "recipient")
    private Integer recipient;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "status")
    private Short status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", insertable = false, updatable = false)
    private UserEntity senderEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient", insertable = false, updatable = false)
    private UserEntity recipientEntity;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public void setRecipient(Integer recipient) {
        this.recipient = recipient;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getStatus() {
        return status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRecipient() {
        return recipient;
    }

    public Integer getSender() {
        return sender;
    }

    public Integer getId() {
        return id;
    }

    public UserEntity getSenderEntity() {
        return senderEntity;
    }

    public UserEntity getRecipientEntity() {
        return recipientEntity;
    }

    public void setSenderEntity(UserEntity senderEntity) {
        this.senderEntity = senderEntity;
    }

    public void setRecipientEntity(UserEntity recipientEntity) {
        this.recipientEntity = recipientEntity;
    }
}
