package anyonethere.cs.brandies.edu.anyonetheregithub;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SiChen on 11/14/17.
 */

public class User {
    public String username;
    public String email;
    public String phone;
    public int credit;
    public int rating;
    public int task_accomplished;
    public int task_posted;

    public HashMap<String, Object> result;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.phone = "";
        this.credit = 0;
        this.rating = 0;
        this.task_accomplished = 0;
        this.task_posted = 0;
    }

    public User(String username, String email, String phone, int credit, int rating, int task_accomplished, int task_posted) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.credit = credit;
        this.rating = rating;
        this.task_accomplished = task_accomplished;
        this.task_posted = task_posted;
    }

    @Exclude
    public Map<String, Object> toMap() {
        result = new HashMap<>();
        result.put("username", username);
        result.put("email", email);
        result.put("phone", phone);
        result.put("rating", rating);
        result.put("task_accomplished", task_accomplished);
        result.put("task_posted", task_posted);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getRating() {
        return rating;
    }

    public int getTask_accomplished() {
        return task_accomplished;
    }

    public int getTask_posted() {
        return task_posted;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTask_accomplished(int task_accomplished) {
        this.task_accomplished = task_accomplished;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setTask_posted(int task_posted) {
        this.task_posted = task_posted;
    }
}
