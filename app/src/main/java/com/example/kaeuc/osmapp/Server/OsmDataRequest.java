package com.example.kaeuc.osmapp.Server;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kaeuc.osmapp.Extras.Constants;
import com.example.kaeuc.osmapp.Extras.Place;
import com.example.kaeuc.osmapp.Extras.OsmXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by kaeuc on 10/22/2016.
 */

public class OsmDataRequest extends AsyncTask<String,String,String> {

    private static final String TAG = "OsmDataRequest" ;
    private OsmDataRequestResponse callBack = null;
    private ProgressBar progressBar;

    private Context parentContext;
    private String filtro = null;

    public OsmDataRequest(Context parentContext, ProgressBar progressBar) {
        this.parentContext = parentContext;
        this.callBack = (OsmDataRequestResponse) parentContext;
        this.progressBar = progressBar;
    }

    @Override
    protected String doInBackground(String... params) {
        filtro = params[0];
        String xmlResponse = null;


        HttpURLConnection connection = null;
        BufferedReader reader = null;
        /*Server URL*/
        URL url = null;
        try{
            // Essa url deve se parecer algo como http://www.overpass-api.de/api/xapi?*[key=value][bbox=-48.46069,-1.47956,-48.45348,-1.47158]
            url = new URL(Constants.SERVER_URL+filtro + Constants.BOUNDING_BOX);
            Log.i(TAG,"Request sent to: "+ url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout( 10000 /*milliseconds*/ );
            connection.setConnectTimeout( 10000 /* milliseconds */ );
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setRequestProperty("Accept", "application/xml");
            connection.connect();


            //Receives the response
            InputStream inputStream = connection.getInputStream();

            StringBuffer buffer = new StringBuffer();
            // Empty response
            if(inputStream == null){
                return null;
            }
            reader =  new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            xmlResponse = buffer.toString();

            return xmlResponse;

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String xmlIncome) {
        super.onPostExecute(xmlIncome);

        OsmXmlParser parser = new OsmXmlParser();
        try {
            final List<Place> locais = parser.parse(xmlIncome);

            //Retorna os valores para a activity que chamou a ASyncTask
            callBack.osmTaskCompleted(locais,filtro);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        progressBar.setVisibility(View.GONE);


    }

}
