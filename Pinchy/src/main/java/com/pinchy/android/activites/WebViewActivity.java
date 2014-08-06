package com.pinchy.android.activites;

/**
 * Created by conormongey on 04/05/2014.
 */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ShareActionProvider;

import com.pinchy.android.R;
import com.pinchy.android.models.LobsterStory;

public class WebViewActivity extends Activity {
    private ShareActionProvider mShareActionProvider;
    private WebView webView;
    LobsterStory story;
    static String TAG = "WebView";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Bundle extras = getIntent().getExtras();
        int story_index = 0;
        if (extras != null) {
            story_index = extras.getInt("story_index");
        }
        story = LobsterStory.hottest().get(story_index);

        if(story.isSelfPost()){
            Intent intent = new Intent(this, CommentsViewActivity.class);
            intent.putExtra("story_index", story_index);
            startActivity(intent);
            return;
        }
        Log.d(TAG,"url:" +  story.url  + " /");
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(story.title);
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(story.url);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webview, menu);

        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain").getIntent();
        shareIntent.putExtra(Intent.EXTRA_TEXT, story.url);

        setShareIntent(shareIntent);
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
            case R.id.action_search:
                intent = new Intent(this, CommentsViewActivity.class);
                intent.putExtra("story_index",story.index);
                startActivity(intent);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}