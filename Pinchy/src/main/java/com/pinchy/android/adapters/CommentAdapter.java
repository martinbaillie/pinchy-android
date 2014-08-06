package com.pinchy.android.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinchy.android.LobsterAPIClient;
import com.pinchy.android.RoundedTransformation;
import com.pinchy.android.models.LobsterComment;

import com.pinchy.android.NetworkCallback;
import com.pinchy.android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by conormongey on 04/05/2014.
 */
public class CommentAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;

    private Context context;
    RoundedTransformation roundedCorners;
    ArrayList<LobsterComment> comments;
    public CommentAdapter(Context context,ArrayList<LobsterComment> comments ) {
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
        viewHolder.innerContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                expand(v.findViewById(R.id.comment_data));
                return false;
            }
        });
        viewHolder.comment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

//                Toast toast = Toast.makeText(context, text, duration);
//                v.setVisibility(View.INVISIBLE);
                collapse(v);
//                toast.show();
                return false;
            }
        });
        return convertView;
    }
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        Log.d("VH", "collapsing");

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(100);
        v.startAnimation(a);
    }
    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();
        Log.d("VH", "expanding");

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
            if(interpolatedTime == 1){
                v.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                notify();
                v.requestLayout();
                v.getParent().requestLayout();
                v.invalidate();

            }else{
                v.getLayoutParams().height = (int)(targtetHeight * interpolatedTime);
            }
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}