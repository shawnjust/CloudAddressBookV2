package com.shawn.tongji.cloudaddressbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawn.tongji.cloudaddressbook.R;
import com.shawn.tongji.cloudaddressbook.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shawn on 5/13/15.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    List<User> list;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public ContactsAdapter() {
        list = new ArrayList<User>();
    }

    public ContactsAdapter(List<User> list) {
        this.list = list;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.contacts_list_item, null);
        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(view);
        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        User user = list.get(position);
        holder.getNameTextView().setText(user.getUserName());
        holder.getEmailTextView().setText(user.getUserEmail());
        holder.setUser(user);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    OnContactsViewHolderClick onContactsViewHolderClick = null;

    public OnContactsViewHolderClick getOnContactsViewHolderClick() {
        return onContactsViewHolderClick;
    }

    public void setOnContactsViewHolderClick(OnContactsViewHolderClick onContactsViewHolderClick) {
        this.onContactsViewHolderClick = onContactsViewHolderClick;
    }

    public interface OnContactsViewHolderClick {
        public void onContactsViewHolderClick(ContactsViewHolder viewHolder);
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView emailTextView;
        User user;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            emailTextView = (TextView) itemView.findViewById(R.id.emailTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onContactsViewHolderClick != null) {
                        onContactsViewHolderClick.onContactsViewHolderClick(ContactsViewHolder.this);
                    }
                }
            });
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public void setNameTextView(TextView nameTextView) {
            this.nameTextView = nameTextView;
        }

        public TextView getEmailTextView() {
            return emailTextView;
        }

        public void setEmailTextView(TextView emailTextView) {
            this.emailTextView = emailTextView;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
