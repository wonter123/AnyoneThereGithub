package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class PostRequestActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_request);

        findViewById(R.id.request_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.request_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mDatabase.child("posts").push().getKey();
                String title = ((EditText) findViewById(R.id.request_title_box)).getText().toString();
                String reward = ((EditText) findViewById(R.id.request_reward_box)).getText().toString();
                String description = ((EditText) findViewById(R.id.request_description_box)).getText().toString();
                String day = ((Spinner) findViewById(R.id.post_request_day)).getSelectedItem().toString();
                String hour = ((Spinner) findViewById(R.id.post_request_hour)).getSelectedItem().toString();
                String minute = ((Spinner) findViewById(R.id.post_request_minute)).getSelectedItem().toString();
                String from = ((Spinner) findViewById(R.id.post_request_from)).getSelectedItem().toString();
                String to = ((Spinner) findViewById(R.id.post_request_to)).getSelectedItem().toString();
                Post newPost = new Post(title, Integer.parseInt(reward), description, Integer.parseInt(day),
                        Integer.parseInt(hour), Integer.parseInt(minute), from, to);
                Map<String, Object> postValues = newPost.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                childUpdates.put("/posts/" + key, postValues);
                childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

                mDatabase.updateChildren(childUpdates);
                finish();
            }
        });
    }

}