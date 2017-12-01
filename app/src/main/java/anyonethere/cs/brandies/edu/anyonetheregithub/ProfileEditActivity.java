package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chensi on 11/30/17.
 */

public class ProfileEditActivity extends AppCompatActivity {

    FirebaseUser user;
    String email;
    String phone;
    EditText emailText;
    EditText phoneText;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_edit);

        mAuth = FirebaseAuth.getInstance();

        // set the user profile in menu header
        user = FirebaseAuth.getInstance().getCurrentUser();

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
        emailText = (EditText) findViewById(R.id.profile_edit_email);
        phoneText = (EditText) findViewById(R.id.profile_edit_phone);
        email = user.getEmail();
        phone = user.getPhoneNumber();
        emailText.setText(email);
        phoneText.setText(phone);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set email and phone as new input data
                email = emailText.getText().toString();
                phone = phoneText.getText().toString();

                Intent saveIntent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(saveIntent);
            }
        });
    }

}
