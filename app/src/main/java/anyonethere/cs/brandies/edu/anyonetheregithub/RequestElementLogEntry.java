package anyonethere.cs.brandies.edu.anyonetheregithub;

import java.util.Date;

/**
 * Created by lurun on 11/14/2017.
 */

public class RequestElementLogEntry {

    public String heading;
    public String description;
    public String reward;
    public int from;
    public int expire;
    public String UserID;
    public int ImageID;
    public Date date;

    public RequestElementLogEntry(String heading, String description, String reward, int from, int expire, String UserID, Date date) {
        this.heading = heading;
        this.description = description;
        this.reward = reward;
        this.expire = expire;
        this.UserID = UserID;
        this.ImageID = ImageID;
        this.date = date;
    }

}
