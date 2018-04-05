package almeida.john.vocabnote.almieda.john.fragments;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import almeida.john.vocabnote.R;

public class LevelProgressActivity extends AppCompatActivity {






    PieChart piechart;
    ProgressBar progressBar;
    GamesAddon gamesAddon;
    TextView currentScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_progress);





        piechart =  findViewById(R.id.piechart);
        progressBar =  findViewById(R.id.determinateBar);
        currentScore = (TextView) findViewById(R.id.currentscore);


        progressBar.setProgress(gamesAddon.getOverAllScore());

        currentScore.setText(String.valueOf(gamesAddon.getOverAllScore()));

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



        PieDataSet DataSet = new PieDataSet(yvalues,"");
        DataSet.setSliceSpace(3f);
        DataSet.setSelectionShift(5f);
        DataSet.setColors(colors);
        PieData data = new PieData(DataSet);
        data.setValueTextSize(20F);
        data.setValueTextColor(Color.BLACK);

        piechart.setData(data);



    }
}
