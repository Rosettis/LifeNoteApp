package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.UMLS;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
    private OnDefinitionListener defListener;
    private final int MY_SOCKET_TIMEOUT_MS = 20000;

    public interface OnTermListener {
        void onTermResonse (String term, String name, String cui, int start, int end);
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
            retrieveDefTGT(context, cui);
        } else {
            retriveDefST(context, cui);
        }

    }

    private UMLS_Api() {}

    //gets api ST ticket
    public void getTerm(Context context, String term, OnTermListener callback, int start, int end) {
        if (tgt == null) {
            retrieveTermTGT(context, term, start, end, callback);
        } else {
            retriveTermST(context, term, start, end, callback);
        }
    }

    public void getTGT(final Context context){
        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();
        // Start the queue
        queue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://utslogin.nlm.nih.gov/cas/v1/tickets", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                Document doc = Jsoup.parse(response);
                Elements elements = doc.select("form");
                tgt = elements.attr("action");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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


    public void retriveDef(Context context, final String ticket, final String string) {
        String url = "https://uts-ws.nlm.nih.gov/rest/content/current/CUI/" + string + "/definitions?ticket=" + ticket + "&pageSize=10"; // + "&sabs=CCPSS,CHV";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsHolder = response.getJSONArray("result");
//                            JSONArray results = resultsHolder.getJSONArray("results");
                            JSONObject result1 = (JSONObject) resultsHolder.get(0);
                            String rootSource = "";
                            int resultIndex = -1;
                            while (!rootSource.equals("MEDLINEPLUS")) {
                                resultIndex ++;
                                result1 = (JSONObject) resultsHolder.get(resultIndex);
                                rootSource = result1.getString("rootSource");
                            }
                            defListener.onDefinitionResponse(result1.getString("value"));
                        } catch (JSONException e) {
                            try {
                                JSONArray resultsHolder = response.getJSONArray("result");
                                JSONObject result1 = (JSONObject) resultsHolder.get(0);
                                defListener.onDefinitionResponse(result1.getString("value"));
                            } catch (JSONException noResponses) {
                                noResponses.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        UMLSConnection.getInstance(context).addToRequestQueue(jsObjRequest);

    }

    public void retriveTerm(Context context, final String ticket, final String string, final int start, final int end, final OnTermListener callback) {
        String url = "https://uts-ws.nlm.nih.gov/rest/search/current?ticket=" + ticket + "&string=" + string + "&searchType=exact" + "&sabs=CCPSS,CHV" + "&pageSize=3";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                        try {
                            JSONObject resultsHolder = response.getJSONObject("result");
                            JSONArray results = resultsHolder.getJSONArray("results");
                            JSONObject result1 = (JSONObject) results.get(0);
                            callback.onTermResonse(string, result1.getString("name"), result1.getString("ui"), start, end);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
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


    private void retriveTermST(final Context context, final String param, final int start, final int end, final OnTermListener callback) {
        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();
        queue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tgt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                retriveTerm(context, response, param, start, end, callback);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void retriveDefST(final Context context, final String param) {
        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();
        queue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tgt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                retriveDef(context, response, param);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void retrieveDefTGT(final Context context, final String param){
        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();
        // Start the queue
        queue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://utslogin.nlm.nih.gov/cas/v1/tickets", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                Document doc = Jsoup.parse(response);
                Elements elements = doc.select("form");
                tgt = elements.attr("action");
                retriveDefST(context, param);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void retrieveTermTGT(final Context context, final String param, final int start, final int end, final OnTermListener callback){
        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();
        // Start the queue
        queue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://utslogin.nlm.nih.gov/cas/v1/tickets", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                Document doc = Jsoup.parse(response);
                Elements elements = doc.select("form");
                tgt = elements.attr("action");
                retriveTermST(context, param, start, end, callback);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
