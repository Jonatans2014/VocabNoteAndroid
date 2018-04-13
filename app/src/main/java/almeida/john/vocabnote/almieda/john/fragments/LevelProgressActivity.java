package almeida.john.vocabnote.almieda.john.fragments;

import android.content.SharedPreferences;
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

import static almeida.john.vocabnote.almieda.john.fragments.GuessWordGameFragment.MY_PREFS_NAME;

public class LevelProgressActivity extends AppCompatActivity {






    PieChart piechart;
    ProgressBar progressBar;


    TextView currentScore,overallhighestscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_progress);


        piechart =  findViewById(R.id.piechart);
        progressBar =  findViewById(R.id.determinateBar);
        currentScore = (TextView) findViewById(R.id.currentscore);
        overallhighestscore =  (TextView) findViewById(R.id.highestScore);


        piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);
        piechart.setExtraOffsets(5,10,5,5);

        piechart.setDragDecelerationFrictionCoef(0.95f);
        piechart.setDrawHoleEnabled(true);
        piechart.setHoleColor(Color.WHITE);
        piechart.setTransparentCircleRadius(61f);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE); // getting Integer
        int OverallScore =prefs.getInt("OverallScore", 0);
        int OverallIncorret =prefs.getInt("OverallIncorret", 0);
        int Overallcorret =prefs.getInt("Overallcorret", 0);
        int OverallhighesCORE= prefs.getInt("OverallHighestScore", 0);


        progressBar.setProgress(OverallScore);
        currentScore.setText(String.valueOf(OverallScore));
        overallhighestscore.setText(String.valueOf(OverallhighesCORE));


        ArrayList<PieEntry> yvalues = new ArrayList<>();
        yvalues.add(new PieEntry(Overallcorret,"Correct"));
        yvalues.add(new PieEntry(OverallIncorret,"Incorrect"));



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
