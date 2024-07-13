package models;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ChirpModel {

    public static ArrayList<Chirp> getFeedChirps(int currentUserId) {
        ArrayList<Chirp> chirps = new ArrayList<>();
        try {

            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("select * from chirp");

            while (results.next()) {
                int user_id = results.getInt("user_id");
                ArrayList<Integer> follows = UserModel.getFollows(currentUserId);
                if (follows.contains(user_id)) {
                    int id = results.getInt("id");
                    String text = results.getString("text");
                    Timestamp timestamp = results.getTimestamp("timestamp");
                    Date date = new Date(timestamp.getTime());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                    String formattedDate = dateFormat.format(date);
                    String formattedTime = timeFormat.format(date);

                    User user = UserModel.getUser(user_id);
                    boolean liked = ChirpModel.checkLiked(id, currentUserId);
                    int likeCount = ChirpModel.likeCount(id);

                    Chirp chirp = new Chirp(id, text, user, timestamp, formattedDate, formattedTime, true, liked, likeCount);
                    chirps.add(chirp);
                }
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        sortByTimestamp(chirps);
        return chirps;
    }

    public static ArrayList<Chirp> getUserChirps(int currentUserId, int targetUserId) {
        ArrayList<Chirp> chirps = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * from chirp WHERE user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, targetUserId);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String text = results.getString("text");
                int user_id = results.getInt("user_id");
                Timestamp timestamp = results.getTimestamp("timestamp");

                Date date = new Date(timestamp.getTime());

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

                String formattedDate = dateFormat.format(date);
                String formattedTime = timeFormat.format(date);
                User user = UserModel.getUser(user_id);
                boolean following = UserModel.checkFollowing(currentUserId, targetUserId);
                boolean liked = ChirpModel.checkLiked(id, currentUserId);
                int likeCount = ChirpModel.likeCount(id);

                Chirp chirp = new Chirp(id, text, user, timestamp, formattedDate, formattedTime, following, liked, likeCount);
                chirps.add(chirp);
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        sortByTimestamp(chirps);
        return chirps;
    }

    public static void addChirp(Chirp chirp, InputStream image, String filename) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "insert into chirp (text, user_id, image, filename)"
                    + " values (? , ? , ? , ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, chirp.getText());
            statement.setInt(2, chirp.getUser().getId());
            statement.setBlob(3, image);
            statement.setString(4, filename);

            statement.execute();

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public static void addLike(int chirpId, int currentUserId) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "insert into likes (chirp_id, user_id)"
                    + " values (? , ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, chirpId);
            statement.setInt(2, currentUserId);

            statement.execute();

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public static void removeLike(int chirpId, int currentUserId) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "DELETE from likes WHERE chirp_id = ? AND user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, chirpId);
            statement.setInt(2, currentUserId);

            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    private static boolean checkLiked(int chirpId, int currentUserId) {
        boolean liked = false;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * from likes WHERE chirp_id = ? AND user_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, chirpId);
            statement.setInt(2, currentUserId);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int chirp_id = results.getInt("chirp_id");
                int user_id = results.getInt("user_id");
                if (chirp_id == chirpId && user_id == currentUserId) {
                    liked = true;
                }
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return liked;
    }

    private static int likeCount(int chirpId) {
        int count = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT * from likes WHERE chirp_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, chirpId);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                count++;
            }

            results.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return count;
    }

    static class ChirpComparator implements Comparator<Chirp> {

        @Override
        public int compare(Chirp o1, Chirp o2) {
            return o2.getTimestamp().compareTo(o1.getTimestamp());
        }

    }

    public static void sortByTimestamp(ArrayList<Chirp> chirps) {
        Comparator<Chirp> comparator = new ChirpComparator();
        Collections.sort(chirps, comparator);
    }

}
