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

    public UserEntity getUser(String pName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<UserEntity> usersList = session.createCriteria(UserEntity.class)
                .add(Restrictions.in("name", new String[]{pName}))
                .list();
        if (usersList.size() > 0) {
            return usersList.get(0);
        }
        session.getTransaction().commit();
        return null;
    }

    public List<UserEntity> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<UserEntity> usersList = session.createCriteria(UserEntity.class)
                .list();
        session.getTransaction().commit();
        return usersList;
    }

    public SessionEntity getSession(String pUUID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<SessionEntity> sessionsList = session.createCriteria(SessionEntity.class)
                .add(Restrictions.in("uuid", new String[]{pUUID}))
                .list();
        if (sessionsList.size() > 0) {
            return sessionsList.get(0);
        }
        session.getTransaction().commit();
        return null;
    }

    public void deleteSession(UserEntity pUser) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        SessionEntity sessionEntity = (SessionEntity) session.createCriteria(SessionEntity.class)
                .add(Restrictions.eq("user", pUser.getId())).uniqueResult();
        if (sessionEntity != null) {
            session.delete(sessionEntity);
        }
        session.getTransaction().commit();
    }

    public void saveSession(UserEntity pUser, String pUUID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setTimestamp(new Date());
        sessionEntity.setUserEntity(pUser);
        sessionEntity.setUuid(pUUID);
        sessionEntity.setUser(pUser.getId());
        session.save(sessionEntity);
        session.getTransaction().commit();
    }

    public List<MessageEntity> getMessages(UserEntity recipient) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<MessageEntity> messagesList = session.createCriteria(MessageEntity.class)
                .add(Restrictions.eq("recipient", recipient.getId()))
                .add(Restrictions.eq("status", MessageEntity.STATUS_NOT_SENDED))
                .list();
        session.getTransaction().commit();
        return messagesList;
    }

    public void updateStatus(List<MessageEntity> pMessages, short pStatus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hqlUpdate = "update MessageEntity c set c.status = :newStatus where c.id = :id";
        Query query = session.createQuery(hqlUpdate)
                .setShort("newStatus", pStatus);
        for (MessageEntity message : pMessages) {
            query.setInteger("id", message.getId())
                    .executeUpdate();
        }
        session.getTransaction().commit();
    }

    public long saveMessage(String message, int sender, int recipient) {
        Date aDate = new Date();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(sender);
        messageEntity.setRecipient(recipient);
        messageEntity.setMessage(message);
        messageEntity.setTimestamp(aDate);
        messageEntity.setStatus(MessageEntity.STATUS_NOT_SENDED);
        session.save(messageEntity);
        session.getTransaction().commit();
        return aDate.getTime();
    }

}
