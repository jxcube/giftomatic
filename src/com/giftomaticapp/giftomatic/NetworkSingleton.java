package com.giftomaticapp.giftomatic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class NetworkSingleton {
	private static NetworkSingleton instance;
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	private static Context ctx;
	
	private NetworkSingleton(Context context) {
		ctx = context;
		requestQueue = getRequestQueue();
		
		imageLoader = new ImageLoader(requestQueue,
				new ImageLoader.ImageCache() {
					private final LruCache<String, Bitmap>
						cache = new LruCache<String, Bitmap>(20);
					
					@Override
					public void putBitmap(String url, Bitmap bitmap) {
						cache.put(url, bitmap);
					}
					
					@Override
					public Bitmap getBitmap(String url) {
						return cache.get(url);
					}
				});
	}
	
	public RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
		}
		return requestQueue;
	}
	
	public static synchronized NetworkSingleton getInstance(Context ctx) {
		if (instance == null) {
			instance = new NetworkSingleton(ctx);
		}
		return instance;
	}
	
	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}
	
	public ImageLoader getImageLoader() {
		return imageLoader;
	}
}
