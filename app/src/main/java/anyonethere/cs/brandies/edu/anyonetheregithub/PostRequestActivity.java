package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostRequestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] locations;
    String[] days;
    String[] hours;
    String[] minutes;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.post_request);

        // get the array of locations
        locations = getResources().getStringArray(R.array.locations);

        // get the array of days
        days = getResources().getStringArray(R.array.day);

        // get the array of hours
        hours = getResources().getStringArray(R.array.hour);

        // get the array of minutes
        minutes = getResources().getStringArray(R.array.minute);

        // Create spinner
        Spinner spinner_from = (Spinner) findViewById(R.id.post_request_from);
        Spinner spinner_to = (Spinner) findViewById(R.id.post_request_to);
        Spinner spinner_day = (Spinner) findViewById(R.id.post_request_day);
        Spinner spinner_hour = (Spinner) findViewById(R.id.post_request_hour);
        Spinner spinner_minute = (Spinner) findViewById(R.id.post_request_minute);

        // Spinner Click Listener
        spinner_from.setOnItemSelectedListener(this);
        spinner_to.setOnItemSelectedListener(this);
        spinner_day.setOnItemSelectedListener(this);
        spinner_hour.setOnItemSelectedListener(this);
        spinner_minute.setOnItemSelectedListener(this);

        // Create adapter for spinner
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hours);
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutes);

        // Drop down layout style
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attach adapter to spinner
        spinner_from.setAdapter(fromAdapter);
        spinner_to.setAdapter(toAdapter);
        spinner_day.setAdapter(dayAdapter);
        spinner_hour.setAdapter(hourAdapter);
        spinner_minute.setAdapter(minuteAdapter);

        findViewById(R.id.request_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.request_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = ((EditText) findViewById(R.id.request_title_box)).getText().toString();
                String reward = ((EditText) findViewById(R.id.request_reward_box)).getText().toString();

                if (title.trim().equals(""))
                    ((EditText) findViewById(R.id.request_title_box)).setError("Please enter a title for your request.");
                else if (reward.trim().equals("") || !reward.trim().matches("[-+]?\\d*\\.?\\d+"))
                    ((EditText) findViewById(R.id.request_reward_box)).setError("Please enter a valid reward amount.");
                else {

                    String key = mDatabase.child("posts").push().getKey();

                    String description = ((EditText) findViewById(R.id.request_description_box)).getText().toString();
                    int day = Integer.parseInt(((Spinner) findViewById(R.id.post_request_day)).getSelectedItem().toString().replaceAll("[^0-9]", ""));
                    int hour = Integer.parseInt(((Spinner) findViewById(R.id.post_request_hour)).getSelectedItem().toString().replaceAll("[^0-9]", ""));
                    int minute = Integer.parseInt(((Spinner) findViewById(R.id.post_request_minute)).getSelectedItem().toString().replaceAll("[^0-9]", ""));
                    String from = ((Spinner) findViewById(R.id.post_request_from)).getSelectedItem().toString();
                    String to = ((Spinner) findViewById(R.id.post_request_to)).getSelectedItem().toString();

                    Calendar cal = Calendar.getInstance();
                    Date current = new Date();
                    cal.setTime(current);
                    cal.add(Calendar.DATE, day);
                    cal.add(Calendar.HOUR, hour);
                    cal.add(Calendar.MINUTE, minute);
                    Date expire = cal.getTime();

                    Post newPost = new Post(title, Integer.parseInt(reward), description, current,
                            expire, from, to);
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    newPost.setPoster(userId);

                    Map<String, Object> postValues = newPost.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/posts/" + key, postValues);
                    childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

                    mDatabase.updateChildren(childUpdates);
                    finish();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // on selected item
        String item = parent.getItemAtPosition(position).toString();

        // Toast selected item
        Toast.makeText(parent.getContext(), "selected: "+ item, Toast.LENGTH_SHORT).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }



}
