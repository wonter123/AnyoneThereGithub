package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = "lalala";
        // make a change
        System.out.print("Hello");
        Log.w("test", "test");
    }

}
