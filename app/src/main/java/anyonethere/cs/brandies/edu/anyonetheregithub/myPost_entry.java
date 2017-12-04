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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myPost_entry extends AppCompatActivity {
    DatabaseReference mDatabase;
    Button confirm_btn;
    Button detail_btn;
    String userId;

    void init() {
        confirm_btn = (Button)findViewById(R.id.myPost_entry_confirm);
        detail_btn = (Button)findViewById(R.id.myPost_entry_detail);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        setContentView(R.layout.activity_my_post_entry);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppAdaptor adp = new AppAdaptor();
                ListView mainListView = (ListView) findViewById( R.id.listexpense);
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    String key = uniqueUserSnapshot.getKey();
                    Post post = uniqueUserSnapshot.getValue(Post.class);
                    if (post.posterId != userId) {
                        continue;
                    }
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
            heading = (TextView) row.findViewById(R.id.myTake_entry_title);
            requester = (TextView) row.findViewById(R.id.myTake_entry_poster);
            reward = (TextView) row.findViewById(R.id.myTake_entry_reward);

            final String k = key.get(index);

            Post post = arrlist.get(index);
            System.out.println("here");

            heading.setText(post.title);
            requester.setText(post.takerId);
            reward.setText(post.reward + "");

            detail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(myPost_entry.this,TakeRequestActivity.class);
                    intent.putExtra("key",k);
                    startActivity(intent);
                }
            });

            return (row);
        }
    }
}