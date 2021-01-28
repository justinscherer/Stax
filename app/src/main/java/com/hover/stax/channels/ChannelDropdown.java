package com.hover.stax.channels;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hover.sdk.utils.VolleySingleton;
import com.hover.stax.R;

import java.util.List;

public class ChannelDropdown extends TextInputLayout {

	private TextInputLayout input;
	private AutoCompleteTextView textView;
	private TextView link;

	private boolean showSelected, showLink, showError = false;
	private Channel highlightedChannel;

	public ChannelDropdown(Context context, AttributeSet attrs) {
		super(context, attrs);
		getAttrs(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.channel_dropdown, this);
		input = findViewById(R.id.channel_dropdown_input);
		textView = findViewById(R.id.channel_autoComplete);
		link = findViewById(R.id.new_account_link);
		fillFromAttrs();
	}

	private void getAttrs(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChannelDropdown, 0, 0);
		try {
			showSelected = a.getBoolean(R.styleable.ChannelDropdown_showSelected, true);
			showLink = a.getBoolean(R.styleable.ChannelDropdown_showLink, false);
		} finally {
			a.recycle();
		}
	}

	private void fillFromAttrs() {
		link.setOnClickListener(v -> toggleLink(false));
		toggleLink(showLink);
	}

	public void updateChannels(List<Channel> channels) {
		if (highlightedChannel == null)
			textView.setText("");
		ChannelDropdownAdapter channelDropdownAdapter = new ChannelDropdownAdapter(ChannelDropdownAdapter.sort(channels, showSelected), getContext());
		textView.setAdapter(channelDropdownAdapter);
		textView.setOnItemClickListener((adapterView, view2, pos, id) -> highlightChannel((Channel) adapterView.getItemAtPosition(pos)));
	}

	private void highlightChannel(Channel channel) { highlightedChannel = channel; }

	public Channel getHighlighted() { return highlightedChannel; }

	public void toggleLink(boolean showLink) {
		link.setVisibility(showLink ? VISIBLE : GONE);
		input.setVisibility(showLink ? GONE : VISIBLE);
		if (showLink) {
			reset();
		}
	}

	public void showError(boolean show) {
		input.setError(show ? getContext().getString(R.string.refresh_balance_error) : null);
		input.setErrorIconDrawable(show ? R.drawable.ic_error_warning_24dp : 0);
		if (show)
			textView.setText(getContext().getString(R.string.link_an_account), false);
	}

	public void reset() {
		if (VolleySingleton.isConnected(getContext()))
			textView.setText("");
		highlightedChannel = null;
	}
}
