package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;


public class myPosts extends AppCompatActivity {
    private DatabaseReference mDatabase;

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
                    } else {
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

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.activity_my_post_entry, parent, false);

            TextView heading,requester,reward;
            heading = (TextView) row.findViewById(R.id.entry_title);
            requester = (TextView) row.findViewById(R.id.entry_poster);
            reward = (TextView) row.findViewById(R.id.entry_reward);

            final String k = key.get(index);

            Post post = arrlist.get(index);
            System.out.println("here");

            heading.setText(post.title);
            requester.setText(post.posterId);
            reward.setText(post.reward+"");

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
                    mDatabase.child("posts").child(k).setValue(3);
                    finish();
                }
            });

            return (row);
        }
    }
}