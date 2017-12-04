package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabase;
    private FirebaseAuth mAuth;
    private String currentUID;

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;

    NavigationView navigationView;
    View menuheader;
    FirebaseUser user;
    TextView userName;
    TextView userEmail;
    ImageView userPhoto;

    String name;
    String email;
    int photoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for photo getter
        final int[] userHeadsId = new int[10];
        userHeadsId[0] = R.drawable.user_head_1;
        userHeadsId[1] = R.drawable.user_head_2;
        userHeadsId[2] = R.drawable.user_head_3;
        userHeadsId[3] = R.drawable.user_head_4;
        userHeadsId[4] = R.drawable.user_head_5;
        userHeadsId[5] = R.drawable.user_head_6;
        userHeadsId[6] = R.drawable.user_head_7;
        userHeadsId[7] = R.drawable.user_head_8;
        userHeadsId[8] = R.drawable.user_head_9;
        userHeadsId[9] = R.drawable.user_head_10;

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // set the user profile in menu header
        user = FirebaseAuth.getInstance().getCurrentUser();

        menuheader = navigationView.getHeaderView(0);

        if (menuheader != null) {
            userEmail = (TextView) menuheader.findViewById(R.id.nav_email);
            userName = (TextView) menuheader.findViewById(R.id.nav_username);
            userPhoto = (ImageView) menuheader.findViewById(R.id.nav_photo);
//            String email = user.getEmail();
//            String name = email.split("@")[0];

            // get current user's name for further search in database
            mAuth = FirebaseAuth.getInstance();
            currentUID = mAuth.getCurrentUser().getUid();
            userDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUID);
            userDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    photoId = Integer.valueOf(dataSnapshot.child("photoId").getValue().toString());
                    Toast.makeText(MainActivity.this, Integer.toString(photoId), Toast.LENGTH_LONG);
                    userPhoto.setImageDrawable(getResources().getDrawable(userHeadsId[photoId]));
                    // load menu navigation data
                    name = dataSnapshot.child("username").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    userName.setText(name);
                    userEmail.setText(email);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


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
        ImageButton mapButton = (ImageButton) findViewById(R.id.activity_main_map_button);
        ImageButton newPostButton = (ImageButton) findViewById(R.id.activity_main_newpost_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
        });
        newPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPostIntent = new Intent(MainActivity.this, PostRequestActivity.class);
                startActivity(newPostIntent);
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
                    if (post.postState > 1) {
                        continue;
                    };
                    adp.addIn(post,key);
                }
                mainListView.setAdapter(adp);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.w("loadPost:onCancelled", firebaseError.toException());
            }
        });

        final SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                            String key = uniqueUserSnapshot.getKey();
                            Post post = uniqueUserSnapshot.getValue(Post.class);
                            if (post.expireDate.before(new Date())) {
                                post.setPostState(3);
                                mDatabase.child(key).child("postState").setValue(3);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        Log.w("loadPost:onCancelled", firebaseError.toException());
                    }
                });
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

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
            Intent intent = new Intent(MainActivity.this, myPosts.class);
            startActivity(intent);
        } else if (id == R.id.nav_mytasks) {
            Intent intent = new Intent(MainActivity.this, myTakes.class);
            startActivity(intent);
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
        ArrayList<Integer> states;

        public AppAdaptor() {
            super();
            key = new ArrayList<>();
            arrlist = new ArrayList<>();
            states = new ArrayList<Integer>();
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

            ImageView iv = (ImageView) row.findViewById(R.id.detail_statusimg);
            //int state = states.get(index);
            int state = arrlist.get(index).postState;
            if(state == 1) iv.setImageResource(R.drawable.stamp_taken);
            else if(state == 2) iv.setImageResource(R.drawable.stamp_completed);

            TextView heading,requester,reward;
            heading = (TextView) row.findViewById(R.id.entry_title);
            requester = (TextView) row.findViewById(R.id.entry_poster);
            reward = (TextView) row.findViewById(R.id.entry_reward);

            RatingBar rate = (RatingBar) row.findViewById(R.id.userRating);

            final String k = key.get(index);

            Post post = arrlist.get(index);
            Log.d("Main Activity: ", "main activity getView");

            heading.setText("Title:  " + post.title);
            requester.setText("From:  " + post.from);
            reward.setText("Reward:  " + post.reward + "");

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