package com.example.android_project;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.Message;
import com.example.android_project.R;
import android.widget.LinearLayout;

import java.util.List;

import io.noties.markwon.Markwon;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages;
    private Markwon markwon;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        markwon = Markwon.create(parent.getContext());
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
//        holder.messageTextView.setText(message.getContent());

        // Get the parent LinearLayout
        LinearLayout parentLayout = (LinearLayout) holder.messageTextView.getParent();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8, 4, 8, 4);

        // Configure alignment and styling based on sender
        if (message.isSentByUser()) {
            layoutParams.gravity = Gravity.END;
            holder.messageTextView.setText(message.getContent());
            holder.messageTextView.setBackgroundResource(R.drawable.sender_message_bg);
            holder.messageTextView.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.white));
        } else {
            layoutParams.gravity = Gravity.START;
            markwon.setMarkdown(holder.messageTextView, message.getContent());
            holder.messageTextView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
            holder.messageTextView.setBackgroundResource(R.drawable.receiver_message_bg);
            holder.messageTextView.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));
        }

        holder.messageTextView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}