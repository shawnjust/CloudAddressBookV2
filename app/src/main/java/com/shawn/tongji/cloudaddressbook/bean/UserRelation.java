package com.shawn.tongji.cloudaddressbook.bean;

/**
 * Created by shawn on 5/14/15.
 */
public class UserRelation extends ContactsInfoListGetter {

    private User userFrom;
    private User userTo;
    private Integer relationType;
    private long createTimestamp;
    private long chageTimestamp;

    public UserRelation() {

    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public long getChageTimestamp() {
        return chageTimestamp;
    }

    public void setChageTimestamp(long chageTimestamp) {
        this.chageTimestamp = chageTimestamp;
    }
}