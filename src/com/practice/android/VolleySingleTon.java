package com.practice.android;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class VolleySingleTon {

	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
	private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static Context mContext;
	private static VolleySingleTon mInstance;
	private JSONArray jsonArray;

	@SuppressWarnings("static-access")
	private VolleySingleTon(Context context) {
		this.mContext = context;
		mRequestQueue = getRequestQueue();
		mImageLoader = new ImageLoader(this.mRequestQueue,
				new DiskLruImageCache(context, context.getPackageCodePath(),
						DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT,
						DISK_IMAGECACHE_QUALITY));
	}

	public static synchronized VolleySingleTon getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new VolleySingleTon(context);
		}
		return mInstance;
	}

	public void addJsonArrayRequest(String url) {
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						setJsonArray(response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						setJsonArray(null);
					}
				});
		addToRequestQueue(jsonArrayRequest);
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(mContext
					.getApplicationContext());
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}

	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	private void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
}