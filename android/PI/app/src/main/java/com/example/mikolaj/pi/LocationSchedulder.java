package com.example.mikolaj.pi;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;


public class LocationSchedulder extends TimerTask {
    private Context context;
    private LocationTracker locationTracker;


    public LocationSchedulder(Context context){
        this.context = context;
        locationTracker = LocationTracker.getInstance(context);
    }

    public void run() {
        execute();
    }

    public void execute() {
        Map<String, String> comment = new HashMap<String, String>();
        comment.put("x", Double.toString(locationTracker.getLatitude()));
        comment.put("y", Double.toString(locationTracker.getLongitude()));
        comment.put("device_id", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        String json = new GsonBuilder().create().toJson(comment, Map.class);
        Log.i("!! ",json);
        makeRequest("https://dzik.herokuapp.com/add_location", json);
    }

    public HttpResponse makeRequest(String uri, String json) {
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            return new DefaultHttpClient().execute(httpPost);
        }catch(Exception e){Log.i("@ER",e.toString());}
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}