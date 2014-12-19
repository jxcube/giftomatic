package com.giftomaticapp.gift_o_matic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThreadPostAdapter extends ArrayAdapter<ForumThread.Post> {
	
	public ThreadPostAdapter(Context context, List<ForumThread.Post> posts) {
		super(context, 0, posts);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ForumThread.Post post = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, parent, false);
		}
		
		TextView author = (TextView) convertView.findViewById(R.id.post_author);
		TextView title = (TextView) convertView.findViewById(R.id.post_title);
		TextView content = (TextView) convertView.findViewById(R.id.post_content);
		
		author.setText(post.getAuthor() + " says:");
		title.setText(post.getTitle());
		content.setText(post.getContent());
		
		return convertView;
	}
	
	
	
}
