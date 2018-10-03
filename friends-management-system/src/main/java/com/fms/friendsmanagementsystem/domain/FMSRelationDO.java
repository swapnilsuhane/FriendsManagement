package com.fms.friendsmanagementsystem.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FMSRelationDO {
    private String emailTo;

    //default=false
    private boolean isFriend;

    //default=false
    private boolean isSubscribed;

    //default=false
    private boolean isBlocked;

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
