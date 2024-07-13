package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserModel {

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("select * from users");

            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String username = results.getString("username");
                String email_address = results.getString("email_address");
                String password_hash = results.getString("password_hash");

                User user = new User(id, name, username, email_address, password_hash);

                users.add(user);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return users;
    }

    public static void addUser(User user) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "insert into users (name, username, email_address, password_hash)"
                    + " values (? , ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getEmailAddress());
            statement.setString(4, user.getPasswordHash());

            statement.execute();

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }
    
    

    public static int findUser(String emailAddress, String passwordHash) {
        int found = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT id FROM users WHERE email_address = ? AND password_hash = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, emailAddress);
            statement.setString(2, passwordHash);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                found = results.getInt("id");
            }

            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return found;
    }
    
    public static User findUser(String usernameSearch) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * FROM users WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, usernameSearch);


            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String username = results.getString("username");
                user = new User(id, name, username);
            }

            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return user;
    }
    
    public static User findUser(String usernameSearch, int userId) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * FROM users WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, usernameSearch);


            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String username = results.getString("username");
                boolean following = UserModel.checkFollowing(userId, id);
                user = new User(id, name, username, following);
            }

            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return user;
    }
    
    public static User getUser(int userId) {
        User user = null;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * FROM users WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);


            ResultSet results = statement.executeQuery();

            if (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                String username = results.getString("username");
                String email_address = results.getString("email_address");
                String password_hash = results.getString("password_hash");

                user = new User(id, name, username, email_address, password_hash);
            }

            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return user;
    }
    
    public static void addFollow(int followerUserId, int userId) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "insert into follows (follower_user_id, user_id)"
                    + " values (? , ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, followerUserId);
            statement.setInt(2, userId);

            statement.execute();

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }
    
    public static void deleteFollow(int followerUserId, int userId) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "DELETE from follows WHERE follower_user_id = ? AND user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, followerUserId);
            statement.setInt(2, userId);

            statement.executeUpdate();
 
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public static boolean checkFollowing(int followerUserId, int userId) {
        boolean following = false;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * from follows WHERE follower_user_id = ? AND user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, followerUserId);
            statement.setInt(2, userId);

            ResultSet results = statement.executeQuery();
            
            while (results.next()) {
                int follower_user_id = results.getInt("follower_user_id");
                int user_id = results.getInt("user_id");
                if(follower_user_id == followerUserId && user_id == userId){
                    following = true;
                }
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return following;
    }
    
    public static ArrayList<Integer> getFollows(int userId) {
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * from follows WHERE follower_user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, userId);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int user_id = results.getInt("user_id");
                
                ids.add(user_id);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return ids;
    }
   
}
