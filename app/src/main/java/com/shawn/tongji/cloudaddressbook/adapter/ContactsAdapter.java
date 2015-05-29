package com.shawn.tongji.cloudaddressbook.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawn.tongji.cloudaddressbook.R;
import com.shawn.tongji.cloudaddressbook.bean.User;
import com.shawn.tongji.cloudaddressbook.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


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
        list = new ArrayList<>();
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
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        User user = list.get(position);
        holder.getNameTextView().setText(user.getUserName());
        holder.getEmailTextView().setText(user.getUserEmail());
        holder.setUser(user);
        if (user.getUserHeader() != null && !"".equals(user.getUserHeader())) {
            MySharedPreferences.getInstance().getUserHeader(user, false, new MySharedPreferences.OnBitMapGetCallback() {
                @Override
                public void onGetBitmap(Bitmap bitmap) {
                    if (bitmap != null) {
                        holder.getUserHeaderImageView().setImageBitmap(bitmap);
                    }
                }
            });
        }
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
        void onContactsViewHolderClick(ContactsViewHolder viewHolder);
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView emailTextView;
        CircleImageView userHeaderImageView;
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
            userHeaderImageView = (CircleImageView) itemView.findViewById(R.id.userHeaderImageView);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }


        public TextView getEmailTextView() {
            return emailTextView;
        }


        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public CircleImageView getUserHeaderImageView() {
            return userHeaderImageView;
        }

    }
}
