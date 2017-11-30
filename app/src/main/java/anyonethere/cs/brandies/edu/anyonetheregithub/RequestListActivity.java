package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class RequestListActivity extends AppCompatActivity {
    private ListView mainListView ;
    private DatabaseReference mDatabase;
    private AppAdaptor adp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_elements);

        adp = new AppAdaptor();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.getRoot();
        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueUserSnapshot : dataSnapshot.getChildren()) {
                    String key = uniqueUserSnapshot.getKey();
                    Post post = uniqueUserSnapshot.getValue(Post.class);
                    adp.addIn(post,key);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

        mainListView = (ListView) findViewById( R.id.listexpense);
        mainListView.setAdapter(adp);
    }

    class AppAdaptor extends BaseAdapter {
        ArrayList<Post> arrlist;
        ArrayList<String> key;
//        Context content;
//        int layoutId;

        public AppAdaptor() {
            super();
            arrlist = new ArrayList<>();
            key = new ArrayList<>();
//            ExpenseLogEntryData e1 = new ExpenseLogEntryData("test","sometest");
//            ExpenseLogEntryData e2 = new ExpenseLogEntryData("another test","someday some night");
//            arrlist.add(e1);
//            arrlist.add(e2);
        }

        public void addIn(Post post,String keys) {
//            User e3 = new ExpenseLogEntryData(expense,note);
            arrlist.add(post);
            key.add(keys);
        }

        public int getCount() {
            return arrlist.size();
        }

        public Object getItem(int index) {
            return arrlist.get(index);
        }

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

            Post post = arrlist.get(index);
            heading.setText((String)post.result.get("title"));
            requester.setText((String)post.result.get("poster"));
            reward.setText((String)post.result.get("reward"));

            final String k = key.get(index);

            Button button = (Button) row.findViewById(R.id.detail);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RequestListActivity.this,TakeRequestActivity.class);
                    intent.putExtra("key",k);
                    startActivity(intent);
                }
            });


            //System.out.println("here");

            return (row);
        }
    }
}

