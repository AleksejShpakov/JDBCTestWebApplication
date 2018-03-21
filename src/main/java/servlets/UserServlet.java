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
import java.lang.reflect.InvocationTargetException;
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
        DataHelper dataHelper = null;
        User user = null;
        JSONObject outJSONObject = new JSONObject();
        String status = "";

        try {
            dataHelper = new DataHelper("java:/Postgres");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (dataHelper == null){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Connect to db failed.");
            return outJSONObject;
        }

        user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("patronymic"));
        user = dataHelper.addUser(user);
        if (user == null){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Add user failed.");
            return outJSONObject;
        }

        outJSONObject.put("status", ResponseStatusDefinition.SUCCESS);
        outJSONObject.put("message", "Add user success.");

        return outJSONObject;
    }

    private JSONObject getAllUsers(HttpServletRequest request){
        DataHelper dataHelper = null;
        User user = null;
        List<User> userList = null;
        JSONObject outJSONObject = new JSONObject();
        JSONObject userJSONObject = null;
        JSONArray usersJSONArray = null;
        String status = "";

        try {
            dataHelper = new DataHelper("java:/Postgres");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (dataHelper == null){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Connect to db failed.");
            return outJSONObject;
        }

        user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("patronymic"));
        user = dataHelper.addUser(user);
        if (user == null){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Add user failed.");
            return outJSONObject;
        }
/*
        userList = dataHelper.getAllUsers();
        usersJSONArray = new JSONArray();
        if(userList != null && userList.size() > 0){
            for(int i = 0; i < userList.size(); i++){
                user = userList.get(i);
                userJSONObject = new JSONObject();
                userJSONObject.put("name", user.getName());
                userJSONObject.put("surname", user.getSurName());
                userJSONObject.put("patronymic", user.getPatronymic());
                usersJSONArray.put(userJSONObject);
            }
        }*/

        outJSONObject.put("users", usersJSONArray);
        outJSONObject.put("status", ResponseStatusDefinition.SUCCESS);
        outJSONObject.put("message", "Add user success.");

        return outJSONObject;
    }

}
