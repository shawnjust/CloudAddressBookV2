package com.shawn.tongji.cloudaddressbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.shawn.tongji.cloudaddressbook.R;
import com.shawn.tongji.cloudaddressbook.annotation.Display;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {


    private Integer userId;
    @Display(fieldKey = "用户名", gravity = 1, drawableId = R.drawable.ic_person_black_24dp)
    private String userName;
    private String userPassword;
    @Display(fieldKey = "邮件地址", gravity = 100,drawableId = R.drawable.ic_mail_black_24dp)
    private String userEmail;

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * full constructor
     */
    public User(String userName, String userPassword, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel source) {
        userId = source.readInt();
        userName = source.readString();
        userPassword = source.readString();
        userEmail = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(userPassword);
        dest.writeString(userEmail);
    }

    public List<ContactsInfoItem> getContactsInfoItem() {
        List<ContactsInfoItem> list = new ArrayList<ContactsInfoItem>();
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Display.class)) {
                field.setAccessible(true);
                Display display = field.getAnnotation(Display.class);
                ContactsInfoItem contactsInfoItem = new ContactsInfoItem();
                contactsInfoItem.setKey(display.fieldKey());
                contactsInfoItem.setGravity(display.gravity());
                try {
                    contactsInfoItem.setValue(String.valueOf(field.get(this)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                contactsInfoItem.setIcon(display.drawableId());
                list.add(contactsInfoItem);
            }
        }
        return list;
    }
}