package servlets;

import entities.User;
import helpers.data.DataHelper;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainPage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataHelper dataHelper = null;
        ArrayList<User> userList = null;
        try {
            dataHelper = new DataHelper("java:/Postgres");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Can not create DataHelper");
        }

        if (dataHelper == null){
            System.out.println("Connection not created!=(");
        }else{
            System.out.println("Connection created!=)");
        }

        userList = dataHelper.getAllUsers();

        request.setAttribute("userList", userList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/index.jsp");
        requestDispatcher.forward(request, response);
    }
}
