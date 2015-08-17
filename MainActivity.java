package edu.niu.cs.graham.z1690752.jsonexample;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Phil Graham on 4/21/2015.
 * json parser class
 */

public class MainActivity extends ListActivity
{
    Button btnView;
    ProgressDialog pDialog;
    final static String SUCCESSTAG = "success";
    final static String MESSAGETAG = "message";
    final static String PLACELISTTAG = "places";
    final static String PLACETAG = "Place";
    final static String NUMBERTAG = "Number";
    final static String URL_IN = "http://faculty.cs.niu.edu/~gbrown/tmp/jsonEx/placeJSON.php";
    JSONArray placeArray = null;
    ArrayList<HashMap<String,String>> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnView = (Button)findViewById(R.id.buttonView);
        btnView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //call asyncTask method to be done in the background
                new LoadPlaces().execute();
            }
        });
    }

    public void upDateJSONData()
    {
        /*
        *retrieve the JSON data by using the jsonParser class
        * then instantiating the ArrayList
         */
        placeList = new ArrayList<HashMap<String,String>>();
        JSONParser jParser = new JSONParser();
        try
        {
            JSONObject jObj = jParser.getJSONFromUrl(URL_IN); //returns entire object
            placeArray = jObj.getJSONArray(PLACELISTTAG);//returns the array of places
            for(int i = 0; i < placeArray.length(); i++)
            {
                JSONObject jsonTemp = placeArray.getJSONObject(i);
                String p = jsonTemp.getString(PLACETAG);
                String n = jsonTemp.getString(NUMBERTAG);
                //create a hashMap item
                HashMap<String,String> map = new HashMap<String, String>();
                map.put(PLACETAG,p);
                map.put(NUMBERTAG,n);
                placeList.add(map);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }//end upDateJSONData

    private void updateList()
    {
        ListAdapter adapter = new SimpleAdapter(this,placeList,R.layout.singleplace,
                new String[]{PLACETAG,NUMBERTAG},new int[]{R.id.textViewplace, R.id.textViewNumber});
        setListAdapter(adapter);
    }

    public class LoadPlaces extends AsyncTask<Void,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            upDateJSONData();
            return true;
        }

        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading place information");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            pDialog.dismiss();
            updateList();
        }
    }//end asyncTask class
}
