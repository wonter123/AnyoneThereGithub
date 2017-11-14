package anyonethere.cs.brandies.edu.anyonetheregithub;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phoebez on 14.11.17.
 */

public class Post {
    private String title;
    private int reward;
    private String description;
    private int hour;
    private int minute;
    private int day;
    private String from;
    private String to;

    public Post(String title, int reward, String description, int day,
                int hour, int minute, String from, String to) {
        this.title = title;
        this.reward = reward;
        this.description = description;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.from = from;
        this.to = to;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("reward", reward);
        result.put("description", description);
        result.put("day", day);
        result.put("hour", hour);
        result.put("minute", minute);
        result.put("to", to);
        result.put("from", from);

        return result;
    }
}
