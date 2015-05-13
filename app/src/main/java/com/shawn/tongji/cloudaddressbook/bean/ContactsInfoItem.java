package com.shawn.tongji.cloudaddressbook.bean;

/**
 * Created by shawn on 5/13/15.
 */
public class ContactsInfoItem {
    public String key;
    public String value;
    public int icon;
    public int gravity;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
}
