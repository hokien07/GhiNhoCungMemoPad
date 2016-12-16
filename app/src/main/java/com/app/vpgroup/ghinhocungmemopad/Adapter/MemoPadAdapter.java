package com.app.vpgroup.ghinhocungmemopad.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.vpgroup.ghinhocungmemopad.MainActivity;
import com.app.vpgroup.ghinhocungmemopad.R;
import com.app.vpgroup.ghinhocungmemopad.model.MemoPad;
import com.app.vpgroup.ghinhocungmemopad.util.FontServices;

import java.util.List;

import static android.R.attr.width;

/**
 * Created by HoKien on 12/15/2016.
 */

public class MemoPadAdapter extends ArrayAdapter<MemoPad> {
    private int with;

    public MemoPadAdapter(Context context, List<MemoPad> data){
        super(context, R.layout.adapter_memopad);
        with = (context.getResources().getDisplayMetrics().widthPixels - 80) / 3;
        for (MemoPad memoPad: data){
            add(memoPad);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MemoPad memoPads = getItem(position);
        convertView =
                LayoutInflater.from(getContext()).inflate(R.layout.adapter_memopad, parent, false);
        TextView textView = (TextView) convertView;
        textView.getLayoutParams().width = width;
        textView.getLayoutParams().height = width + 30;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).onActionAddOrViewMemoPad(memoPads);
            }
        });
        FontServices.getInstance(getContext()).setTypeface(textView);
        if (!TextUtils.isEmpty(memoPads.content))
            textView.setText(memoPads.content);
        return convertView;
    }
}
