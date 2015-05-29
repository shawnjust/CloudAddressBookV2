package com.shawn.tongji.cloudaddressbook.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawn.tongji.cloudaddressbook.R;
import com.shawn.tongji.cloudaddressbook.bean.ContactsInfoItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ContactsInfoAdapter extends RecyclerView.Adapter<ContactsInfoAdapter.ContactsInfoViewHolder> {

    private final Context context;
    List<ContactsInfoItem> list;

    public ContactsInfoAdapter(Context context, List<ContactsInfoItem> list) {
        this.context = context;
        this.list = list;
    }

    public List<ContactsInfoItem> getList() {
        return list;
    }

    public void setList(List<ContactsInfoItem> list) {
        this.list = list;
        Collections.sort(this.list, new Comparator<ContactsInfoItem>() {
            @Override
            public int compare(ContactsInfoItem lhs, ContactsInfoItem rhs) {
                if (lhs.getGravity() > rhs.getGravity()) {
                    return 1;
                } else if (lhs.getGravity() == rhs.getGravity()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        this.notifyDataSetChanged();
    }

    @Override
    public ContactsInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.contacts_info_with_icon_item, null);
        ContactsInfoViewHolder contactsInfoViewHolder = new ContactsInfoViewHolder(view);
        return contactsInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsInfoViewHolder holder, int position) {
        ContactsInfoItem item = list.get(position);
        holder.getKeyTextView().setText(item.getKey());
        holder.getValueTextView().setText(item.getValue());
        ImageView imageView = holder.getImageView();
        if (item.getIcon() == -1) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            Drawable drawable = context.getResources().getDrawable(item.getIcon());
            if (drawable != null) {
                drawable.setColorFilter(context.getResources().getColor(R.color.primaryColorDark), PorterDuff.Mode.SRC_ATOP);
            }
            imageView.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        Log.i("viewType", "View Type: " + viewType);
        return viewType;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ContactsInfoViewHolder extends RecyclerView.ViewHolder {


        TextView keyTextView;
        TextView valueTextView;
        ImageView imageView;

        public ContactsInfoViewHolder(View itemView) {
            super(itemView);
            keyTextView = (TextView) itemView.findViewById(R.id.keyTextView);
            valueTextView = (TextView) itemView.findViewById(R.id.valueTextView);
            imageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        }

        public TextView getKeyTextView() {
            return keyTextView;
        }

        public TextView getValueTextView() {
            return valueTextView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
