package capstone.uoit.ca.lifenoteapp.functions.Graphs;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;

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
        Button sortedTermGraphBtn = (Button) rootView.findViewById(R.id.btn_termGraphSorted);
        sortedTermGraphBtn.setOnClickListener(this);
        Button illnessGraphBtn = (Button) rootView.findViewById(R.id.btn_illnessGraph);
        illnessGraphBtn.setOnClickListener(this);
        displayTermUseGraph(false);
        return rootView;
    }

    public void displayTermUseGraph(boolean sorted) {
        CodifiedHashMapManager dataManager = CodifiedHashMapManager.getInstance(getContext());
        Log.i("Test injection", "displayTermUseGraph: ");
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
            ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>();
            list.addAll(set);
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> lhs, Map.Entry<String, Integer> rhs) {
                    if (lhs.getValue() < rhs.getValue()) return -1;
                    else if (lhs.getValue() == rhs.getValue()) return rhs.getKey().compareTo(lhs.getKey());
                    else return 1;
                }
            });

            int positionCount = 0;
            for (Map.Entry<String, Integer> currEntry : list) {
                entries.add(new BarEntry((int) currEntry.getValue(), positionCount));
                Log.i("Graph home", "displayTermUseGraph: " + currEntry.getValue() + " key:" + currEntry.getKey() + "pos:" + positionCount);
                labels.add((String) currEntry.getKey());
                positionCount ++;
            }


//            Iterator iterator = set.iterator();
//            while(iterator.hasNext()) {
//                Map.Entry mentry = (Map.Entry)iterator.next();
//                sortedTreeMap.put((Integer) mentry.getValue(), (String) mentry.getKey());
//            }
//
//            Set sortedSet = sortedTreeMap.entrySet();
//            Iterator sortedIterator = sortedSet.iterator();
//            int positionCount = 0;
//            while(sortedIterator.hasNext()) {
//                Map.Entry mentry = (Map.Entry)sortedIterator.next();
//                entries.add(new BarEntry((int) mentry.getKey(), positionCount));
//                Log.i("Graph home", "displayTermUseGraph: " + mentry.getValue() + " key:" + mentry.getKey() + "pos:" + positionCount);
//                labels.add((String) mentry.getValue());
//                positionCount ++;
//            }
        }
        if (entries.size() > 0) {
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
        } else {
            Log.i("NOPE", "displayTermUseGraph: NOPE NOPE NOPE");
        }
    }


    public void displayIllnessGraph(String term) {

        NoteDBHelper notesDatabase = NoteDBHelper.getInstance(getContext());
        ArrayList<Note> allNotes = notesDatabase.getAllNotes();
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();


        int positionCount = 0;
        for (Note currNote : allNotes) {
            if (currNote.getCodifiedWords().contains(term) && currNote.getLayout().containsIllnessModule()) {
                entries.add(new Entry(currNote.getIllSeverity(), positionCount));
                labels.add(currNote.getDate());
                positionCount ++;
                currNote.printNote();
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, "# of calls");
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
            case R.id.btn_termGraphSorted:
                displayTermUseGraph(true);
                break;
            case R.id.btn_illnessGraph:
                displayIllnessGraph("flu");
                break;

        }
    }
}
