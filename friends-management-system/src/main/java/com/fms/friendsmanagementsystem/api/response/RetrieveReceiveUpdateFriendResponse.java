package com.fms.friendsmanagementsystem.api.response;

import java.util.Set;

public class RetrieveReceiveUpdateFriendResponse extends BaseResponse{
    Set<String> recipients;

    public RetrieveReceiveUpdateFriendResponse(Set<String> recipients) {
        this.recipients = recipients;
        super.success = true;
    }

    public Set<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<String> recipients) {
        this.recipients = recipients;
    }
}
