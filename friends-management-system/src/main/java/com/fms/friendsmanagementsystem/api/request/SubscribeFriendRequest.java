package com.fms.friendsmanagementsystem.api.request;

public class SubscribeFriendRequest {
    private String requestor;
    private String target;

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
