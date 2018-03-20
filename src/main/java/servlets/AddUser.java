package servlets;

import definitions.ResponseStatusDefinition;
import entities.User;
import helpers.data.DataHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject;

public class AddUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataHelper dataHelper = null;
        User user = null;
        List<User> userList = null;
        //JSONObject outJSONObject = new JSONObject();
        String status = "";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
/*
        try {
            dataHelper = new DataHelper("java:/Postgres");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (dataHelper == null){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Connect to db failed.");
            response.getWriter().write(outJSONObject.toString());
            return;
        }

        user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("patronymic"));
        user = dataHelper.addUser(user);
        if (user == null){
            outJSONObject.put("status", ResponseStatusDefinition.ERROR);
            outJSONObject.put("message", "Add user failed.");
            response.getWriter().write(outJSONObject.toString());
            return;
        }

        userList = dataHelper.getAllUsers();

        outJSONObject.put("status", ResponseStatusDefinition.SUCCESS);
        outJSONObject.put("message", "Add user success.");
        response.getWriter().write(outJSONObject.toString());*/
    }
}
