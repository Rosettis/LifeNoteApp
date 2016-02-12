package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

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
 * Created by Peter on 12/02/16.
 */
public class UMLS_Api {
    private static UMLS_Api ourInstance;
    private String tgt;
    private String ticket;
    private onResponseListener listener;

    public interface onResponseListener {
        void onResonse (String response);
    }

    public static UMLS_Api getInstance() {
        if (ourInstance == null) {
            ourInstance = new UMLS_Api();
            return ourInstance;
        }
        else return ourInstance;
    }

    private UMLS_Api() {}

    //gets api ST ticket
    public void getApiTicket(Context context, onResponseListener listener) {
        this.listener = listener;
        if (tgt == null) {
            tgt = retrieveTGT(context);
        }
        ticket = null;
    }


    public String retrieveTGT(Context context){
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
                listener.onResonse(tgt);
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
