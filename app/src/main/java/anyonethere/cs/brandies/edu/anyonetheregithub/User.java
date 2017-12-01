package anyonethere.cs.brandies.edu.anyonetheregithub;

/**
 * Created by zhanglingjun on 11/14/17.
 */

public class User {
    public String username;
    public String email;
    public String phone;
    public int rating;
    public int task_accomplished;
    public int task_posted;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.phone = "";
        this.rating = 0;
        this.task_accomplished = 0;
        this.task_posted = 0;
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

    public void setTask_posted(int task_posted) {
        this.task_posted = task_posted;
    }
}
