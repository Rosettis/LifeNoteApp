package capstone.uoit.ca.lifenoteapp.navbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by nicholas on 31/10/15.
 */
public class NavMenuAdapter extends BaseAdapter {
    private Context context;
    private int viewID;
    private ArrayList<NavMenuItem> items;

    public NavMenuAdapter(Context context, int viewID,  ArrayList<NavMenuItem> items) {
        this.context = context;
        this.viewID = viewID;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(viewID, null);
        }

        ImageView menuIcon = (ImageView) convertView.findViewById(R.id.nav_item_icon);
        menuIcon.setImageResource(items.get(position).getItemIcon());

        TextView menuName = (TextView) convertView.findViewById(R.id.nav_item);
        menuName.setText(items.get(position).getItemName());

        return convertView;
    }
}
