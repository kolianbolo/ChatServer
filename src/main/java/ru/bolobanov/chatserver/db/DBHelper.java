package ru.bolobanov.chatserver.db;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import ru.bolobanov.chatserver.entities.mapping.MessageEntity;
import ru.bolobanov.chatserver.entities.mapping.SessionEntity;
import ru.bolobanov.chatserver.entities.mapping.UserEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by Bolobanov Nikolay on 19.01.16.
 */
public class DBHelper {

    public UserEntity getUser(Session pSession, String pName) {
        pSession.beginTransaction();
        List<UserEntity> usersList = pSession.createCriteria(UserEntity.class)
                .add(Restrictions.in("name", new String[]{pName}))
                .list();
        if (usersList.size() > 0) {
            return usersList.get(0);
        }
        pSession.getTransaction().commit();
        return null;
    }

    public List<UserEntity> getAllUsers(Session pSession) {
        pSession.beginTransaction();
        List<UserEntity> usersList = pSession.createCriteria(UserEntity.class)
                .list();
        pSession.getTransaction().commit();
        return usersList;
    }

    public SessionEntity getSession(Session pSession, String pUUID) {

        pSession.beginTransaction();
        List<SessionEntity> sessionsList = pSession.createCriteria(SessionEntity.class)
                .add(Restrictions.in("uuid", new String[]{pUUID}))
                .list();
        if (sessionsList.size() > 0) {
            return sessionsList.get(0);
        }
        pSession.getTransaction().commit();
        return null;
    }

    public void deleteSession(Session pSession, UserEntity pUser) {
        pSession.beginTransaction();
        SessionEntity sessionEntity = (SessionEntity) pSession.createCriteria(SessionEntity.class)
                .add(Restrictions.eq("user", pUser.getId())).uniqueResult();
        if (sessionEntity != null) {
            pSession.delete(sessionEntity);
        }
        pSession.getTransaction().commit();
    }

    public void saveSession(Session pSession, UserEntity pUser, String pUUID) {
        pSession.beginTransaction();
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setTimestamp(new Date());
        sessionEntity.setUserEntity(pUser);
        sessionEntity.setUuid(pUUID);
        sessionEntity.setUser(pUser.getId());
        pSession.save(sessionEntity);
        pSession.getTransaction().commit();
    }

    public List<MessageEntity> getMessages(Session pSession, UserEntity recipient) {
        pSession.beginTransaction();
        List<MessageEntity> messagesList =  pSession.createCriteria(MessageEntity.class)
                .add(Restrictions.eq("recipient", recipient.getId()))
                .add(Restrictions.eq("status", MessageEntity.STATUS_NOT_SENDED))
                .list();
        pSession.getTransaction().commit();
        return messagesList;
    }

    public void updateStatus(Session pSession, List<MessageEntity> pMessages, short pStatus) {
        pSession.beginTransaction();
        String hqlUpdate = "update MessageEntity c set c.status = :newStatus where c.id = :id";
        Query query = pSession.createQuery(hqlUpdate)
                .setShort("newStatus", pStatus);
        for (MessageEntity message : pMessages) {
            query.setInteger("id", message.getId())
                    .executeUpdate();
        }
        pSession.getTransaction().commit();
    }

    public long saveMessage(Session pSession, String message, int sender, int recipient) {
        Date aDate = new Date();
        pSession.beginTransaction();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(sender);
        messageEntity.setRecipient(recipient);
        messageEntity.setMessage(message);
        messageEntity.setTimestamp(aDate);
        messageEntity.setStatus(MessageEntity.STATUS_NOT_SENDED);
        pSession.save(messageEntity);
        pSession.getTransaction().commit();
        return aDate.getTime();
    }

}
