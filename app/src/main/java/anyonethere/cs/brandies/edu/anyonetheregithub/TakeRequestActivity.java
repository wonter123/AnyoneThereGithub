package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TakeRequestActivity extends AppCompatActivity {

    Intent intent;
    private String postId;
    private DatabaseReference mDatabase;
    private DatabaseReference curtPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_request);

        drawPost();
        //drawPoster();
        cancel();
        take();
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
            public void onClick(View view) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            curtPost.child("takerId").setValue(userId);
            finish();
            }
        });
    }

    void drawPost() {
        intent = getIntent();
        if (intent.getExtras() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            postId = intent.getExtras().getString("key");
            mDatabase = FirebaseDatabase.getInstance().getReference("posts");
            curtPost = mDatabase.child(postId);

            curtPost.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Post p = dataSnapshot.getValue(Post.class);

                    TextView header = (TextView) findViewById(R.id.takeRequest_headContent);
                    TextView reward = (TextView) findViewById(R.id.takeRequest_rewardContent);
                    TextView from = (TextView) findViewById(R.id.takeRequest_fromContent);
                    TextView to = (TextView) findViewById(R.id.takeRequest_toContent);
                    TextView expireDate = (TextView) findViewById(R.id.takeRequest_ExpireContent);
                    TextView description = (TextView) findViewById(R.id.takeRequest_descriptionContent);

                    header.setText(p.title);
                    reward.setText(p.reward);
                    description.setText(p.description);
                    expireDate.setText(p.expireDate.toString());
                    from.setText(p.from);
                    to.setText(p.to);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }

    // waiting support for user profile
    void drawPoster() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference curtUser = FirebaseDatabase.getInstance().getReference("posts");
        DatabaseReference user = curtUser.child(userId);
    }
}