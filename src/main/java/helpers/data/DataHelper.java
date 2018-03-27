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

public abstract class DataHelper {

    public static Connection getConnection(){
        InitialContext initialContext = null;
        DataSource dataSource = null;
        Connection connection = null;

        try {
            initialContext = new InitialContext();
            dataSource = (DataSource)initialContext.lookup("java:/Postgres");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User addUser(User user) throws Exception{
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        int updated = 0;
        int quantityOfRows = 0;

        connection = DataHelper.getConnection();
        if (connection == null){
            throw new Exception("Не удалось подключиться к базе данных");
        }

        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("SELECT * FROM \"user\" WHERE name='" + user.getName() + "' AND surname='" + user.getSurName() + "'AND patronymic='" + user.getPatronymic() + "'");
            if (resultSet.last()){
                quantityOfRows = resultSet.getRow();
            }
        }catch(SQLException ex){
            throw new Exception("Возникла ошибка при обращении к базе данных");
        }

        if (quantityOfRows > 0){
            throw new Exception("Такой пользовать уже существует");
        }

        try {
            statement = connection.createStatement();
            updated = statement.executeUpdate ("INSERT INTO \"user\"(name, surname, patronymic)" +
                    "VALUES ('" + user.getName() + "', '" + user.getSurName() + "', '" + user.getPatronymic() + "');");
        } catch (SQLException e) {
            throw new Exception("Возникла ошибка при обращении к базе данных");
        }

        if (updated < 1){
            throw new Exception("Пользователь не был добавлен");
        }

        DataHelper.closeConnection(connection);

        return user;
    }

    public static User removeUser(User user) throws Exception{
        Statement statement = null;
        Connection connection = null;
        int deleted = 0;

        connection = DataHelper.getConnection();
        if (connection == null){
            throw new Exception("Не удалось подключиться к базе данных");
        }

        try {
            statement = connection.createStatement();
            deleted = statement.executeUpdate ("DELETE FROM \"user\" WHERE name='" + user.getName() + "' AND surname='" + user.getSurName() + "' AND patronymic='" + user.getPatronymic() + "'");
        } catch (SQLException e) {
            throw new Exception("Возникла ошибка при обращении к базе данных");
        }

        if (deleted < 1){
            throw new Exception("Пользователь не был удален");
        }

        DataHelper.closeConnection(connection);

        return user;
    }

    public static ArrayList<User> getAllUsers() throws Exception{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        ArrayList<User> userList = new ArrayList<User>();

        connection = DataHelper.getConnection();
        if (connection == null){
            throw new Exception("Не удалось подключиться к базе данных");
        }

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM \"user\"");

            while(resultSet.next()){
                user = new User(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("patronymic"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new Exception("Возникла ошибка при обращении к базе данных");
        }

        DataHelper.closeConnection(connection);

        return userList;
    }

    public static ArrayList<User> searchUsers(String column, String value) throws Exception{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        ArrayList<User> userList = new ArrayList<User>();

        connection = DataHelper.getConnection();
        if (connection == null){
            throw new Exception("Не удалось подключиться к базе данных");
        }

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM \"user\" WHERE " + column + "='" + value + "'");

            while(resultSet.next()){
                user = new User(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("patronymic"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new Exception("Возникла ошибка при обращении к базе данных");
        }

        DataHelper.closeConnection(connection);

        return userList;
    }

}
