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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference mDatabase;

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;

    NavigationView navigationView;
    View menuheader;
    FirebaseUser user;
    TextView userName;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // set the user profile in menu header
        user = FirebaseAuth.getInstance().getCurrentUser();

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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppAdaptor adp = new AppAdaptor();
                ListView mainListView = (ListView) findViewById( R.id.listlog);
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    String key = uniqueUserSnapshot.getKey();
                    Post post = uniqueUserSnapshot.getValue(Post.class);
                    adp.addIn(post,key);
                }
                mainListView.setAdapter(adp);
            }


            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.w("loadPost:onCancelled", firebaseError.toException());
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
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_post) {
            // Handle the post new request action
            Intent intent = new Intent(MainActivity.this, PostRequestActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_myposts) {
            Intent intent = new Intent(MainActivity.this, RequestListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mytasks) {

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

    class AppAdaptor extends BaseAdapter {
        ArrayList<Post> arrlist;
        ArrayList<String> key;

        public AppAdaptor() {
            super();
            key = new ArrayList<>();
            arrlist = new ArrayList<>();
        }

        public void addIn(Post post, String keys) {
            arrlist.add(post);
            key.add(keys);

        }

        @Override
        public int getCount() {
            return arrlist.size();
        }

        @Override
        public Object getItem(int index) {
            return arrlist.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup parent){

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.request_list, parent, false);

            TextView heading,requester,reward;
            heading = (TextView) row.findViewById(R.id.heading);
            requester = (TextView) row.findViewById(R.id.Requester);
            reward = (TextView) row.findViewById(R.id.reward);

            RatingBar rate = (RatingBar) row.findViewById(R.id.userRating);

            final String k = key.get(index);

            Post post = arrlist.get(index);
            Log.d("Main Activity: ", "main activity getView");

            heading.setText("Title:  "+post.title);
            requester.setText("From:  "+post.from);
            reward.setText("Reward:  "+post.reward+"");

            rate.setRating(post.rating);

            Button button = (Button) row.findViewById(R.id.detail);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,TakeRequestActivity.class);
                    intent.putExtra("key",k);
                    Log.d("Main Activity: ", "main activity onClick");
                    startActivity(intent);
                }
            });

            return (row);
        }
    }
}
