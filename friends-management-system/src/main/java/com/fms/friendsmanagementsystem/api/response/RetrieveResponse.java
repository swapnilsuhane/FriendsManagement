package com.fms.friendsmanagementsystem.api.response;

import java.util.Set;

public class RetrieveResponse extends BaseResponse{
    Set<String> friends;
    int count;

    public RetrieveResponse() {
        super.success = true;
    }

    public Set<String> getFriends() {
        return friends;
    }

    public void setFriends(Set<String> friends) {
        this.friends = friends;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
