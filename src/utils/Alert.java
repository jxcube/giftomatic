package utils;

import android.content.Context;
import android.widget.Toast;

public class Alert {
	
	public static void alert(Context c, String msg) {
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}
	
}
