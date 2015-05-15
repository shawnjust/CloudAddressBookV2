package com.shawn.tongji.cloudaddressbook.bean;

import com.shawn.tongji.cloudaddressbook.R;
import com.shawn.tongji.cloudaddressbook.annotation.Display;

import java.util.List;

public class User extends ContactsInfoListGetter {


    private Integer userId;
    @Display(fieldKey = "用户名", gravity = 1, drawableId = R.drawable.ic_person_black_24dp)
    private String userName;
    private String userPassword;
    @Display(fieldKey = "邮件地址", gravity = 100, drawableId = R.drawable.ic_mail_black_24dp)
    private String userEmail;
    private List<UserContactInfo> userContactInfoList;

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

    public List<UserContactInfo> getUserContactInfoList() {
        return userContactInfoList;
    }

    public void setUserContactInfoList(List<UserContactInfo> userContactInfoList) {
        this.userContactInfoList = userContactInfoList;
    }
}