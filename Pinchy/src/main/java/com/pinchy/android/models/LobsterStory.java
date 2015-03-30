package com.pinchy.android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by conormongey on 04/05/2014.
 */
public class LobsterStory {
    public String[] tags;
    public String title;
    public String url;
    public int comment_count;
    public String comments_url;
    public String short_id;
    public int score;
    public String user;
    public int index;
    public ArrayList<LobsterComment> comments = new ArrayList<LobsterComment>();
    private static ArrayList<LobsterStory> hottest= new ArrayList<LobsterStory>();

    public static ArrayList<LobsterStory> hottest(){
        return hottest;
    }
    public static void resetHottest(){
        hottest().clear();
    }
    public LobsterStory(JSONObject data){
        try {
            this.comment_count = data.getInt("comment_count");
            this.url = data.getString("url");
            this.score = data.getInt("score");
            this.title = data.getString("title");
            this.url = data.getString("url");
            this.short_id = data.getString("short_id");
            JSONArray tags = data.getJSONArray("tags");
            this.tags = new String[tags.length()];
            for (int i = 0; i < tags.length(); i++) {
                this.tags[i] = tags.getString(i);
            }
            JSONObject submitter = data.getJSONObject("submitter_user");
            this.user = submitter.getString("username");
        }catch (JSONException e){

        }
    }
    public static void addHottest(JSONObject data, int index){
        LobsterStory story = new LobsterStory(data);
        story.index = index;
        hottest.add(story);
    }

    public boolean isSelfPost(){
        return this.url.isEmpty();
    }

    public void resetComments(){
        comments.clear();
    }
    @Override
    public String toString(){
        return this.title;
    }
}
