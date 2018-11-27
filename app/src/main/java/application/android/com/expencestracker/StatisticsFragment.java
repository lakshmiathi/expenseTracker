package application.android.com.expencestracker;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import application.android.com.expencestracker.DBImp.ExpenseDaoImpl;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    PieChart pieChart;
    


    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = (PieChart) view.findViewById(R.id.piechart) ;
        chart();
        return  view;
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

    public void chart(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < queryYData().size(); i++)
            yVals.add(new Entry((int) Math.round(queryYData().get(i)), i));
        List<String> xVals = new ArrayList<String>();
        for (int i = 0; i < queryXData().size(); i++)
            xVals.add(queryXData().get(i).toString());

        PieDataSet dataSet = new PieDataSet(yVals, "Total");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(3000, Easing.EasingOption.EaseInOutSine);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }



}
