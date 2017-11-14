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

    public Post(String title, int reward, String description, Date postDate,
                Date expireDate, String from, String to) {
        this.title = title;
        this.reward = reward;
        this.description = description;
        this.postDate = postDate;
        this.expireDate = expireDate;
        this.from = from;
        this.to = to;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("reward", reward);
        result.put("description", description);
        result.put("postDate", postDate);
        result.put("expireDate", expireDate);
        result.put("to", to);
        result.put("from", from);

        return result;
    }
}
