package servlets;

import definitions.ResponseStatusDefinition;
import entities.User;
import helpers.data.DataHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject outJSONObject = new JSONObject();
        String methodName = "";
        Method userMethod = null;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        methodName = request.getParameter("method");
        if (methodName == null || methodName.equals("")){
            outJSONObject = new JSONObject();
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Method parameter is not defined");
            response.getWriter().write(outJSONObject.toString());
            return;
        }

        try {
            userMethod = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class);
        } catch (NoSuchMethodException e) {
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Method not found");
            response.getWriter().write(outJSONObject.toString());
            return;
        }

        try {
            outJSONObject = (JSONObject) userMethod.invoke(this, request);
        } catch (Exception ex) {
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Method invoke error");
            response.getWriter().write(outJSONObject.toString());
            return;
        }

        response.getWriter().write(outJSONObject.toString());
    }

    private JSONObject addUser(HttpServletRequest request){
        User user = null;
        JSONObject outJSONObject = new JSONObject();

        user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("patronymic"));
        try {
            user = DataHelper.addUser(user);
        }catch(Exception ex){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", ex.getMessage());
            return outJSONObject;
        }

        outJSONObject.put("status", ResponseStatusDefinition.SUCCESS);
        outJSONObject.put("message", "Add user success.");

        return outJSONObject;
    }

    private JSONObject removeUser(HttpServletRequest request){
        User user = null;
        JSONObject outJSONObject = new JSONObject();

        user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("patronymic"));
        try{
            user = DataHelper.removeUser(user);
        }catch(Exception ex){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", ex.getMessage());
            return outJSONObject;
        }

        outJSONObject.put("status", ResponseStatusDefinition.SUCCESS);
        outJSONObject.put("message", "Remove user success.");

        return outJSONObject;
    }

    private JSONObject getAllUsers(HttpServletRequest request){
        User user = null;
        List<User> userList = null;
        JSONObject outJSONObject = new JSONObject();
        JSONObject userJSONObject = null;
        JSONArray usersJSONArray = null;

        try{
            userList = DataHelper.getAllUsers();
        }catch(Exception ex){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", ex.getMessage());
            return outJSONObject;
        }
        usersJSONArray = new JSONArray();
        for(int i = 0; i < userList.size(); i++){
            user = userList.get(i);
            userJSONObject = new JSONObject();
            userJSONObject.put("name", user.getName());
            userJSONObject.put("surname", user.getSurName());
            userJSONObject.put("patronymic", user.getPatronymic());
            usersJSONArray.put(userJSONObject);
        }

        outJSONObject.put("users", usersJSONArray);
        outJSONObject.put("status", ResponseStatusDefinition.SUCCESS);
        outJSONObject.put("message", "Get users success.");

        return outJSONObject;
    }

    private JSONObject searchUsers(HttpServletRequest request){
        User user = null;
        List<User> userList = null;
        JSONObject outJSONObject = new JSONObject();
        JSONObject userJSONObject = null;
        JSONArray usersJSONArray = null;

        try{
            userList = DataHelper.searchUsers(request.getParameter("search_parameter_type"), request.getParameter("search_parameter_value"));
        }catch(Exception ex){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", ex.getMessage());
            return outJSONObject;
        }
        usersJSONArray = new JSONArray();
        for(int i = 0; i < userList.size(); i++){
            user = userList.get(i);
            userJSONObject = new JSONObject();
            userJSONObject.put("name", user.getName());
            userJSONObject.put("surname", user.getSurName());
            userJSONObject.put("patronymic", user.getPatronymic());
            usersJSONArray.put(userJSONObject);
        }

        outJSONObject.put("users", usersJSONArray);
        outJSONObject.put("status", ResponseStatusDefinition.SUCCESS);
        outJSONObject.put("message", "Serch users success.");

        return outJSONObject;
    }
}
