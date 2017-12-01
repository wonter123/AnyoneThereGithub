package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chensi on 11/30/17.
 */

public class ProfileEditActivity extends AppCompatActivity {

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_edit);

        Button cancelButton = (Button) findViewById(R.id.profile_edit_cancelbutton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(cancelIntent);
            }
        });

        Button saveButton = (Button) findViewById(R.id.profile_edit_savebutton);
        EditText emailText = (EditText) findViewById(R.id.profile_edit_email);
        EditText phoneText = (EditText) findViewById(R.id.profile_edit_phone);
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent saveIntent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                startActivity(saveIntent);
            }
        });
    }

}
