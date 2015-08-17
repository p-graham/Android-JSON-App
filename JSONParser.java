package edu.niu.cs.graham.z1690752.jsonexample;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by Phil Graham on 4/21/2015.
 * json parser class
 */
public class JSONParser
{
    InputStream is = null;
    JSONObject jObj = null;
    String json = "";

    public JSONParser()
    {
        //default constructor
    }

    public JSONObject getJSONFromUrl(String urlInputString)
    {
        URL url = null;
        try
        {
            url = new URL(urlInputString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine())!= null)
            {
                sb.append(line + '\n');
            }
            json = sb.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            jObj = new JSONObject(json);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jObj;
    }
}
