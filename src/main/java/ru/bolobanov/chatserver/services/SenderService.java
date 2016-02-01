package ru.bolobanov.chatserver.services;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.bolobanov.chatserver.ErrorMessages;
import ru.bolobanov.chatserver.db.DBHelper;
import ru.bolobanov.chatserver.db.HibernateUtil;
import ru.bolobanov.chatserver.entities.mapping.MessageEntity;
import ru.bolobanov.chatserver.entities.mapping.SessionEntity;
import ru.bolobanov.chatserver.entities.request.ReceiveEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Bolobanov Nikolay on 19.01.16.
 */
@Path("/sender")
public class SenderService {

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response send(ReceiveEntity receiveEntity) {
        JSONObject returned = new JSONObject();
        DBHelper dbHelper = new DBHelper();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if ((receiveEntity.getSession() == null)) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.INCORRECT_REQUEST);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.INCORRECT_REQUEST));
                return Response.status(200).entity(returned.toString()).build();
            }

            SessionEntity sessionEntity = dbHelper.getSession(session, receiveEntity.getSession());
            if (sessionEntity == null) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.BAD_SESSION);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.BAD_SESSION));
                return Response.status(200).entity(returned.toString()).build();
            }
            List<MessageEntity> messagesList = dbHelper.getMessages(session, sessionEntity.getUserEntity());
            dbHelper.updateStatus(session, messagesList, MessageEntity.STATUS_SENDED);
            if (messagesList == null) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.UNEXPECTED_ERROR);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.UNEXPECTED_ERROR));
                return Response.status(200).entity(returned.toString()).build();
            }
            JSONArray messagesJSON = new JSONArray();
            for (MessageEntity message : messagesList) {
                JSONObject messageJson = new JSONObject();
                messageJson.put("message", message.getMessage());
                messageJson.put("sender", message.getSenderEntity().getName());
                messageJson.put("receiver", message.getRecipientEntity().getName());
                messageJson.put("timestamp", message.getTimestamp().getTime());
                messagesJSON.put(messageJson);
            }
            returned.put("messages", messagesJSON);
            returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.OK);
            returned.put(ErrorMessages.ERROR_MESSAGE, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }finally{
            session.close();
        }
        return Response.status(200).entity(returned.toString()).build();

    }
}
