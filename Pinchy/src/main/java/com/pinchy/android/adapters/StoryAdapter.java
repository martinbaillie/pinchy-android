package com.pinchy.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pinchy.android.LobsterAPIClient;
import com.pinchy.android.activites.CommentsViewActivity;
import com.pinchy.android.models.LobsterStory;
import com.pinchy.android.NetworkCallback;
import com.pinchy.android.R;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by conormongey on 04/05/2014.
 */
public class StoryAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private int page = 2;
    private Context context;
    private boolean storyFetchInProgress = false;
    private ArrayList<LobsterStory> stories;

    public StoryAdapter(Context context, ArrayList stories) {
        super(context, R.layout.story_view);
        this.stories = stories;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void refresh(ArrayList<LobsterStory> stories){
        this.page = 2;
        this.stories = stories;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount(){
        return stories.size();
    }

    static class ViewHolder {
        TextView title;
        TextView submitter;
        TextView comment_count;
        TextView votes;
        View comment_section;
    };


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("Story Adapter", "Pos: " + position);
        final LobsterStory item = stories.get(position);
        ViewHolder viewHolder;

        if (convertView==null){
            convertView = inflater.inflate(R.layout.story_view, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.story_title);
            viewHolder.submitter = (TextView) convertView.findViewById(R.id.submitter);
            viewHolder.comment_count = (TextView) convertView.findViewById(R.id.comments);
            viewHolder.votes = (TextView) convertView.findViewById(R.id.votes);
            viewHolder.comment_section = convertView.findViewById(R.id.comment_box);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final StoryAdapter adapter = this;
        if((position > LobsterStory.hottest().size() - 5) && !storyFetchInProgress){
            storyFetchInProgress = true;
            LobsterAPIClient.getHottest(page, new NetworkCallback() {
                @Override
                public void onSuccess() {
                    adapter.page += 1;
                    storyFetchInProgress = false;
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure() {

                }
            });
        }
        viewHolder.title.setText(item.title);
        viewHolder.submitter.setText(item.user);
        viewHolder.comment_count.setText(item.comment_count + "");
        viewHolder.votes.setText(item.score + "");
        viewHolder.comment_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsViewActivity.class);
                intent.putExtra("story_index", position);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}