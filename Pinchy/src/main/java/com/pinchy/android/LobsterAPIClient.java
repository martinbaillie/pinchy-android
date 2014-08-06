package com.pinchy.android;

/**
 * Created by conormongey on 04/05/2014.
 */
import android.util.Log;

import com.loopj.android.http.*;
import com.pinchy.android.models.LobsterComment;
import com.pinchy.android.models.LobsterStory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LobsterAPIClient {
    private static AsyncHttpClient _sharedClient = null;
    private static String TAG = "APIClient";
    private static AsyncHttpClient sharedClient(){
        if(_sharedClient == null) {
            _sharedClient = new AsyncHttpClient();
        }
        return _sharedClient;

    }
    private static String baseUrl(){
        return "https://lobste.rs/";
    }

    public static void getComments(final LobsterStory story, final NetworkCallback callback){
        final String url = baseUrl() + "s/" + story.short_id + ".json";
        sharedClient().get(url,null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                // Pull out the first event on the public timeline
                try {
                    story.resetComments();
                    JSONArray comments = response.getJSONArray("comments");
                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject comment_data = comments.getJSONObject(i);
                        LobsterComment comment = new LobsterComment(comment_data);
                        story.comments.add(comment);

                    }
                    callback.onSuccess();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                Log.d("FUCK", url);

            }
        });
    }

    public static void getHottest(Integer page, final NetworkCallback callback){
        if(page == null){
            page = 1;
        }
        String url = baseUrl() + "hottest.json?page=" + page;
        sharedClient().get(url,null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray stories) {
            // Pull out the first event on the public timeline
                for(int i=0; i< stories.length(); i++){
                    JSONObject story = null;
                    try {
                        story = stories.getJSONObject(i);
                        LobsterStory.addHottest(story, i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                callback.onSuccess();
            }
        });


    }

}
