package com.fms.friendsmanagementsystem.api.request;

import java.util.List;

public class RetrieveCommonFriendsRequest {
    List<String> friends;

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        if(friends !=null && friends.size()>0){
            return friends.stream().reduce("RetrieveCommonFriendsRequest: ", (a,b) -> (a + ", " + b));
        } else {
            return null;
        }
    }
}
