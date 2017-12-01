package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by chensi on 11/30/17.
 */

public class ProfileActivity extends AppCompatActivity {

    String name;
    String email;
    String phoneNumber;
    String photoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        Bundle bundle = getIntent().getExtras();

        TextView profileName = (TextView) findViewById(R.id.profile_name);
        TextView profileEmail = (TextView) findViewById(R.id.profile_email);
        ImageView profilePhoto = (ImageView) findViewById(R.id.profile_photo);
        TextView profilePhone = (TextView) findViewById(R.id.profile_phone);

        // set each view with input information
        profileName.setText(bundle.getString("name"));
        profileEmail.setText(bundle.getString("email"));
        profilePhone.setText(bundle.getString("phone"));
//        profilePhoto.setImageURI(Uri.parse(bundle.getString("photo"))); // since current no uri for photo

    }
}
