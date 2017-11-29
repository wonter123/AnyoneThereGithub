package anyonethere.cs.brandies.edu.anyonetheregithub;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by phoebez on 14.11.17.
 */

public class Post {
    private String title;
    private int reward;
    private String description;
    private Date postDate;
    private Date expireDate;
    private String from;
    private String to;
    private String posterId;
    private String takerId;
    HashMap<String, Object> result;

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
        result = new HashMap<>();
        toMap();

    }

    public Map<String, Object> toMap() {
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
