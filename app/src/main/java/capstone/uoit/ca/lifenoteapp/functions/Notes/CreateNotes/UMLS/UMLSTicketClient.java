package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.UMLS;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Peter on 09/02/16.
 */
public class UMLSTicketClient {
    private static UMLSTicketClient ourInstance;
    String tgt;
    String ticket;

    public static UMLSTicketClient getInstance() {
        if (ourInstance == null) {
            ourInstance = new UMLSTicketClient();
            return ourInstance;
        }
        else return ourInstance;
    }

    private UMLSTicketClient() {}

    public String getTicket(Context context) {
        if (tgt == null) {
            tgt = getTGT(context);
        }
        ticket = "";

        RequestQueue queue = UMLSConnection.getInstance(context).getRequestQueue();

        // Start the queue
        queue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tgt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response222222:" + response);
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                Document doc = Jsoup.parse(response);
//                Elements elements = doc.select("form");
//                tgt = elements.attr("action");
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
        return tgt;
    }

    public String getTGT(Context context){
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
        return tgt;
    }

}
