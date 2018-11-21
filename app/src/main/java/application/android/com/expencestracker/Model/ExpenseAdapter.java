package application.android.com.expencestracker.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import application.android.com.expencestracker.R;

public class ExpenseAdapter extends ArrayAdapter {
    private final Activity context;
    private final List<Expense> exp_list;


    public ExpenseAdapter(Activity context, int resource, List<Expense> exp_list) {
        super(context, R.layout.listview_entry, exp_list);
        this.context = context;
        this.exp_list = exp_list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_entry, null, true);

        ImageView item_image = (ImageView) rowView.findViewById(R.id.itemIcon);
        TextView item_id = (TextView) rowView.findViewById(R.id.item_Id);
        TextView item_label = (TextView) rowView.findViewById(R.id.itemLabel);
        TextView item_date = (TextView) rowView.findViewById(R.id.itemDate);
        TextView item_money = (TextView) rowView.findViewById(R.id.itemCount);

        // Do the assignment.
        try {
            item_id.setText(Integer.toString(this.exp_list.get(position).getId()));
            item_label.setText(this.exp_list.get(position).getCategory());
            item_date.setText(DateUtil.dateToString(this.exp_list.get(position).getDate()));
            item_money.setText(String.format("%.2f", this.exp_list.get(position).getAmount()));
            item_image.setImageResource(R.drawable.splash);
            //item_image.setImageResource(R.drawable.class.getDeclaredField(this.exp_list.get(position).getCategory()).getInt(R.drawable.class));
        } catch (Exception e) {
            //item_image.setImageResource(R.drawable.entertainment);
        }
        return rowView;
    }
}
