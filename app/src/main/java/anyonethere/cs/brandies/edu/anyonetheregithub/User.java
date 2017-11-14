package anyonethere.cs.brandies.edu.anyonetheregithub;

/**
 * Created by zhanglingjun on 11/14/17.
 */

public class User {
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
