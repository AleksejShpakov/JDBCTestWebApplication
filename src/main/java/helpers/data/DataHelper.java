package helpers.data;

import entities.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataHelper {
    private Connection connection = null;

    public DataHelper(String jndiStr) throws NamingException, SQLException, NullPointerException {
        InitialContext initialContext = null;
        DataSource dataSource = null;

        initialContext = new InitialContext();
        dataSource = (DataSource)initialContext.lookup(jndiStr);
        if (dataSource != null){
            connection = dataSource.getConnection();
        }else{
            throw new NullPointerException("DataSource for \"" + jndiStr + " is null.");
        }
    }


    public User addUser(User user){
        Statement statement = null;
        int updated = 0;
        try {
            statement = connection.createStatement();
            updated = statement.executeUpdate ("INSERT INTO \"user\"(name, surname, patronymic)" +
                    "VALUES ('" + user.getName() + "', '" + user.getSurName() + "', '" + user.getPatronymic() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }

        if (updated < 1){
            user = null;
        }
        return user;
    }

    public ArrayList<User> getAllUsers(){
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        ArrayList<User> userList = new ArrayList<User>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM \"user\"");

            while(resultSet.next()){
                user = new User(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("patronymic"));
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }



}
