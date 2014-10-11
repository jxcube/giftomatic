package com.giftomaticapp.giftomatic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Alert {
	public static void alert(Context ctx, String title, String message) {
		new AlertDialog.Builder(ctx)
		.setTitle(title)
		.setMessage(message)
		.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		})
		.show();
	}
}
