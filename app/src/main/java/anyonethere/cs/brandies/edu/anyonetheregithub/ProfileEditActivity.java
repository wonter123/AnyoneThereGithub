package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chensi on 11/30/17.
 */

public class ProfileEditActivity extends AppCompatActivity {

    private FirebaseUser user;
    private String email;
    private String phone;
    private String name;
    private int photoId;
    private int credit;
    private int rating;
    private int post_number;
    private int accomplished_number;
    private String currentUID;

    private TextView profileName;
    private TextView profileEmail;
    private TextView profileCoin;
    private ImageView profilePhoto;
    private RatingBar profileRating;
    private TextView profileTaskAccomplished;
    private TextView profileTaskPoseted;
    private EditText phoneText;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference parentDatabase;
    private DatabaseReference curUserReference;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_edit);

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

        mAuth = FirebaseAuth.getInstance();
        currentUID = mAuth.getCurrentUser().getUid();

        // get current user's name for further search in database
        email = mAuth.getCurrentUser().getEmail();

        parentDatabase = FirebaseDatabase.getInstance().getReference();

        // get the database information
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUID);

        // extract user info from database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(ProfileEditActivity.this, dataSnapshot.child("username").getValue().toString(), Toast.LENGTH_SHORT).show();
                currentUser = dataSnapshot.getValue(User.class);

                // set all fields from database
                profileName = (TextView) findViewById(R.id.profile_edit_name);
                profileEmail = (TextView) findViewById(R.id.profile_edit_email);
                phoneText = (EditText) findViewById(R.id.profile_edit_phone);
                profilePhoto = (ImageView) findViewById(R.id.profile_edit_photo);
                profileCoin = (TextView) findViewById(R.id.profile_edit_credit);
                profileRating = (RatingBar) findViewById(R.id.profile_edit_ratingBar);
                profileTaskAccomplished = (TextView) findViewById(R.id.profile_edit_accomplished_number);
                profileTaskPoseted = (TextView) findViewById(R.id.profile_edit_post_number);

                // set the field of user
                name = currentUser.getUsername();
                email = currentUser.getEmail();
                phone = currentUser.getPhone();
                photoId = currentUser.getPhotoId();
                rating = currentUser.getRating();
                credit = currentUser.getCredit();
                accomplished_number = currentUser.getTask_accomplished();
                post_number = currentUser.getTask_posted();

                // set each view with input information
                profileName.setText(name);
                profileEmail.setText(email);
                profileCoin.setText(Integer.toString(credit));
                phoneText.setText(phone);
                profilePhoto.setImageDrawable(getResources().getDrawable(userHeadsId[photoId]));
                profileRating.setNumStars(rating);
                profileTaskAccomplished.setText(Integer.toString(accomplished_number));
                profileTaskPoseted.setText(Integer.toString(post_number));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // click cancel button
        Button cancelButton = (Button) findViewById(R.id.profile_edit_cancelbutton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(cancelIntent);
            }
        });

        // click save button
        Button saveButton = (Button) findViewById(R.id.profile_edit_savebutton);
        // save input phone into database
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneText = (EditText) findViewById(R.id.profile_edit_phone);
                phone = phoneText.getText().toString();
                mDatabase.child("phone").setValue(phone, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d("!!!!!Warning: ", "SAVED WITH ERROR");
                        } else {
                            Log.d("!!!!!Warning: ", "SAVED SUCCESS");
                        }
                    }
                });

                Intent saveIntent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(saveIntent);

            }
        });
    }

}
