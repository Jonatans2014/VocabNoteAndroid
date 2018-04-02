package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;


import java.util.ArrayList;

import almeida.john.vocabnote.R;

public class PeformanceAnalysisFragment extends Fragment {


    PieChart piechart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_peformance_analysis, container, false);


        piechart =  view.findViewById(R.id.piechart);

        piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);
        piechart.setExtraOffsets(5,10,5,5);

        piechart.setDragDecelerationFrictionCoef(0.95f);
        piechart.setDrawHoleEnabled(true);
        piechart.setHoleColor(Color.WHITE);
        piechart.setTransparentCircleRadius(61f);


        ArrayList<PieEntry> yvalues = new ArrayList<>();
        yvalues.add(new PieEntry(40f,"Correct"));
        yvalues.add(new PieEntry(100f,"Incorrect"));



        final int[] MY_COLORS = {Color.rgb(65, 244, 199), Color.rgb(255,0,0)};
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: MY_COLORS) colors.add(c);



        PieDataSet DataSet = new PieDataSet(yvalues,"Countries");
        DataSet.setSliceSpace(3f);
        DataSet.setSelectionShift(5f);
        DataSet.setColors(colors);
        PieData data = new PieData(DataSet);
        data.setValueTextSize(20F);
        data.setValueTextColor(Color.WHITE);

        piechart.setData(data);



        return view;



    }


}
