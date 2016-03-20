package capstone.uoit.ca.lifenoteapp.functions.Graphs;

import android.content.Context;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Peter on 18/03/16.
 */
public class CodifiedHashMapManager {
    private static CodifiedHashMapManager instance;
    private static TreeMap<String, Integer> codifiedWordsHashMap;
    private static Context context;

    public static CodifiedHashMapManager getInstance(Context c) {
        context = c;
        if (instance == null) {instance = new CodifiedHashMapManager();}
        return instance;
    }

    private CodifiedHashMapManager(){
        if (codifiedWordsHashMap == null) {
            CodifiedWordsDBHelper dbHelper = CodifiedWordsDBHelper.getInstance(context);
            codifiedWordsHashMap = dbHelper.generateHashMap();
        }
    }

    public TreeMap<String, Integer> getHashMap() {
        return codifiedWordsHashMap;
    }

    public void addEntries(ArrayList<String> listOfWords) {
        //add words to the
        for (String word : listOfWords) {
            Integer currValue;
            if ((currValue = codifiedWordsHashMap.get(word)) == null) codifiedWordsHashMap.put(word, 1);
            else codifiedWordsHashMap.put(word, currValue + 1);
        }

        //save hashmap to database
        CodifiedWordsDBHelper dbHelper = CodifiedWordsDBHelper.getInstance(context);
        dbHelper.deleteAllEntrys();
        dbHelper.createAllEntries(codifiedWordsHashMap);
    }

    public void clearHashTable(){
        codifiedWordsHashMap.clear();
        CodifiedWordsDBHelper dbHelper = CodifiedWordsDBHelper.getInstance(context);
        dbHelper.deleteAllEntrys();
    }
}
