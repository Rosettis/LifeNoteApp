package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Peter on 12/02/16.
 */
public class UMLS_Api {
    private static UMLS_Api ourInstance;
    private String tgt;
    private String ticket;
    private OnTermListener termListener;
    private OnDefinitionListener defListener;

    public interface OnTermListener {
        void onTermResonse (String term, String name, String cui);
    }

    public interface  OnDefinitionListener {
        void onDefinitionResponse (String definition);
    }

    public static UMLS_Api getInstance() {
        if (ourInstance == null) {
            ourInstance = new UMLS_Api();
            return ourInstance;
        }
        else return ourInstance;
    }


    public void getDefinition(Context context, String cui, OnDefinitionListener listener) {
        this.defListener = listener;
        if (tgt == null) {
            retrieveTGT(context, cui, "def");
        } else {
            ticket = null;
            retriveST(context, cui, "def");
        }

    }

    private UMLS_Api() {}

    //gets api ST ticket
    public void getTerm(Context context, String term, OnTermListener listener) {
        this.termListener = listener;
        if (tgt == null) {
            retrieveTGT(context, term, "term");
        } else {
            ticket = null;
            retriveST(context, term, "term");
        }
    }

    public void retriveDef(Context context, final String ticket, final String string) {
        System.out.println("RETRIEVING DEFINITION xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        String url = "https://uts-ws.nlm.nih.gov/rest/content/current/CUI/" + string + "/definitions?ticket=" + ticket; // + "&sabs=CCPSS,CHV";
        System.out.println(url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                        System.out.println("Definition: " + response.toString());
//                        try {
//                            JSONObject resultsHolder = response.getJSONObject("result");
//                            JSONArray results = resultsHolder.getJSONArray("results");
//                            JSONObject result1 = (JSONObject) results.get(0);
//                            termListener.onTermResonse(string, result1.getString("name"), result1.getString("ui"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        UMLSConnection.getInstance(context).addToRequestQueue(jsObjRequest);

    }

    public void retriveTerm(Context context, final String ticket, final String string) {
        String url = "https://uts-ws.nlm.nih.gov/rest/search/current?ticket=" + ticket + "&string=" + string + "&searchType=exact" + "&sabs=CCPSS,CHV";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                        System.out.println("Response: " + response.toString());
                        try {
                            JSONObject resultsHolder = response.getJSONObject("result");
                            JSONArray results = resultsHolder.getJSONArray("results");
                            JSONObject result1 = (JSONObject) results.get(0);
                            termListener.onTermResonse(string, result1.getString("name"), result1.getString("ui"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ticket", ticket);
                return params;
            }
        };

        UMLSConnection.getInstance(context).addToRequestQueue(jsObjRequest);

    }

    public void retriveST(final Context context, final String param, final String intent) {
        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();
        queue.start();

        System.out.println("TGT:" + tgt);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tgt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response222222:" + response);
                if (intent.equals("term")) retriveTerm(context, response, param);
                else if (intent.equals("def")) retriveDef(context, response, param);
                else Log.e("Invalid Intent", "Error: Invalid api intnent of: " + intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volly Error2222222");
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("service", "http://umlsks.nlm.nih.gov");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        UMLSConnection.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void retrieveTGT(final Context context, final String param, final String intent){
        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();
        // Start the queue
        queue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://utslogin.nlm.nih.gov/cas/v1/tickets", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response:" + response);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                Document doc = Jsoup.parse(response);
                Elements elements = doc.select("form");
                tgt = elements.attr("action");
                retriveST(context, param, intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volly Error");
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", "lifenote");
                params.put("password", "Lifenote!pmms");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        UMLSConnection.getInstance(context).addToRequestQueue(stringRequest);
    }
}
