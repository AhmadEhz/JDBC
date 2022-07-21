package org.example.Repository;

//import java.sql.Statement;
//
//import javax.naming.spi.DirStateFactory.Result;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;

import org.example.Entity.User;

import java.sql.*;
import java.util.Scanner;

public class UserRepository {
    private final Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "457894561");

    public UserRepository() throws SQLException {
    }

    public void addUser(User user) throws SQLException {
        String query = "insert into Users (Name, Family, Email, Password)" +
                "values (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getFamily());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public User selectUser(int id) throws SQLException {
        User user = new User();
        String query = "select * from Users " +
                "where Id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user.setId(resultSet.getInt("Id"));
            user.setName(resultSet.getString("Name"));
            user.setFamily(resultSet.getString("Family"));
            user.setEmail(resultSet.getString("Email"));
            user.setPassword(resultSet.getString("Password"));
            preparedStatement.close();
            return user;
        } else return null;
    }

    public User[] loadByName(String name, String family) throws SQLException {
        User user[] = new User[20];
        String query = "select * from Users " +
                "where Name = ? and Family = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, family);
        ResultSet resultSet = preparedStatement.executeQuery();
        int i = 0;
        while (resultSet.next()) {
            user[i].setId(resultSet.getInt("Id"));
            user[i].setName(resultSet.getString("Name"));
            user[i].setFamily(resultSet.getString("Family"));
            user[i].setEmail(resultSet.getString("Email"));
            user[i].setPassword(resultSet.getString("Password"));
            i++;
        }
        preparedStatement.close();
        return user;
    }

    public boolean userContains(User user) throws SQLException {
        String query = """
                select * from Users
                where Name = ? and Family = ? and Email = ? and Password = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getFamily());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        if (resultSet.next()) {
            resultSet.close();
            return true;
        } else {
            resultSet.close();
            return false;
        }
    }
    public boolean userContain (int id) throws SQLException {
        if (selectUser(id)==null)
            return false;
        else return true;
    }

    public void updateUser(int id, User user) throws SQLException {
        String query = "update Users set Name = ?, " +
                "family = ?, " +
                "Email = ?," +
                "Password = ?" +
                "where id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getFamily());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteUser(int id) throws SQLException {
        String query = "delete from Users " +
                "where id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public void deleteUser (String name) throws SQLException {
        String query = "delete from Users Where Name = '"+name+"'";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
    public void deleteUser2 (String name) throws SQLException {
        String query = "delete from Users Where Name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
    }

    public static void main(String[] args) throws SQLException {
        UserRepository sql = new UserRepository();
        Scanner scanner = new Scanner(System.in);
        /*User user = new User();
        user.setName("ggg");
        user.setFamily("hhh");
        user.setEmail("e@yahoo.com");
        user.setPassword("1234");
        sql.addUser(user);*/
        sql.deleteUser(scanner.nextLine());
    }
}
