package com.hover.stax.requests;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.hover.stax.R;

import java.util.List;

public class RecipientAdapter extends RecyclerView.Adapter<RecipientAdapter.RecipientViewHolder> {
	private List<String> recipients;
	private UpdateListener updateListener;

	RecipientAdapter(List<String> numbers, UpdateListener listener) {
		this.recipients = numbers;
		updateListener = listener;
	}

	void update(List<String> numbers) { recipients = numbers; notifyDataSetChanged(); }

	@NonNull
	@Override
	public RecipientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipient_input, parent, false);
		return new RecipientViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final @NonNull RecipientViewHolder holder, int position) {
		holder.input.setText(recipients.get(position), TextView.BufferType.EDITABLE);
		holder.input.addTextChangedListener(new TextWatcher() {
			@Override public void afterTextChanged(Editable s) { }
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateListener.onUpdate(position, s.toString());
			}
		});
		holder.contactButton.setOnClickListener(view -> updateListener.onClickContact(position, holder.view.getContext()));
	}

	public interface UpdateListener {
		void onUpdate(int pos, String recipient);
		void onClickContact(int index, Context c);
	}

	static class RecipientViewHolder extends RecyclerView.ViewHolder {
		final View view;
		final EditText input;
		final AppCompatImageButton contactButton;

		RecipientViewHolder(@NonNull View itemView) {
			super(itemView);
			view = itemView;
			input = itemView.findViewById(R.id.recipient_input);
			contactButton = itemView.findViewById(R.id.contact_button);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		return recipients == null ? 0 : recipients.size();
	}
}