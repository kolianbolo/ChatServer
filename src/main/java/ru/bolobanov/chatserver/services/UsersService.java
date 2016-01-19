package ru.bolobanov.chatserver.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.bolobanov.chatserver.db.DBHelper;
import ru.bolobanov.chatserver.entities.mapping.UserEntity;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Bolobanov Nikolay on 19.01.16.
 */
@Path("/users")
public class UsersService {
    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response sendUsers() {
        JSONObject returned = new JSONObject();
        DBHelper dbHelper = new DBHelper();
        try {
            List<UserEntity> usersList = dbHelper.getAllUsers();
            JSONArray usersArray = new JSONArray();
            for (UserEntity userEntity : usersList) {
                JSONObject nodeJson = new JSONObject();
                nodeJson.put("name", userEntity.getName());
                usersArray.put(nodeJson);
            }
            returned.put("users", usersArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Response.status(200).entity(returned.toString()).build();
    }
}
