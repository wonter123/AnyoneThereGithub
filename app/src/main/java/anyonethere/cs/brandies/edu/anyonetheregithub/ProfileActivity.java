package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.w3c.dom.Text;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        // get current user's name for further search in database
        mAuth = FirebaseAuth.getInstance();
        currentUID = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();

        // get the database information
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUID);
        // extract user info from database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);

                // set all fields from database
                TextView profileName = (TextView) findViewById(R.id.profile_name);
                TextView profileEmail = (TextView) findViewById(R.id.profile_email);
                TextView profilePhone = (TextView) findViewById(R.id.profile_phone);
                RatingBar profileRating = (RatingBar) findViewById(R.id.profile_ratingBar);
                TextView profileCredit = (TextView) findViewById(R.id.profile_credit);
                TextView profileTaskAccomplished = (TextView) findViewById(R.id.profile_accomplished_number);

                // set each view with input information
                profileName.setText(currentUser.getUsername());
                profileEmail.setText(currentUser.getEmail());
                profilePhone.setText(currentUser.getPhone());
                profileCredit.setText(Integer.toString(currentUser.getCredit()));
                profileRating.setNumStars(currentUser.getRating());
                profileTaskAccomplished.setText(Integer.toString(currentUser.getTask_accomplished()));
                Toast.makeText(ProfileActivity.this, "POST: ", Toast.LENGTH_LONG);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mrDatabase = FirebaseDatabase.getInstance().getReference("posts");
        mrDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int accomplished = 0;
                int posted = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    post = ds.getValue(Post.class);
//                    Toast.makeText(ProfileActivity.this, "POST: " + post.toString(), Toast.LENGTH_LONG).show();
//                    if (post.getPosterId().equals(currentUID)) posted++;
                }
//                TextView profileTaskPosted = (TextView) findViewById(R.id.profile_post_number);
//                profileTaskPosted.setText(Integer.toString(currentUser.getTask_posted()));
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
