package com.pinchy.android.activites;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pinchy.android.LobsterAPIClient;
import com.pinchy.android.NetworkCallback;
import com.pinchy.android.R;
import com.pinchy.android.TypefaceSpan;
import com.pinchy.android.adapters.StoryAdapter;
import com.pinchy.android.models.LobsterStory;


public class StoryActivity extends Activity {

    StoryAdapter adapter;

    private void setActionBarTitle(String title, ActionBar actionBar, Context context){
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(context,"Lobster 1.4.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);
    }

    public void refresh(){
        LobsterStory.resetHottest();
        LobsterAPIClient.getHottest(1, new NetworkCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh(LobsterStory.hottest());
                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        setActionBarTitle("Lobste.rs", this.getActionBar(), this);

        //setup list adapter

        final ListView storyFeed = (ListView) findViewById(R.id.stories);
        final Context context = this;
        adapter = new StoryAdapter(context, LobsterStory.hottest());
        storyFeed.setAdapter(adapter);
        refresh();
        storyFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("story_index",position);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.action_settings:
                intent = new Intent(this, UserSettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
