package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.app.Dialog;
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


public class myPosts extends AppCompatActivity {
    private DatabaseReference mDatabase;
    DatabaseReference curtPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cancel();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");

        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppAdaptor adp = new AppAdaptor();
                ListView mainListView = (ListView) findViewById( R.id.myPosts_list);
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    String key = uniqueUserSnapshot.getKey();
                    Post post = uniqueUserSnapshot.getValue(Post.class);
                    if (post.posterId != null && post.posterId.equals(uid)) {
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
        Button cancel = (Button)findViewById(R.id.myPosts_back);
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

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.activity_my_post_entry, parent, false);

            ImageView iv = (ImageView) row.findViewById(R.id.detail_statusimg);
            //int state = states.get(index);
            int state = arrlist.get(index).postState;
            if(state == 1) iv.setImageResource(R.drawable.stamp_taken);
            else if(state == 2) iv.setImageResource(R.drawable.stamp_completed);

            TextView heading,requester,reward;
            heading = (TextView) row.findViewById(R.id.entry_title);
            requester = (TextView) row.findViewById(R.id.entry_poster);
            reward = (TextView) row.findViewById(R.id.entry_reward);
            ImageView userPhoto = (ImageView) row.findViewById(R.id.userID);
            final String k = key.get(index);

            Post post = arrlist.get(index);
            System.out.println("here");

            userPhoto.setImageResource(userHeadsId[post.getImageID()]);
            heading.setText(post.title);
            requester.setText(post.takerName);
            reward.setText("Reward: " + post.reward+"");

            Button button = (Button) row.findViewById(R.id.entry_detail);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(myPosts.this,TakeRequestActivity.class);
                    intent.putExtra("key",k);
                    startActivity(intent);
                }
            });

            Button confirm = (Button) row.findViewById(R.id.entry_confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference mBase = FirebaseDatabase.getInstance().getReference("posts");
                    curtPost = mDatabase.child(k);
                    curtPost.child("postState").setValue(2);

                    final Dialog rankDialog = new Dialog(myPosts.this);
                    rankDialog.setContentView(R.layout.rank_dialog);
                    rankDialog.setCancelable(true);

                    RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                    double rate = ratingBar.getRating();



                    Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rankDialog.dismiss();
                            finish();
                        }
                    });
                    //now that the dialog is set up, it's time to show it
                    rankDialog.show();


                }
            });

            return (row);
        }
    }
}