package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TakeRequestActivity extends AppCompatActivity {

    Intent intent;
    String postId;
    int postStatus;
    DatabaseReference mDatabase;
    DatabaseReference curtPost;
    final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    final String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


    Button take;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_request);
        init();
    }

    void init() {
        take = (Button)findViewById(R.id.takeRequest_take);
        call = (Button)findViewById(R.id.takeRequest_call);
        drawPost();
        drawPoster();
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
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curtPost.child("takerId").setValue(userId);
                curtPost.child("postState").setValue(1);
                curtPost.child("takerName").setValue(userEmail);
                finish();
            }
        });
    }

    void drawPost() {
        intent = getIntent();
        if (intent.getExtras() != null) {
            postId = intent.getExtras().getString("key");
            postStatus = intent.getExtras().getInt("state");
            mDatabase = FirebaseDatabase.getInstance().getReference("posts");
            curtPost = mDatabase.child(postId);

            curtPost.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Post p = dataSnapshot.getValue(Post.class);
                    if (p != null && p.postState != 0) {  // bug here
                        take.setVisibility(View.GONE);
                    } else {
                        take();
                    }

                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(p.takerId)) {
                        call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference poster = FirebaseDatabase.getInstance().getReference("users").child(p.posterId);

                                poster.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String phone = dataSnapshot.getValue(User.class).getPhone();
                                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                finish();
                            }
                        });
                    } else {
                        call.setVisibility(View.GONE);
                    }

                    TextView header = (TextView) findViewById(R.id.takeRequest_headContent);
                    TextView reward = (TextView) findViewById(R.id.takeRequest_rewardContent);
                    TextView from = (TextView) findViewById(R.id.takeRequest_fromContent);
                    TextView to = (TextView) findViewById(R.id.takeRequest_toContent);
                    TextView expireDate = (TextView) findViewById(R.id.takeRequest_ExpireContent);
                    TextView description = (TextView) findViewById(R.id.takeRequest_descriptionContent);

                    header.setText(p.title);
                    reward.setText("" + p.reward);
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
        DatabaseReference curtUser = FirebaseDatabase.getInstance().getReference("posts");
        DatabaseReference user = curtUser.child(userId);
    }
}