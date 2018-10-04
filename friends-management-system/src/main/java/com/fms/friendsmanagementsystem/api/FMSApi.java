package com.fms.friendsmanagementsystem.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.friendsmanagementsystem.api.request.*;
import com.fms.friendsmanagementsystem.api.response.*;
import com.fms.friendsmanagementsystem.controller.FMSController;
import com.fms.friendsmanagementsystem.exception.FMSException;
import com.fms.friendsmanagementsystem.util.MessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class FMSApi {

    @Autowired
    FMSController fmsController;

    @PostMapping("/create-friend")
    private BaseResponse createFriend(@RequestBody String request){
        System.out.println("createConnection - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateFriendRequest requestObject;
            try {
                requestObject= objectMapper.readValue(request, CreateFriendRequest.class);
                if(requestObject == null || requestObject.getFriends()==null || requestObject.getFriends().size()==0){
                    throw new Exception();
                }
            } catch (Exception e){
                throw new FMSException(MessageConfig.getMessage("INVALID_REQUEST_ERROR"));
            }

            String fromEmail = requestObject.getFriends().get(0);
            String toEmail = requestObject.getFriends().get(1);
            //create a friend
            String res = fmsController.createFriend(fromEmail, toEmail);
            if (res != null) {
                ((ErrorResponse)response).setErrorCode("1001");
                ((ErrorResponse)response).setErrorMessage(res);
            } else{
                response = new SuccessResponse();
            }

        } catch (FMSException e) {
            ((ErrorResponse)response).setErrorCode(e.getErrorCode());
            ((ErrorResponse)response).setErrorMessage(e.toString());
        } catch (Exception e) {
            ((ErrorResponse)response).setErrorCode("-1000");
            ((ErrorResponse)response).setErrorMessage(e.toString());
        }

        return response;

    }

    @PostMapping("/retrieve-friend")
    private BaseResponse retrieveFriend(@RequestBody String request){
        System.out.println("retrieveFriend - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RetrieveFriendRequest requestObject;
            try {
                requestObject= objectMapper.readValue(request, RetrieveFriendRequest.class);
                if(requestObject == null || requestObject.getEmail()==null){
                    throw new Exception();
                }
            } catch (Exception e){
                throw new FMSException(MessageConfig.getMessage("INVALID_REQUEST_ERROR"));
            }
            //retrieve the friends
            Set<String> set = fmsController.retrieveFriend(requestObject.getEmail());
            response = new RetrieveResponse();
            ((RetrieveResponse)response).setCount(set.size());
            ((RetrieveResponse)response).setFriends(set);

        } catch (FMSException e) {
            ((ErrorResponse)response).setErrorCode(e.getErrorCode());
            ((ErrorResponse)response).setErrorMessage(e.toString());
        } catch (Exception e) {
            ((ErrorResponse)response).setErrorCode("-1000");
            ((ErrorResponse)response).setErrorMessage(e.toString());
        }
        return response;
    }

    @PostMapping("/retrieve-common-friend")
    private BaseResponse retrieveCommonFriend(@RequestBody String request){
        System.out.println("retrieveCommonFriend - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RetrieveCommonFriendsRequest requestObject;
            try {
                requestObject= objectMapper.readValue(request, RetrieveCommonFriendsRequest.class);
                if(requestObject == null || requestObject.getFriends()==null || requestObject.getFriends().size()==0){
                    throw new Exception();
                }
            } catch (Exception e){
                throw new FMSException(MessageConfig.getMessage("INVALID_REQUEST_ERROR"));
            }
            String email1 = requestObject.getFriends().get(0);
            String email2 = requestObject.getFriends().get(1);
            //retrieve the common friends
            Set<String> set = fmsController.retrieveCommonFriend(email1, email2);
            response = new RetrieveResponse();
            ((RetrieveResponse)response).setCount(set.size());
            ((RetrieveResponse)response).setFriends(set);

        } catch (FMSException e) {
            ((ErrorResponse)response).setErrorCode(e.getErrorCode());
            ((ErrorResponse)response).setErrorMessage(e.toString());
        } catch (Exception e) {
            ((ErrorResponse)response).setErrorCode("-1000");
            ((ErrorResponse)response).setErrorMessage(e.toString());
        }
        return response;
    }

    @PostMapping("/subscribe-friend")
    private BaseResponse subscribeFriend(@RequestBody String request){
        System.out.println("subscribeFriend - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SubscribeFriendRequest requestObject;
            try {
                requestObject= objectMapper.readValue(request, SubscribeFriendRequest.class);
                if(requestObject == null || requestObject.getRequestor()==null || requestObject.getTarget()==null){
                    throw new Exception();
                }
            } catch (Exception e){
                throw new FMSException(MessageConfig.getMessage("INVALID_REQUEST_ERROR"));
            }
            //subscribe a friend
            String res = fmsController.subscribeFriend(requestObject.getRequestor(), requestObject.getTarget());
            if(res != null){
                ((ErrorResponse)response).setErrorCode("1002");
                ((ErrorResponse)response).setErrorMessage(res);
            } else{
                response = new SuccessResponse();
            }

        } catch (FMSException e) {
            ((ErrorResponse)response).setErrorCode(e.getErrorCode());
            ((ErrorResponse)response).setErrorMessage(e.toString());
        } catch (Exception e) {
            ((ErrorResponse)response).setErrorCode("-1000");
            ((ErrorResponse)response).setErrorMessage(e.toString());
        }
        return response;
    }

    @PostMapping("/block-friend")
    private BaseResponse blockFriend(@RequestBody String request){
        System.out.println("blockConnection - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BlockFriendRequest requestObject;
            try {
                requestObject= objectMapper.readValue(request, BlockFriendRequest.class);
                if(requestObject == null || requestObject.getRequestor()==null || requestObject.getTarget()==null){
                    throw new Exception();
                }
            } catch (Exception e){
                throw new FMSException(MessageConfig.getMessage("INVALID_REQUEST_ERROR"));
            }
            //subscribe a friend
            String res = fmsController.blockFriend(requestObject.getRequestor(), requestObject.getTarget());
            if(res != null){
                ((ErrorResponse)response).setErrorCode("1003");
                ((ErrorResponse)response).setErrorMessage(res);
            } else{
                response = new SuccessResponse();
            }

        } catch (FMSException e) {
            ((ErrorResponse)response).setErrorCode(e.getErrorCode());
            ((ErrorResponse)response).setErrorMessage(e.toString());
        } catch (Exception e) {
            ((ErrorResponse)response).setErrorCode("-1000");
            ((ErrorResponse)response).setErrorMessage(e.toString());
        }
        return response;
    }

    @PostMapping("/retrieve-update-friend")
    private BaseResponse retrieveReceiveUpdateFriend(@RequestBody String request){
        System.out.println("retrieveReceiveUpdateFriend - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RetrieveReceiveUpdateFriendRequest requestObject;
            try {
                requestObject= objectMapper.readValue(request, RetrieveReceiveUpdateFriendRequest.class);
                if(requestObject == null || requestObject.getSender()==null || requestObject.getText()==null){
                    throw new Exception();
                }
            } catch (Exception e){
                throw new FMSException(MessageConfig.getMessage("INVALID_REQUEST_ERROR"));
            }
            //retrieve receive update list
            Set<String> set = fmsController.retrieveReceiveUpdateFriend(requestObject.getSender(), requestObject.getText());
            response = new RetrieveReceiveUpdateFriendResponse(set);
        } catch (FMSException e) {
            ((ErrorResponse)response).setErrorCode(e.getErrorCode());
            ((ErrorResponse)response).setErrorMessage(e.toString());
        } catch (Exception e) {
            ((ErrorResponse)response).setErrorCode("-1000");
            ((ErrorResponse)response).setErrorMessage(e.toString());
        }
        return response;
    }

}
