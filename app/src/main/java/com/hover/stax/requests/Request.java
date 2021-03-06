package com.hover.stax.requests;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hover.stax.R;
import com.hover.stax.utils.DateUtils;

@Entity(tableName = "requests")
public class Request {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	public int id;

	@NonNull
	@ColumnInfo(name = "recipient")
	public String recipient;

	@ColumnInfo(name = "amount")
	public String amount;

	@ColumnInfo(name = "note")
	public String note;

	@ColumnInfo(name = "message")
	public String message;

	@ColumnInfo(name = "matched_transaction_uuid")
	public String matched_transaction_uuid;

	@NonNull
	@ColumnInfo(name = "date_sent", defaultValue = "CURRENT_TIMESTAMP")
	public Long date_sent;

	public Request() {
	}

	public Request(String r, String a, String n) {
		recipient = r;
		amount = a;
		note = n;
		date_sent = DateUtils.now();
	}

	public String getDescription(Context c) {
		return c.getString(R.string.descrip_request, recipient);
	}
}
