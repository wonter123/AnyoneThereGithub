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
    public int photoId;
    public int credit;
    public int rating;
    public int task_accomplished;
    public int task_posted;

    final int[] userHeadsId = new int[10];

    public HashMap<String, Object> result;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        userHeadsId[0] = R.drawable.user_head_1;
        userHeadsId[1] = R.drawable.user_head_2;
        userHeadsId[2] = R.drawable.user_head_3;
        userHeadsId[3] = R.drawable.user_head_4;
        userHeadsId[4] = R.drawable.user_head_5;
        userHeadsId[5] = R.drawable.user_head_6;
        userHeadsId[6] = R.drawable.user_head_7;
        userHeadsId[7] = R.drawable.user_head_8;
        userHeadsId[8] = R.drawable.user_head_9;
        userHeadsId[9] = R.drawable.user_head_10;

        this.username = username;
        this.email = email;
        this.phone = "";
        this.photoId = userHeadsId[(int) (Math.random()*10)];
        this.credit = 100;
        this.rating = 5;
        this.task_accomplished = 0;
        this.task_posted = 0;
    }

    public User(String username, String email, String phone, int photoId, int credit, int rating, int task_accomplished, int task_posted) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.photoId = photoId;
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
        result.put("photoId", photoId);
        result.put("rating", rating);
        result.put("task_accomplished", task_accomplished);
        result.put("task_posted", task_posted);
        return result;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
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
