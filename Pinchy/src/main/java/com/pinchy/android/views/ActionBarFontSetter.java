package com.pinchy.android.views;

import android.app.ActionBar;
import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;

import com.pinchy.android.TypefaceSpan;

/**
 * Created by conormongey on 09/11/14.
 */
public class ActionBarFontSetter {
    public static void set(String title, ActionBar actionBar, Activity activity){
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(activity,"Lobster 1.4.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(s);
        activity.getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
