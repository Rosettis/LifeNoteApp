package capstone.uoit.ca.lifenoteapp.functions.Graphs;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

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
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CreateNoteHome;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayoutDBHelper;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.NoteDBHelper;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.ViewNote;

public class GraphHome extends Fragment implements View.OnClickListener {
    View rootView;
    private boolean illnessMode = false;
    private String term;
    TextView yLabel;

    public GraphHome() {
        // Required empty public constructor
    }

    public static GraphHome newInstance() {
        GraphHome fragment = new GraphHome();
        return fragment;
    }

    public static GraphHome newInstance(String term) {
        GraphHome fragment = new GraphHome();
        fragment.illnessMode = true;
        fragment.term = term;
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        NoteDBHelper dbHelper = NoteDBHelper.getInstance(getContext());
        rootView = inflater.inflate(R.layout.fragment_graph_home, container, false);

        if (dbHelper.getAllNotes().size() > 0) {
            // Inflate the layout for this fragment
            Button termGraphBtn = (Button) rootView.findViewById(R.id.btn_termGraph);
            termGraphBtn.setOnClickListener(this);
            Button sortedTermGraphBtn = (Button) rootView.findViewById(R.id.btn_termGraphSorted);
            sortedTermGraphBtn.setOnClickListener(this);
            yLabel = (TextView) rootView.findViewById(R.id.graph_top_label);
            if (!illnessMode) {
                displayTermUseGraph(false);
                yLabel.setText("Uses");
            }
            else {
                displayIllnessGraph(term);
                yLabel.setText("Date");

            }
        } else {
            ((LinearLayout) rootView.findViewById(R.id.linearLayout_btn_container)).setVisibility(View.GONE);
            ((TextView) rootView.findViewById(R.id.empty_view)).setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void displayTermUseGraph(boolean sorted) {
        CodifiedHashMapManager dataManager = CodifiedHashMapManager.getInstance(getContext());
//        Log.i("Test injection", "displayTermUseGraph: ");
        TreeMap<String, Integer> treeMap = dataManager.getHashMap();
        final ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();
        int maxValue = 0;

        yLabel.setText("Uses");



        if (!sorted) {
            NavigableMap<String, Integer> set = treeMap.descendingMap();
            int positionCount = 0;
            for (Map.Entry<String, Integer> entry : set.entrySet()) {
                entries.add(new BarEntry(entry.getValue(), positionCount));
                if (entry.getValue() > maxValue) maxValue = entry.getValue();
                labels.add(entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1));
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
                if (currEntry.getValue() > maxValue) maxValue = currEntry.getValue();
                entries.add(new BarEntry((int) currEntry.getValue(), positionCount));
//                Log.i("Graph home", "displayTermUseGraph: " + currEntry.getValue() + " key:" + currEntry.getKey() + "pos:" + positionCount);
                labels.add((String) currEntry.getKey());
                positionCount ++;
            }
        }
        if (entries.size() > 0) {
            BarDataSet dataset = new BarDataSet(entries, "# of Users");
            HorizontalBarChart chart = new HorizontalBarChart(getContext());
            BarData data = new BarData(labels, dataset);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextSize(14f);
            chart.setData(data);
            chart.setDescription("# of term mentions");
            chart.animateY(1000);
            chart.setVisibleYRangeMaximum(7, YAxis.AxisDependency.LEFT);
            chart.setVisibleYRangeMaximum(7, YAxis.AxisDependency.RIGHT);
            chart.setDragEnabled(false);


            Legend legend = chart.getLegend();
            legend.setEnabled(false);

            YAxis topAxis = chart.getAxisLeft();
            topAxis.setAxisMinValue(0);
            topAxis.setAxisMaxValue(maxValue);
            topAxis.setLabelCount(maxValue + 1, true);
            topAxis.setValueFormatter(new IntegerYAxisFOrmatter());
            topAxis.setTextSize(14);

            YAxis botAxis = chart.getAxisRight();
            botAxis.setAxisMinValue(0);
            botAxis.setAxisMaxValue(maxValue);
            botAxis.setLabelCount(maxValue + 1, true);
            botAxis.setValueFormatter(new IntegerYAxisFOrmatter());
            topAxis.setTextSize(14);

            XAxis rightAxis = chart.getXAxis();
            rightAxis.setLabelsToSkip(0);
            rightAxis.setAvoidFirstLastClipping(true);
            rightAxis.setTextSize(14f);
            rightAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);


//
//            YAxis botAxis = chart.getAxisRight();
//            botAxis.setEnabled(false);


//        rightAxis.setAxisMaxValue(0);
//        rightAxis.setAxisMaxValue(10);
//        rightAxis.setLabelCount(10, true);

            chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                    if (entries.get(e.getXIndex()).getVal() < 2) {
                        Toast.makeText(getContext(), "Not enough mentions: Make more notes containing this term to see statistics", Toast.LENGTH_LONG).show();
                    } else {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction
                                .replace(R.id.content, GraphHome.newInstance(labels.get(e.getXIndex()).toLowerCase()))
                                .addToBackStack(null)
                                .commit();
                    }
                }

                @Override
                public void onNothingSelected() {

                }
            });

            dataset.setColors(ColorTemplate.PASTEL_COLORS);
            chart.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            LinearLayout graphHolder = (LinearLayout) rootView.findViewById(R.id.layout_graphHolder);
            graphHolder.removeAllViews();
            graphHolder.addView(chart);
        }
    }

    public class MyValueFormatter implements ValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return Math.round(value)+"";
        }
    }

    public class IntegerYAxisFOrmatter implements  YAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return Math.round(value)+"";
        }
    }


    public void displayIllnessGraph(String term) {
        NoteDBHelper notesDatabase = NoteDBHelper.getInstance(getContext());
        ArrayList<Note> allNotes = notesDatabase.getAllNotes();
        final ArrayList<Entry> entries = new ArrayList<>();
        final ArrayList<Long> noteIds = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        yLabel.setText("Date");


        int positionCount = 0;
        for (Note currNote : allNotes) {
            if (currNote.getLayout().containsIllnessModule()) {
                for (String currTerm : currNote.getCodifiedWords()) {
                    if (currTerm.equals(term)) {
                        currNote.printNote();
                        System.out.println("Adding Severity:" + currNote.getIllSeverity() + " at Position: " + positionCount);
                        entries.add(new Entry(currNote.getIllSeverity(), positionCount));
                        noteIds.add(currNote.getId());
                        System.out.println("Adding Label: " + currNote.getDate());
                        String[] dateParts = currNote.getDate().split(" ");
                        if (labels.size() == 0) labels.add(dateParts[1] + " " + dateParts[2]);
                        else labels.add(dateParts[2]);
                    positionCount ++;
                    }
                }
            } else {
                System.out.println(positionCount + ":Fail");
            }
        }
        LineDataSet dataSet = new LineDataSet(entries, "# of calls");
            LineChart chart = new LineChart(getContext());
        chart.setData(new LineData(labels, dataSet));
        chart.setDescription("Term Severity");
        chart.animateY(1000);
        if (entries.size() > 2) {
            chart.setVisibleXRangeMinimum(2);
        } else {
            chart.setVisibleXRangeMinimum(1);
        }


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction
                            .replace(R.id.content, CreateNoteHome.newInstance(noteIds.get(e.getXIndex())))
                            .addToBackStack(null)
                            .commit();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.setScaleEnabled(false);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinValue(0);
        leftAxis.setAxisMaxValue(10);
        leftAxis.setLabelCount(11, true);

        XAxis topAxis = chart.getXAxis();
        topAxis.setLabelsToSkip(0);
        topAxis.setTextSize(10f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(getResources().getColor(R.color.colorAccent));
        dataSet.setFillAlpha(240);
        dataSet.setDrawCubic(true);
        dataSet.setValueFormatter(new MyValueFormatter());
        dataSet.setValueTextSize(14f);
            dataSet.setDrawHorizontalHighlightIndicator(false);
        chart.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
            LinearLayout graphHolder = (LinearLayout) rootView.findViewById(R.id.layout_graphHolder);
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
        }
    }
}
