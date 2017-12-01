package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;


/**
 * Created by chensi on 11/30/17.
 */

public class ProfileActivity extends AppCompatActivity {

    String name;
    String email;
    String phone;
    String photoURL;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        Bundle bundle = getIntent().getExtras();

        TextView profileName = (TextView) findViewById(R.id.profile_name);
        TextView profileEmail = (TextView) findViewById(R.id.profile_email);
        ImageView profilePhoto = (ImageView) findViewById(R.id.profile_photo);
        TextView profilePhone = (TextView) findViewById(R.id.profile_phone);

        name = user.getEmail().split("@")[0];
        email = user.getEmail();
        phone = user.getPhoneNumber();

        // set each view with input information
        profileName.setText(name);
        profileEmail.setText(email);
        profilePhone.setText(phone);
//        profilePhoto.setImageURI(Uri.parse(bundle.getString("photo"))); // since current no uri for photo

        Button editButton = (Button) findViewById(R.id.profile_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                startActivity(editIntent);
            }
        });

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
