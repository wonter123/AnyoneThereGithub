package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;

    NavigationView navigationView;
    View menuheader;
    TextView userName;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        ((TextView) findViewById(R.id.nav_username)).setText(user.getDisplayName());
//        ((TextView) findViewById(R.id.nav_email)).setText(user.getEmail());

        // set the user profile in menu header
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        menuheader = navigationView.getHeaderView(0);

        if (menuheader != null) {
            userEmail = (TextView) menuheader.findViewById(R.id.nav_email);
            userName = (TextView) menuheader.findViewById(R.id.nav_username);
            String email = user.getEmail();
            String name = email.split("@")[0];

            // load menu navigation data
            userName.setText(name);
            userEmail.setText(email);
        } else {
            Log.d("Warning", "menu header is null");
        }


        // set tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // set button to map
        FloatingActionButton mapButton = (FloatingActionButton) findViewById(R.id.main_mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
        });


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the profile action


        } else if (id == R.id.nav_post) {
            // Handle the post new request action
            Intent intent = new Intent(MainActivity.this, PostRequestActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_management) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
