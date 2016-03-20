package capstone.uoit.ca.lifenoteapp.functions.Graphs;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import capstone.uoit.ca.lifenoteapp.R;

public class GraphHome extends Fragment implements View.OnClickListener {
    View rootView;

    public GraphHome() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GraphHome newInstance() {
        GraphHome fragment = new GraphHome();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_graph_home, container, false);
        Button termGraphBtn = (Button) rootView.findViewById(R.id.btn_termGraph);
        termGraphBtn.setOnClickListener(this);
        Button illnessGraphBtn = (Button) rootView.findViewById(R.id.btn_illnessGraph);
        illnessGraphBtn.setOnClickListener(this);
        displayTermUseGraph(false);
        return rootView;
    }

    public void displayTermUseGraph(boolean sorted) {
        CodifiedHashMapManager dataManager = CodifiedHashMapManager.getInstance(getContext());
        TreeMap<String, Integer> treeMap = dataManager.getHashMap();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        if (!sorted) {
            NavigableMap<String, Integer> set = treeMap.descendingMap();
            int positionCount = 0;
            for (Map.Entry<String, Integer> entry : set.entrySet()) {
                entries.add(new BarEntry((int) entry.getValue(), positionCount));
                labels.add((String) entry.getKey());
                positionCount ++;
            }
        } else {
            TreeMap<Integer, String> sortedTreeMap = new TreeMap<>();
            Set set = treeMap.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)iterator.next();
                sortedTreeMap.put((Integer) mentry.getValue(), (String) mentry.getKey());
            }

            Set sortedSet = sortedTreeMap.entrySet();
            Iterator sortedIterator = sortedSet.iterator();
            int positionCount = 0;
            while(sortedIterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)sortedIterator.next();
                entries.add(new BarEntry((int) mentry.getKey(), positionCount));
                labels.add((String) mentry.getValue());
                positionCount ++;
            }
        }
        BarDataSet dataset = new BarDataSet(entries, "# of Users");
        HorizontalBarChart chart = new HorizontalBarChart(getContext());
        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("# of term mentions");
        chart.animateY(1000);
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        chart.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout graphHolder = (LinearLayout) rootView.findViewById(R.id.layout_graphHolder);
        graphHolder.removeAllViews();
        graphHolder.addView(chart);


/*        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Cancer");
        labels.add("Flu");
        labels.add("Cold");
        labels.add("Stomach");
        labels.add("Head");
        labels.add("cough");

//        HorizontalBarChart chart = (HorizontalBarChart) rootview.findViewById(R.id.chart);
        HorizontalBarChart chart = new HorizontalBarChart(getContext());

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("# of term mentions");
        chart.animateY(1000);
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        chart.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout graphHolder = (LinearLayout) rootView.findViewById(R.id.layout_graphHolder);
        graphHolder.removeAllViews();
        graphHolder.addView(chart);*/
    }


    public void displayIllnessGraph() {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(2f, 1));
        entries.add(new Entry(1f, 2));
        entries.add(new Entry(3f, 3));
        entries.add(new Entry(5f, 4));
        entries.add(new Entry(9f, 5));

        LineDataSet dataSet = new LineDataSet(entries, "# of calls");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        LineChart chart = new LineChart(getContext());
        chart.setData(new LineData(labels, dataSet));
        chart.setDescription("# of term mentions");
        chart.animateY(1000);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(getResources().getColor(R.color.colorAccent));
        dataSet.setFillAlpha(240);
        dataSet.setDrawCubic(true);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        chart.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout graphHolder = (LinearLayout) rootView.findViewById(R.id.layout_graphHolder);
        graphHolder.removeAllViews();
        graphHolder.addView(chart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_termGraph:
                displayTermUseGraph(false);
                break;
            case R.id.btn_illnessGraph:
//                displayIllnessGraph();
                displayTermUseGraph(true);
                break;

        }
    }
}
