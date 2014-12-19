package com.giftomaticapp.gift_o_matic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThreadListAdapter extends ArrayAdapter<ForumThread> {
	
	public ThreadListAdapter(Context context, List<ForumThread> threads) {
		super(context, 0, threads);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ForumThread thread = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.thread_list_item, parent, false);
		}
		
		TextView threadTitle = (TextView) convertView.findViewById(R.id.thread_title);
		TextView threadStarter = (TextView) convertView.findViewById(R.id.thread_starter);
		
		threadTitle.setText(thread.title);
		threadStarter.setText("by " + thread.getThreadStarter());
		
		return convertView;
	}
	
	

}
