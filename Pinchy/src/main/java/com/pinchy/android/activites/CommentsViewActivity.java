package com.pinchy.android.activites;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.pinchy.android.LobsterAPIClient;
import com.pinchy.android.NetworkCallback;
import com.pinchy.android.R;
import com.pinchy.android.TypefaceSpan;
import com.pinchy.android.adapters.CommentAdapter;
import com.pinchy.android.adapters.StoryAdapter;
import com.pinchy.android.models.LobsterComment;
import com.pinchy.android.models.LobsterStory;
import com.pinchy.android.views.ActionBarFontSetter;

import java.util.ArrayList;
import java.util.Vector;

public class CommentsViewActivity extends Activity {
    LobsterStory story;
    private int story_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_view);
        setStory();
        ActionBarFontSetter.set(story.title, getActionBar(), this);
        ListView storyFeed = (ListView) findViewById(R.id.commentsView);
        final CommentAdapter adapter = new CommentAdapter(this, story.comments);
        storyFeed.setAdapter(adapter);

        LobsterAPIClient.getComments(story, new NetworkCallback() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comments_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id){
            case R.id.action_settings:
                intent = new Intent(this, UserSettingActivity.class);
                break;
            case R.id.action_open_web:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("story_index", story.index);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    private void setStory(){
        Bundle extras = getIntent().getExtras();
        story_index = 0;
        if (extras != null) {
            story_index = extras.getInt("story_index");
        }
        story = LobsterStory.hottest().get(story_index);
    }
}
