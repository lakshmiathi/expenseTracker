package application.android.com.expencestracker;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import application.android.com.expencestracker.DBImp.DBHelper;
import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarFragment extends Fragment {

    BarChart barChart ;
    BarData BARDATA ;
    BarDataSet dataSet;
    private DBHelper sqLiteUtil;
    private SQLiteDatabase db;
    public void ExpenseDaoImpl(Context context) {
        sqLiteUtil = new DBHelper(context, 3);
        db = sqLiteUtil.getWritableDatabase();
    }
    public BarFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar, container, false);
        barChart = (BarChart) view.findViewById(R.id.chart);
        createbarChart();
        return view;
    }
    public ArrayList<Double> queryYData(){
        ExpenseDaoImpl db = new ExpenseDaoImpl(getActivity());
        Bundle b = getArguments();
        String id = b.getString("ID");
        return db.Amounts(Integer.parseInt(id));
    }
    public  ArrayList<String> queryXData(){
        ExpenseDaoImpl db = new ExpenseDaoImpl(getActivity());
        Bundle b = getArguments();
        String id =b.getString("ID");
        return  db.Categories(Integer.parseInt(id));
    }
    public void createbarChart(){
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < queryYData().size(); i++)
            yVals.add(new BarEntry((int) Math.round(queryYData().get(i)), i));
        List<String> xVals = new ArrayList<String>();
        for (int i = 0; i < queryYData().size(); i++)
            xVals.add(queryXData().get(i).toString());
        BarDataSet dataSet = new BarDataSet(yVals, "Expenses");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        BARDATA = new BarData(xVals,dataSet);
        barChart.setEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDrawGridBackground(false);
        barChart.animateXY(2000, 2000);
        barChart.setData(BARDATA);
        barChart.animateY(3000);
    }



    

}
