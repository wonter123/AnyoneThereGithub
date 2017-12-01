package anyonethere.cs.brandies.edu.anyonetheregithub;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by phoebez on 14.11.17.
 */

@IgnoreExtraProperties
public class Post {
    public String title;
    public int reward;
    public String description;
    public Date postDate;
    public Date expireDate;
    public String from;
    public String to;
    public String posterId;
    public String takerId;
    public HashMap<String, Object> result;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String title, int reward, String description, Date postDate,
                Date expireDate, String from, String to) {
        this.title = title;
        this.reward = reward;
        this.description = description;
        this.postDate = postDate;
        this.expireDate = expireDate;
        this.from = from;
        this.to = to;
        this.posterId = null;
        this.takerId = null;
        toMap();
    }

    @Exclude
    public Map<String, Object> toMap() {
        result = new HashMap<>();
        result.put("title", title);
        result.put("reward", reward);
        result.put("description", description);
        result.put("postDate", postDate);
        result.put("expireDate", expireDate);
        result.put("to", to);
        result.put("from", from);
        result.put("poster", posterId);
        result.put("taker", takerId);
        return result;
    }

    public void setTaker(String id) {
        this.takerId = id;
        result.put("taker", takerId);
    }

    public void setPoster(String id) {
        this.posterId = id;
        result.put("poster", posterId);
    }
}
