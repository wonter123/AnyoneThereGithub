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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;


public class RequestListActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    final int[] userHeadsId = new int[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_elements);

        userHeadsId[0] = R.drawable.user_head_1;
        userHeadsId[1] = R.drawable.user_head_2;
        userHeadsId[2] = R.drawable.user_head_3;
        userHeadsId[3] = R.drawable.user_head_4;
        userHeadsId[4] = R.drawable.user_head_5;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppAdaptor adp = new AppAdaptor();
                ListView mainListView = (ListView) findViewById( R.id.listexpense);
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
            heading = (TextView) row.findViewById(R.id.entry_title);
            requester = (TextView) row.findViewById(R.id.entry_poster);
            reward = (TextView) row.findViewById(R.id.entry_reward);

            final String k = key.get(index);

            Post post = arrlist.get(index);
            System.out.println("here");

            heading.setText(post.title);
            requester.setText(post.posterId);
            reward.setText(post.reward+"");

            ImageView profileImg = (ImageView) row.findViewById(R.id.userID);
            int photoId = userHeadsId[(int) (Math.random()*5)];
            profileImg.setImageDrawable(getResources().getDrawable(photoId));
            Log.d("Warning: ", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Math.random()*5");

            Button button = (Button) row.findViewById(R.id.detail);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RequestListActivity.this,TakeRequestActivity.class);
                    intent.putExtra("key",k);
                    startActivity(intent);
                }
            });

            return (row);
        }
    }
}

