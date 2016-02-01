package ru.bolobanov.chatserver.services;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;
import ru.bolobanov.chatserver.ErrorMessages;
import ru.bolobanov.chatserver.db.DBHelper;
import ru.bolobanov.chatserver.db.HibernateUtil;
import ru.bolobanov.chatserver.entities.mapping.UserEntity;
import ru.bolobanov.chatserver.entities.request.LoginEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/login")
public class LoginService {

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginEntity login) {
        JSONObject returned = new JSONObject();
        DBHelper dbHelper = new DBHelper();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            if ((login.getLogin() == null) || login.getLogin().isEmpty()) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.INCORRECT_REQUEST);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.INCORRECT_REQUEST));
                return Response.status(200).entity(returned.toString()).build();
            }

            UserEntity userEntity = dbHelper.getUser(session, login.getLogin());

            if (userEntity == null || !userEntity.getPassword().equals(login.getPassword())) {
                returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.INCORRECT_PASSWORD);
                returned.put(ErrorMessages.ERROR_MESSAGE, ErrorMessages.messages.get(ErrorMessages.INCORRECT_PASSWORD));
                return Response.status(200).entity(returned.toString()).build();
            }
            String sessionUUID = createSession(session, userEntity, dbHelper);
            JSONObject userJSON = new JSONObject();
            if (sessionUUID != null) {
                userJSON.put("login", userEntity.getName());
                userJSON.put("session", sessionUUID);
                returned.put("user", userJSON);
            }
            returned.put(ErrorMessages.ERROR_CODE, ErrorMessages.OK);
            returned.put(ErrorMessages.ERROR_MESSAGE, "");

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return Response.status(200).entity(returned.toString()).build();
    }


    private String createSession(Session pSession, UserEntity userEntity, DBHelper dbHelper) {
        String sessionUUID = UUID.randomUUID().toString();
        dbHelper.deleteSession(pSession, userEntity);
        dbHelper.saveSession(pSession, userEntity, sessionUUID);
        return sessionUUID;
    }
}
