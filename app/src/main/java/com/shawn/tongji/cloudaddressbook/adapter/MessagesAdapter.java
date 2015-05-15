package com.shawn.tongji.cloudaddressbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawn.tongji.cloudaddressbook.R;
import com.shawn.tongji.cloudaddressbook.bean.User;

import java.util.List;

/**
 * Created by shawn on 5/15/15.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    List<User> list;

    public MessagesAdapter(List<User> list) {
        this.list = list;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.message_info, null);
        MessagesViewHolder messagesViewHolder = new MessagesViewHolder(view);
        return messagesViewHolder;
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {
        holder.getNameTextView().setText(list.get(position).getUserName());
        holder.getMessageTextView().setText("请求添加您为好友");
        holder.setUser(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemViewHolderClick {
        void onItemViewHolderClick(MessagesViewHolder viewHolder);
    }

    OnItemViewHolderClick onItemViewHolderClickListener = null;

    public OnItemViewHolderClick getOnItemViewHolderClickListener() {
        return onItemViewHolderClickListener;
    }

    public void setOnItemViewHolderClickListener(OnItemViewHolderClick onItemViewHolderClickListener) {
        this.onItemViewHolderClickListener = onItemViewHolderClickListener;
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView messageTextView;
        User user;

        public MessagesViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemViewHolderClickListener != null) {
                        onItemViewHolderClickListener.onItemViewHolderClick(MessagesViewHolder.this);
                    }
                }
            });
        }

        public TextView getMessageTextView() {
            return messageTextView;
        }

        public void setMessageTextView(TextView messageTextView) {
            this.messageTextView = messageTextView;
        }

        public TextView getNameTextView() {

            return nameTextView;
        }

        public void setNameTextView(TextView nameTextView) {
            this.nameTextView = nameTextView;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
