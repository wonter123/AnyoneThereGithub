package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PostRequestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] locations;
    String[] days;
    String[] hours;
    String[] minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent data = new Intent();
                data.putExtra("title", ((EditText) findViewById(R.id.request_title_box)).getText().toString());
                data.putExtra("reward", ((EditText) findViewById(R.id.request_reward_box)).getText().toString());
                data.putExtra("description", ((EditText) findViewById(R.id.request_description_box)).getText().toString());
                data.putExtra("day", ((Spinner) findViewById(R.id.post_request_day)).getSelectedItem().toString());
                data.putExtra("hour", ((Spinner) findViewById(R.id.post_request_hour)).getSelectedItem().toString());
                data.putExtra("minute", ((Spinner) findViewById(R.id.post_request_minute)).getSelectedItem().toString());
                data.putExtra("from", ((Spinner) findViewById(R.id.post_request_from)).getSelectedItem().toString());
                data.putExtra("to", ((Spinner) findViewById(R.id.post_request_to)).getSelectedItem().toString());
                setResult(RESULT_OK, data);
                finish();
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
