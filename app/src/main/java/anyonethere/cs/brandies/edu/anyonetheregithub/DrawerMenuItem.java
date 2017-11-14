package anyonethere.cs.brandies.edu.anyonetheregithub;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by chensi on 11/7/17.
 */
@Layout(R.layout.drawer_item)
public class DrawerMenuItem {

    public static final int DRAWER_MENU_ITEM_PROFILE = 1;
    public static final int DRAWER_MENU_ITEM_NEWREQUEST = 2;
    public static final int DRAWER_MENU_ITEM_MANAGEMENT = 3;
    public static final int DRAWER_MENU_ITEM_NOTIFICATIONS = 4;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 5;

    private int mMenuPosition;
    private Context mContext;
    private DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    public DrawerMenuItem(Context context, int menuPosition) {
        mContext = context;
        mMenuPosition = menuPosition;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                itemNameTxt.setText("Profile");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_face));
                break;
            case DRAWER_MENU_ITEM_NEWREQUEST:
                itemNameTxt.setText("Create Request");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_upload));
                break;
            case DRAWER_MENU_ITEM_MANAGEMENT:
                itemNameTxt.setText("Management");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_management));
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                itemNameTxt.setText("Notifications");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications));
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_logout));
                itemNameTxt.setText("Logout");
                break;
        }
    }

    @Click(R.id.mainView)
    private void onMenuItemClick(){
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                Toast.makeText(mContext, "Profile", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NEWREQUEST:
                Toast.makeText(mContext, "Create New Requests", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onNewRequestMenuSelected();
                break;
            case DRAWER_MENU_ITEM_MANAGEMENT:
                Toast.makeText(mContext, "Manage Requests", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onManagementMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                Toast.makeText(mContext, "Check Notifications", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onNotificationsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onLogoutMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack) {
        mCallBack = callBack;
    }

    public interface DrawerCallBack{
        void onProfileMenuSelected();
        void onNotificationsMenuSelected();
        void onNewRequestMenuSelected();
        void onManagementMenuSelected();
        void onLogoutMenuSelected();
    }
}
