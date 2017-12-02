package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
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

    FirebaseUser user;
    String email;
    String phone;
    String name;
    int rating;
    int post_number;
    int accomplished_number;

    EditText phoneText;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    DatabaseReference curUserReference;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_edit);

        // get the database information
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        // get current user's name for further search in database
        email = mAuth.getCurrentUser().getEmail();

        // extract user info from database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    currentUser = ds.getValue(User.class);

                    if(currentUser.getEmail().equals(email)){
                        // set all fields from database
                        TextView profileName = (TextView) findViewById(R.id.profile_edit_name);
                        TextView profileEmail = (TextView) findViewById(R.id.profile_edit_email);
                        phoneText = (EditText) findViewById(R.id.profile_edit_phone);
                        RatingBar profileRating = (RatingBar) findViewById(R.id.profile_edit_ratingBar);
                        TextView profileTaskAccomplished = (TextView) findViewById(R.id.profile_edit_accomplished_number);
                        TextView profileTaskPoseted = (TextView) findViewById(R.id.profile_edit_post_number);

                        // set the field of user
                        name = currentUser.getUsername();
                        email = currentUser.getEmail();
                        phone = currentUser.getPhone();
                        rating = currentUser.getRating();
                        accomplished_number = currentUser.getTask_accomplished();
                        post_number = currentUser.getTask_posted();

                        // set each view with input information
                        profileName.setText(name);
                        profileEmail.setText(email);
                        phoneText.setText(phone);
                        profileRating.setNumStars(rating);
                        profileTaskAccomplished.setText(Integer.toString(accomplished_number));
                        profileTaskPoseted.setText(Integer.toString(post_number));

                        break;
                    }
                }
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
        // set the edit field with old data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User tmp = ds.getValue(User.class);
                            if (tmp.getEmail().equals(email)) {
                                String key = ds.getKey();

                                phoneText = (EditText) findViewById(R.id.profile_edit_phone);
                                String phone = phoneText.getText().toString();

                                curUserReference = mDatabase.child(key);
                                curUserReference.child("phone").setValue(phone);

                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent saveIntent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(saveIntent);

            }
        });
    }

}
