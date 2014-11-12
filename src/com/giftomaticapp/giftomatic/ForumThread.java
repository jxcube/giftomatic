package com.giftomaticapp.giftomatic;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

	public class ForumThread {
		String title, username, content;
		List<String> post;
		
		public ForumThread() {
			
		}

		public ForumThread(String title, String username, String content, List<String> post) {
			this.title = title;
			this.username = username;
			this.content = content;
			this.post = post;
		}
		
		public ForumThread(JSONObject thread) throws JSONException {
			title = thread.getString("title");
			username = thread.getString("username");
			content = thread.getString("content");
			post = new ArrayList<String>();
			for (int i = 0; i < thread.getJSONArray("post").length(); i++) {
				post.add(thread.getJSONArray("post").getString(i));
			}
		}
		
}
