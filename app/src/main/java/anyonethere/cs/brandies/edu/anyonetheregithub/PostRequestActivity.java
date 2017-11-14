package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class PostRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_request);

        // Create spinner
        Spinner spinner_from = (Spinner) findViewById(R.id.post_request_from);
        Spinner spinner_to = (Spinner) findViewById(R.id.post_request_to);
        Spinner spinner_day = (Spinner) findViewById(R.id.post_request_day);
        Spinner spinner_hour = (Spinner) findViewById(R.id.post_request_hour);
        Spinner spinner_minute = (Spinner) findViewById(R.id.post_request_minute);

        // Spinner Click Listener
        spinner_from.set



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

}
