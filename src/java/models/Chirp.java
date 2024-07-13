package models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Chirp implements Serializable {

    private int id;
    private String text;
    private User user;
    private Timestamp timestamp;
    private String date;
    private String time;
    private boolean liked;
    private boolean following;
    private byte[] image;
    private String filename;
    private int likeCount;

    public Chirp() {
    }

    public Chirp(String text, User user) {
        this.text = text;
        this.user = user;

    }
    //Delete at some point
        public Chirp(int id, String text, User user, String date, String time) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.date = date;
        this.time = time;
    }

    public Chirp(int id, String text, User user, Timestamp timestamp, String date, String time, boolean following, boolean liked, int likeCount) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.timestamp = timestamp;
        this.date = date;
        this.time = time;
        this.liked = liked;
        this.following = following;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return "Chirp{" + "id=" + id + ", text=" + text + ", user=" + user + ", timestamp=" + timestamp + ", date=" + date + ", time=" + time + ", liked=" + liked + ", following=" + following + ", image=" + image + ", filename=" + filename + ", likeCount=" + likeCount + '}';
    }
    
}
