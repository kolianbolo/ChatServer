package ru.bolobanov.chatserver.services;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;
import ru.bolobanov.chatserver.ErrorMessages;
import ru.bolobanov.chatserver.db.DBHelper;
import ru.bolobanov.chatserver.db.HibernateUtil;
import ru.bolobanov.chatserver.entities.mapping.SessionEntity;
import ru.bolobanov.chatserver.entities.mapping.UserEntity;
import ru.bolobanov.chatserver.entities.request.SendEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by Bolobanov Nikolay on 19.01.16.
 */
@Path("/receiver")
public class ReceiverService {

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response reception(SendEntity request) {
        JSONObject returned = new JSONObject();
        DBHelper dbHelper = new DBHelper();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if ((request.getSession() == null) || (request.getMessage() == null) || (request.getRecipient() == null)) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.INCORRECT_REQUEST);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.INCORRECT_REQUEST));
                return Response.status(200).entity(returned.toString()).build();
            }

            SessionEntity sessionEntity = dbHelper.getSession(session, request.getSession());
            if (sessionEntity == null) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.BAD_SESSION);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.BAD_SESSION));
                return Response.status(200).entity(returned.toString()).build();
            }
            UserEntity recipientUser = dbHelper.getUser(session, request.getRecipient());
            if (recipientUser == null) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.RECIPIENT_NOT_EXISTS);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.RECIPIENT_NOT_EXISTS));
                return Response.status(200).entity(returned.toString()).build();
            }
            long timestamp = dbHelper.saveMessage(session, request.getMessage(), sessionEntity.getUserEntity().getId(), recipientUser.getId());
            if (timestamp != 0) {
                returned.put("timestamp", timestamp);
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.OK);
                returned.put(ErrorMessages.ERROR_MESSAGE, "");
            } else {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.UNEXPECTED_ERROR);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.UNEXPECTED_ERROR));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally{
            session.close();
        }
        return Response.status(200).entity(returned.toString()).build();

    }
}
