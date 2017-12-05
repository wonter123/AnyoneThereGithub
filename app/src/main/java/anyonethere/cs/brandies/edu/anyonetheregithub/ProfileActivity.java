package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by chensi on 11/30/17.
 */

public class ProfileActivity extends AppCompatActivity {

    private String email;
    private String currentUID;
    private DatabaseReference mDatabase;
    private DatabaseReference mrDatabase;
    private FirebaseAuth mAuth;
    private User currentUser;
    private Post post;
    boolean localFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        final int[] userHeadsId = new int[10];
        userHeadsId[0] = R.drawable.user_head_1;
        userHeadsId[1] = R.drawable.user_head_2;
        userHeadsId[2] = R.drawable.user_head_3;
        userHeadsId[3] = R.drawable.user_head_4;
        userHeadsId[4] = R.drawable.user_head_5;
        userHeadsId[5] = R.drawable.user_head_6;
        userHeadsId[6] = R.drawable.user_head_7;
        userHeadsId[7] = R.drawable.user_head_8;
        userHeadsId[8] = R.drawable.user_head_9;
        userHeadsId[9] = R.drawable.user_head_10;

        // get current user's name for further search in database
        mAuth = FirebaseAuth.getInstance();
        String intentString = getIntent().getStringExtra("userid");
        if (intentString == null)  currentUID = mAuth.getCurrentUser().getUid();
        else {
                currentUID = getIntent().getStringExtra("userid");
                localFlag = false;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot userData = dataSnapshot.child("users");
                DataSnapshot postData = dataSnapshot.child("posts");
                DataSnapshot curUserData = userData.child(currentUID);
                currentUser = curUserData.getValue(User.class);

                int accomplished_num = 0;
                int post_num = 0;
                for (DataSnapshot ds : postData.getChildren()) {
                    if (ds.child("posterId").getValue().toString().equals(currentUID)) {
                        post_num++;
                    }
                    if (ds.child("takerId").getValue() != null &&
                            ds.child("takerId").getValue().toString().equals(currentUID) &&
                            Integer.valueOf(ds.child("postState").getValue().toString()) == 2) {
                        accomplished_num++;
                    }
                }

                // set all fields from database
                TextView profileName = (TextView) findViewById(R.id.profile_name);
                TextView profileEmail = (TextView) findViewById(R.id.profile_email);
                TextView profilePhone = (TextView) findViewById(R.id.profile_phone);
                ImageView profilePhoto = (ImageView) findViewById(R.id.profile_photo);
                RatingBar profileRating = (RatingBar) findViewById(R.id.profile_ratingBar);
                TextView profileCredit = (TextView) findViewById(R.id.profile_credit);
                TextView profileTaskAccomplished = (TextView) findViewById(R.id.profile_accomplished_number);
                TextView profileTaskPosted = (TextView) findViewById(R.id.profile_post_number);

                // set each view with input information
                profileName.setText(currentUser.getUsername());
                profileEmail.setText(currentUser.getEmail());
                if (currentUID.equals(mAuth.getCurrentUser().getUid())) profilePhone.setText(currentUser.getPhone());
                else {
                    Toast.makeText(ProfileActivity.this, "herhe", Toast.LENGTH_LONG).show();
                    profilePhone.setText("xxx-xxx-xxxx");
                    Button saveButton = (Button) findViewById(R.id.profile_edit_button);
                    saveButton.setVisibility(View.GONE);
                }
                profilePhoto.setImageDrawable(getResources().getDrawable(userHeadsId[currentUser.getPhotoId()]));
                profileCredit.setText(Integer.toString(currentUser.getCredit()));
                profileRating.setNumStars(currentUser.getRating());
                profileTaskAccomplished.setText(Integer.toString(accomplished_num));
                profileTaskPosted.setText(Integer.toString(post_num));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // GOTO EDIT
        Button editButton = (Button) findViewById(R.id.profile_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                startActivity(editIntent);
            }
        });

        // GOTO BACK
        Button backButton = (Button) findViewById(R.id.profile_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });

    }

}
