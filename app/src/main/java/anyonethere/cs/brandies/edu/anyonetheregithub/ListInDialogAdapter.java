package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhanglingjun on 11/30/17.
 */

public class ListInDialogAdapter extends ArrayAdapter<Post> {


    public ListInDialogAdapter(Context context, int textViewResourceId, List<Post> objects)
    {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View curView = convertView;
        if (curView == null)
        {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            curView = vi.inflate(R.layout.list_in_dialog_element, null);
        }
        Post p = getItem(position);
        TextView Heading = (TextView) curView.findViewById (R.id.list_in_dialog_heading);
        TextView from = (TextView) curView.findViewById (R.id.list_in_dialog_from);
        TextView Reward = (TextView) curView.findViewById(R.id.list_in_dialog_reward);
        ImageView profileImg = (ImageView) curView.findViewById(R.id.list_in_dialog_userID);
//        Button detailButton = (Button) curView.findViewById(R.id.list_in_dialog_detail);
        RatingBar rb = (RatingBar) curView.findViewById(R.id.list_in_dialog_ratingbar);
        ImageView iv = (ImageView) curView.findViewById(R.id.list_in_dialog_takenimg);

        if(p.postState == 1) iv.setImageResource(R.drawable.stamp_taken);
//        if(p.postState == 2) return new Space(curView.getContext());

        Heading.setText(p.getTitle());
        from.setText("From: "+p.getFrom());
        Reward.setText("Reward: "+p.getReward()+"");
//        profileImg.setImageResource(p);
        rb.setRating(p.getRating());

//        final String key = p.getPosterId();

//        detailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i  = new Intent(MapsActivity.this, TakeRequestActivity.class);
//                i.putExtra("key", key);
//                curView.startActivity(i);
//            }
//        });


        return curView;

    }

}