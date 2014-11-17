package com.giftomaticapp.giftomatic;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

	public class ForumThread {
		String title;
		List<Post> posts;
		int id;
		
		public ForumThread(String title, List<Post> posts, int id) {
			this.title = title;
			this.posts = posts;
			this.id = id;
		}
		
		public String getThreadStarter() {
			return posts.get(posts.size()-1).getAuthor();
		}
		
		public static ForumThread fromJson(JSONObject json) throws JSONException {
			int id = json.getInt("id");
			String title = json.getString("title");
			List<Post> posts = new ArrayList<Post>();
			JSONArray postsArray = json.getJSONArray("Posts");
			for (int i = 0; i < postsArray.length(); i++) {
				JSONObject postObj = postsArray.getJSONObject(i);
				posts.add(new Post(postObj.getString("title"), postObj.getString("content"), postObj.getJSONObject("User").getString("username")));
			}
			return new ForumThread(title, posts, id);
		}
		
		public static List<ForumThread> toThreadList(JSONArray arr) throws JSONException {
			List<ForumThread> threads = new ArrayList<ForumThread>();
			for (int i = 0; i < arr.length(); i++) {
				List<Post> posts = new ArrayList<Post>();
				JSONObject obj = arr.getJSONObject(i);
				JSONArray postsArray = obj.getJSONArray("Posts");
				for (int j = 0; j < postsArray.length(); j++) {
					JSONObject postObj = postsArray.getJSONObject(j);
					posts.add(new Post(postObj.getString("title"), postObj.getString("content"), postObj.getJSONObject("User").getString("username")));
				}
				threads.add(new ForumThread(obj.getString("title"), posts, obj.getInt("id")));
			}
			return threads;
		}
		
		public static class Post {
			
			private String title, content, author;
			
			public Post(String title, String content, String author) {
				this.title = title;
				this.content = content;
				this.author = author;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getAuthor() {
				return author;
			}

			public void setAuthor(String author) {
				this.author = author;
			}
			
			
			
		}
		
}
