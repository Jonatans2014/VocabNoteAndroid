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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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
        yvalues.add(new PieEntry(34f,"US"));
        yvalues.add(new PieEntry(23f,"UK"));
        yvalues.add(new PieEntry(35f,"BR"));
        yvalues.add(new PieEntry(34f,"iNDIA"));
        yvalues.add(new PieEntry(40f,"cHINA"));


        PieDataSet DataSet = new PieDataSet(yvalues,"Countries");
        DataSet.setSliceSpace(3f);
        DataSet.setSelectionShift(5f);
        DataSet.setColors(ColorTemplate.MATERIAL_COLORS);


        PieData data = new PieData(DataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        piechart.setData(data);



        return view;



    }


}
