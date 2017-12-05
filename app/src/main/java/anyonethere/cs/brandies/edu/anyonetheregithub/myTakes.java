package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class myTakes extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_takes);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cancel();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppAdaptor adp = new AppAdaptor();
                ListView mainListView = (ListView) findViewById( R.id.myTakes_list);
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    String key = uniqueUserSnapshot.getKey();
                    Post post = uniqueUserSnapshot.getValue(Post.class);
                    if (post.takerId != null && post.takerId.equals(uid)) {
                        adp.addIn(post,key);
                    }
                }
                mainListView.setAdapter(adp);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.w("loadPost:onCancelled", firebaseError.toException());
            }
        });
    }

    // if cancel is clicked, do nothing
    void cancel() {
        Button cancel = (Button)findViewById(R.id.myTakes_back);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class AppAdaptor extends BaseAdapter {
        ArrayList<Post> arrlist;
        ArrayList<String> key;

        public AppAdaptor() {
            super();
            key = new ArrayList<>();
            arrlist = new ArrayList<>();
        }

        public void addIn(Post post,String keys) {
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
            View row = inflater.inflate(R.layout.activity_my_take_entry, parent, false);

            ImageView iv = (ImageView) row.findViewById(R.id.taken_stamp);
            int state = arrlist.get(index).postState;
            if(state == 1) iv.setImageResource(R.drawable.stamp_taken);
            else if(state == 2) iv.setImageResource(R.drawable.stamp_completed);
            TextView heading,reward;
            heading = (TextView) row.findViewById(R.id.taken_heading);
            final TextView poster_phone = (TextView) row.findViewById(R.id.taken_phone);
            reward = (TextView) row.findViewById(R.id.taken_reward);

            final String k = key.get(index);

            Post post = arrlist.get(index);

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
            ImageView userPhoto = (ImageView) row.findViewById(R.id.taken_userID);
            userPhoto.setImageResource(userHeadsId[post.getImageID()]);

            RatingBar ratingBar = (RatingBar)row.findViewById(R.id.taken_ratingbar);
            ratingBar.setRating(post.getRating());

            heading.setText(post.title);
            reward.setText("Reward: " + post.reward+"");
            mDatabase = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference p = mDatabase.child(post.posterId);

            p.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User poster = dataSnapshot.getValue(User.class);

                    poster_phone.setText("Requester phone: "+poster.getPhone());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            try {
                Button button = (Button) row.findViewById(R.id.taken_detail);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(myTakes.this,TakeRequestActivity.class);
                        intent.putExtra("key",k);
                        startActivity(intent);
                    }
                });
            } catch (RuntimeException e) {}
            return (row);
        }
    }
}