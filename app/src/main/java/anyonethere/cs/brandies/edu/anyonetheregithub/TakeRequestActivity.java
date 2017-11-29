package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TakeRequestActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_request);

        cancel();

    }

    // if cancel is clicked, do nothing
    void cancel() {
        Button cancel = (Button)findViewById(R.id.takeRequest_cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void take() {
        Button take = (Button)findViewById(R.id.takeRequest_take);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mDatabase.child("posts").push().getKey();

                String title = ((EditText) findViewById(R.id.request_title_box)).getText().toString();
                String reward = ((EditText) findViewById(R.id.request_reward_box)).getText().toString();
                String description = ((EditText) findViewById(R.id.request_description_box)).getText().toString();
                int day = Integer.parseInt(((Spinner) findViewById(R.id.post_request_day)).getSelectedItem().toString());
                int hour = Integer.parseInt(((Spinner) findViewById(R.id.post_request_hour)).getSelectedItem().toString());
                int minute = Integer.parseInt(((Spinner) findViewById(R.id.post_request_minute)).getSelectedItem().toString());
                String from = ((Spinner) findViewById(R.id.post_request_from)).getSelectedItem().toString();
                String to = ((Spinner) findViewById(R.id.post_request_to)).getSelectedItem().toString();

                Calendar cal = Calendar.getInstance();
                Date current = new Date();
                cal.setTime(current);
                cal.add(Calendar.DATE, day);
                cal.add(Calendar.HOUR, hour);
                cal.add(Calendar.MINUTE, minute);
                Date expire = cal.getTime();

                Post newPost = new Post(title, Integer.parseInt(reward), description, current,
                        expire, from, to);

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
