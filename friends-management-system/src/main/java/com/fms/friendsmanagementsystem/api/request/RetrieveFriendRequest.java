package com.fms.friendsmanagementsystem.api.request;

public class RetrieveFriendRequest {
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}
