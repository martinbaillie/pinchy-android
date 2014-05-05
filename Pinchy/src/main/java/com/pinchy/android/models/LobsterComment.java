package com.pinchy.android.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by conormongey on 04/05/2014.
 */
public class LobsterComment {

    public LobsterUser user;
    public String htmlData;
    public int indentLevel;
    public int score;

    public LobsterComment(JSONObject data){
        try{
            this.htmlData = data.getString("comment");
            this.user = new LobsterUser(data.getJSONObject("commenting_user"));
            this.indentLevel = data.getInt("indent_level");
            this.score = data.getInt("score");

        }catch (JSONException e){

        }
    }
}
