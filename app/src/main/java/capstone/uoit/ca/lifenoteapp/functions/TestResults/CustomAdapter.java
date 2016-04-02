package capstone.uoit.ca.lifenoteapp.functions.TestResults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Matthew on 4/2/2016.
 */

public class CustomAdapter extends BaseAdapter{

    List<String> result;
    Context context;
    List<Integer> imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(TestResultsFragment mainActivity, List<String> resultList, List<Integer> resultImages) {
        // TODO Auto-generated constructor stub
        result = resultList;
        context = mainActivity.getContext();
        imageId = resultImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.test_result_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.resultDescription);
        holder.img=(ImageView) rowView.findViewById(R.id.resultImage);

        holder.tv.setText(result.get(position));
        holder.img.setImageResource(imageId.get(position));

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: Change the onclick of the pictures
                Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }

}