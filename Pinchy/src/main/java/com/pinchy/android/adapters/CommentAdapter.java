package com.pinchy.android.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pinchy.android.LobsterAPIClient;
import com.pinchy.android.RoundedTransformation;
import com.pinchy.android.models.LobsterComment;

import com.pinchy.android.NetworkCallback;
import com.pinchy.android.R;
import com.squareup.picasso.Picasso;

import java.util.Vector;

/**
 * Created by conormongey on 04/05/2014.
 */
public class CommentAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;

    private Context context;
    RoundedTransformation roundedCorners;
    Vector<LobsterComment> comments;
    public CommentAdapter(Context context,Vector<LobsterComment> comments ) {
        super(context, R.layout.story_view);
        this.comments = comments;
        this.context = context;
        this.roundedCorners = new RoundedTransformation(30,0);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount(){
        return comments.size();
    }

    static class ViewHolder {
        TextView comment;
        View innerContainer;
        ImageView avatar;
        TextView username;
    };


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("Story Adapter", "Pos: " + position);
        final LobsterComment item = comments.get(position);
        ViewHolder viewHolder;

        if (convertView==null){
            convertView = inflater.inflate(R.layout.comment_view, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.comment = (TextView) convertView.findViewById(R.id.comment_data);
            viewHolder.innerContainer = convertView.findViewById(R.id.comment_container);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.commenting_user_avatar);
            viewHolder.username = (TextView) convertView.findViewById(R.id.commenting_user_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(context)
                .load(item.user.avatarUrl)
                .transform(roundedCorners)
                .into(viewHolder.avatar);
        viewHolder.comment.setText(Html.fromHtml(item.htmlData));
        viewHolder.username.setText(item.user.username);
        float shiftRight = 30 * (item.indentLevel - 1);
        viewHolder.innerContainer.setPadding((int) shiftRight, 0, 0, 0);
        return convertView;
    }
}