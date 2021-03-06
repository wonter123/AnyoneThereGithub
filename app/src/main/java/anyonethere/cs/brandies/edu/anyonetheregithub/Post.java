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
    public int rating;
    public int imageID;
    public int postState;
    public HashMap<String, Object> result;
    public String takerName = null;
    public String posterName;

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
        this.postState = 0;
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
        result.put("imageID", imageID);
        result.put("to", to);
        result.put("from", from);
        result.put("posterId", posterId);
        result.put("takerId", takerId);
//        result.put("status", status);
        result.put("postState", postState);
        result.put("takerName", takerName);
        result.put("posterName", posterName);
        result.put("rating", rating);
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

//    public void setStatus(boolean boo) {
//        status = boo;
//        result.put("status", status);
//    }
//
//    public void setPostComplete() {
//        setStatus(false);
//    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public void setPostState(int newState){
        this.postState = newState;
    }

    public String  toString(){
        return this.title+" "+this.description;
    }

    public String getFrom(){
        return this.from;
    }

    public String getTitle(){
        return title;
    }
    public String getPosterId(){
        return posterId;
    }
    public int getReward(){
        return reward;
    }

    public String getDescription() {
        return description;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public String getTo() {
        return to;
    }

    public String getTakerId() {
        return takerId;
    }

    public int getRating() {
        return rating;
    }

    public int getImageID() {
        return imageID;
    }

    public int getPostState() {return this.postState;
    }
}
