package application.android.com.expencestracker;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import application.android.com.expencestracker.DBImp.DBdesign;

public class CustomAdapterList extends CursorAdapter {

    private  Context context;
    private  Cursor cursor;

    public CustomAdapterList(Context context ,Cursor cursor)
    {
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.expense_list,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView_expense = (TextView)view.findViewById(R.id.textview_expense_category);
        TextView textView_amount = (TextView)view.findViewById(R.id.textview_amount );
        TextView textView_date = (TextView)view.findViewById(R.id.textview_date);

        String expense_category = cursor.getString(cursor.getColumnIndexOrThrow(DBdesign.EXPENSE_TABLE_INFO_COLUM_CATEGORY));
        String expense_date = cursor.getString(cursor.getColumnIndexOrThrow(DBdesign.EXPENSE_TABLE_INFO_COLUM_DATE));
        String expense_amount = cursor.getString(cursor.getColumnIndexOrThrow(DBdesign.EXPENSE_TABLE_INFO_COLUM_AMOUNT));
        String expense_id = cursor.getString(cursor.getColumnIndexOrThrow(DBdesign.EXPENSE_TABLE_INFO_COLUM_ID));

        ImageView img = (ImageView) view.findViewById(R.id.imview_category);

        switch (expense_category) {
            case "Grocery":
                img.setImageResource(R.drawable.grocery);
                break;
            case "Rent":
                img.setImageResource(R.drawable.rent);
                break;
            case "Shopping":
                img.setImageResource(R.drawable.shopping);
                break;
            case "Travel":
                img.setImageResource(R.drawable.travel);
                break;
            case "Others":
                img.setImageResource(R.drawable.others);
                break;
        }

        textView_expense.setText(expense_category);
        textView_amount.setText(expense_amount);
        textView_date.setText(expense_date);

    }


}

