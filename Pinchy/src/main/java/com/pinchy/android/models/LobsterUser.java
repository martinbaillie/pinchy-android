package com.pinchy.android.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by conormongey on 04/05/2014.
 */
public class LobsterUser {
    public boolean isAdmin=false;
    public boolean isModerator=false;
    public String username;
    public String avatarUrl;

    public LobsterUser(JSONObject data){
        try{
            this.avatarUrl = data.getString("avatar_url");
            this.username = data.getString("username");
            this.isAdmin = data.getBoolean("is_admin");
            this.isModerator = data.getBoolean("is_moderator");
        }catch (JSONException e){

        }
    }
}
