package com.pinchy.android.activites;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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

import java.util.Vector;

public class CommentsViewActivity extends Activity {
    private void setActionBarTitle(String title, ActionBar actionBar, Context context){
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(context,"Lobster 1.4.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

// Update the action bar title with the TypefaceSpan instance
        actionBar.setTitle(s);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("Lobste.rs", getActionBar(), this);
        setContentView(R.layout.activity_comments_view);
        Bundle extras = getIntent().getExtras();
        final LobsterStory story;
        int story_index = 0;
        if (extras != null) {
            story_index = extras.getInt("story_index");
        }
        story = LobsterStory.hottest().get(story_index);
        final ListView storyFeed = (ListView) findViewById(R.id.commentsView);
        final Context context = getApplicationContext();
        Vector<LobsterComment> comments = story.comments;
        final CommentAdapter adapter = new CommentAdapter(context, comments);
        storyFeed.setAdapter(adapter);

        LobsterAPIClient.getComments(story, new NetworkCallback() {
            @Override
            public void onSuccess() {
                for (LobsterComment comment : story.comments){
                    Log.d("Comments", comment.toString());

                }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
