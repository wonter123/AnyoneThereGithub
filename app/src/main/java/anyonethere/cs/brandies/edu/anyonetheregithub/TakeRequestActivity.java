package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TakeRequestActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_request);

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
        Button take = (Button)findViewById(R.id.takeRequest_take);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            // set takerID to post, and re-save in FireBase
            // databaseReference.child("taker").setValue(userId);
            finish();
            }
        });
    }
}
