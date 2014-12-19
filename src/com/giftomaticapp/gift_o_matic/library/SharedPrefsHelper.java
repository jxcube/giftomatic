package com.giftomaticapp.gift_o_matic.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefsHelper {
	final String prefs = "com.giftomaticapp.giftomatic.LOGIN_DATA";
	final String auth = "authenticated";
	final String user = "username";
	final String mail = "email";
	Context context;
	static SharedPrefsHelper singleton;

	private SharedPrefsHelper(Context context) {
		this.context = context;
	}

	public static SharedPrefsHelper getHelper(Context context) {
		if (singleton == null) {
			singleton = new SharedPrefsHelper(context);
		}
		return singleton;
	}

	private SharedPreferences getPrefs() {
		return context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
	}

	public boolean getAuthenticated() {
		return getPrefs().getBoolean(auth, false);
	}

	public void setAuthenticated(boolean value) {
		getEditor().putBoolean(auth, value);
	}

	public String getUsername() {
		return getPrefs().getString(user, "no username found");
	}

	public void setUsername(String value) {
		getEditor().putString(user, value);
	}

	public String getEMail() {
		return getPrefs().getString(mail, "no Email found");
	}

	public void setEmail(String value) {
		getEditor().putString(mail, value);
	}

	public void savePrefs() {
		getEditor().commit();
	}

	private Editor getEditor() {
		return getPrefs().edit();
	}
}
