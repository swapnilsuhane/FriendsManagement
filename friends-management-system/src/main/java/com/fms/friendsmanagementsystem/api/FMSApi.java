package com.fms.friendsmanagementsystem.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.friendsmanagementsystem.api.request.*;
import com.fms.friendsmanagementsystem.api.response.*;
import com.fms.friendsmanagementsystem.controller.FMSController;
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
            CreateFriendRequest createFriendRequest= objectMapper.readValue(request, CreateFriendRequest.class);

            String fromEmail = createFriendRequest.getFriends().get(0);
            String toEmail = createFriendRequest.getFriends().get(1);
            //create a friend
            String res = fmsController.createFriend(fromEmail, toEmail);
            if (res != null) {
                ((ErrorResponse)response).setErrorCode("1001");
                ((ErrorResponse)response).setErrorMessage(res);
            } else{
                response = new SuccessResponse();
            }

        } catch (Exception e) {
            System.out.println("Error in createConnection: " +e);
            ((ErrorResponse)response).setErrorCode("5000");
            ((ErrorResponse)response).setErrorMessage("Something wrong, please check \n " + e.getMessage());
        }

        return response;

    }

    @PostMapping("/retrieve-friend")
    private BaseResponse retrieveFriend(@RequestBody String request){
        System.out.println("retrieveFriend - request: " + request);
        BaseResponse response = new ErrorResponse(); //todo
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RetrieveFriendRequest retrieveFriendRequest= objectMapper.readValue(request, RetrieveFriendRequest.class);
            String email = retrieveFriendRequest.getEmail();
            //retrieve the friends
            Set<String> set = fmsController.retrieveFriend(email);
            response = new RetrieveResponse();
            ((RetrieveResponse)response).setCount(set.size());
            ((RetrieveResponse)response).setFriends(set);

        } catch (Exception e) {
            System.out.println("Error in retrieveFriend: " +e);
            ((ErrorResponse)response).setErrorCode("5000");
            ((ErrorResponse)response).setErrorMessage("Something wrong, please check \n " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/retrieve-common-friend")
    private BaseResponse retrieveCommonFriend(@RequestBody String request){
        System.out.println("retrieveCommonFriend - request: " + request);
        BaseResponse response = new ErrorResponse(); //todo
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RetrieveCommonFriendsRequest commonFriendsRequest= objectMapper.readValue(request, RetrieveCommonFriendsRequest.class);
            String email1 = commonFriendsRequest.getFriends().get(0);
            String email2 = commonFriendsRequest.getFriends().get(1);
            //retrieve the common friends
            Set<String> set = fmsController.retrieveCommonFriend(email1, email2);
            response = new RetrieveResponse();
            ((RetrieveResponse)response).setCount(set.size());
            ((RetrieveResponse)response).setFriends(set);

        } catch (Exception e) {
            System.out.println("Error in create friend: " +e);
            ((ErrorResponse)response).setErrorCode("5000");
            ((ErrorResponse)response).setErrorMessage("Something wrong, please check \n " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/subscribe-friend")
    private BaseResponse subscribeFriend(@RequestBody String request){
        System.out.println("subscribeFriend - request: " + request);
        BaseResponse response = new ErrorResponse(); //todo
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SubscribeFriendRequest subscribeFriendRequest= objectMapper.readValue(request, SubscribeFriendRequest.class);
            String requestor = subscribeFriendRequest.getRequestor();
            String target = subscribeFriendRequest.getTarget();
            //subscribe a friend
            String res = fmsController.subscribeFriend(requestor, target);
            if(res != null){
                ((ErrorResponse)response).setErrorCode("1002");
                ((ErrorResponse)response).setErrorMessage(res);
            } else{
                response = new SuccessResponse();
            }

        } catch (Exception e) {
            System.out.println("Error in subscribeFriend: " +e);
            ((ErrorResponse)response).setErrorCode("5000");
            ((ErrorResponse)response).setErrorMessage("Something wrong, please check \n " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/block-friend")
    private BaseResponse blockFriend(@RequestBody String request){
        System.out.println("blockConnection - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BlockFriendRequest subscribeFriendRequest= objectMapper.readValue(request, BlockFriendRequest.class);
            String requestor = subscribeFriendRequest.getRequestor();
            String target = subscribeFriendRequest.getTarget();
            //subscribe a friend
            String res = fmsController.blockFriend(requestor, target);
            if(res != null){
                ((ErrorResponse)response).setErrorCode("1003");
                ((ErrorResponse)response).setErrorMessage(res);
            } else{
                response = new SuccessResponse();
            }

        } catch (Exception e) {
            System.out.println("Error in blockConnection: " +e);
            ((ErrorResponse)response).setErrorCode("5000");
            ((ErrorResponse)response).setErrorMessage("Something wrong, please check \n " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/retrieve-update-friend")
    private BaseResponse retrieveReceiveUpdateFriend(@RequestBody String request){
        System.out.println("retrieveReceiveUpdateFriend - request: " + request);
        BaseResponse response = new ErrorResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RetrieveReceiveUpdateFriendRequest requestObj =
                objectMapper.readValue(request, RetrieveReceiveUpdateFriendRequest.class);
            String sender = requestObj.getSender();
            String text = requestObj.getText();
            //retrieve receive update list
            Set<String> set = fmsController.retrieveReceiveUpdateFriend(sender, text);
            response = new RetrieveReceiveUpdateFriendResponse(set);
        } catch (Exception e) {
            System.out.println("Error in blockConnection: " +e);
            ((ErrorResponse)response).setErrorCode("5000");
            ((ErrorResponse)response).setErrorMessage("Something wrong, please check \n " + e.getMessage());
        }
        return response;
    }

}
