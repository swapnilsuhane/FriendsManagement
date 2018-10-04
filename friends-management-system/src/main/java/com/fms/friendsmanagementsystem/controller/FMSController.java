package com.fms.friendsmanagementsystem.controller;

import com.fms.friendsmanagementsystem.dao.FMSDao;
import com.fms.friendsmanagementsystem.domain.FMSRelationDO;
import com.fms.friendsmanagementsystem.util.MessageConfig;
import com.fms.friendsmanagementsystem.validate.FMSValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class FMSController implements BaseController{

    @Autowired
    FMSDao fmsDao;

    public String createFriend(String fromEmail, String toEmail){
        if(FMSValidator.isInValidEmail(fromEmail) || FMSValidator.isInValidEmail(toEmail)){
            return "Invalid Email address";
        }
        FMSRelationDO fmsRelationDO = new FMSRelationDO();
        fmsRelationDO.setEmailTo(toEmail);
        fmsRelationDO.setFriend(true);
        return fmsDao.createConnection(fromEmail, fmsRelationDO);
    }

    public Set<String> retrieveFriend(String email){
        if(FMSValidator.isInValidEmail(email)){
            return new TreeSet<>();
        }
        Set<FMSRelationDO> set = fmsDao.queryConnectionList(email);
        if (set !=null && set.size()>0) {
            return set.stream()
               //  .filter(r -> !r.isBlocked()) //filter blocked friends ?? not clear
                .filter(r -> r.isFriend())  //only friends
                .map(r -> r.getEmailTo())
                .collect(Collectors.toSet());
        } else{
            return new TreeSet<>();
        }
    }

    public Set<String> retrieveCommonFriend(String email1, String email2){
        Set<String> commonFriends = new TreeSet<>();
        if(FMSValidator.isInValidEmail(email1) || FMSValidator.isInValidEmail(email1)){
            return commonFriends;
        }
        Set<String> friends1  = retrieveFriend(email1);
        Set<String> friends2  = retrieveFriend(email2);

        for (String f1 : friends1) {
            for (String f2: friends2) {
                if(f1.equalsIgnoreCase(f2)){
                    commonFriends.add(f1);
                }
            }
        }
        return commonFriends;
    }

    public String subscribeFriend(String requestor, String target){
        if(FMSValidator.isInValidEmail(requestor) || FMSValidator.isInValidEmail(target)){
            return MessageConfig.getMessage("INVALID_EMAIL_ERROR");
        }
        boolean alreadyFriend = isFriendAlreadyExists(requestor, target);

        if(alreadyFriend) {
            return MessageConfig.getMessage("SUBSCRIBER_ALREADY_FRIEND_ERROR");
        }

        FMSRelationDO fmsRelationDO = new FMSRelationDO();
        fmsRelationDO.setEmailTo(target);
        fmsRelationDO.setSubscribed(true);

        fmsDao.createConnection(requestor, fmsRelationDO);

        return null;
    }

    public String blockFriend(String requestor, String target){
        if(FMSValidator.isInValidEmail(requestor) || FMSValidator.isInValidEmail(target)){
            return MessageConfig.getMessage("INVALID_EMAIL_ERROR");
        }
        FMSRelationDO fmsRelationDO = fmsDao.queryConnection(requestor, target);

        //friend exists
        if(fmsRelationDO != null){
            fmsRelationDO.setBlocked(true);
            fmsDao.updateConnection(requestor, fmsRelationDO);
        } else{
            fmsRelationDO = new FMSRelationDO();
            fmsRelationDO.setEmailTo(target);
            fmsRelationDO.setBlocked(true);
            fmsDao.createConnection(requestor, fmsRelationDO);
        }
        return null;
    }

    public Set<String> retrieveReceiveUpdateFriend(String sender, String text){
        Set<String> responseSet = new TreeSet<>();
        if(FMSValidator.isInValidEmail(sender)){
            return responseSet;
        }
        Set<FMSRelationDO> connections = fmsDao.queryConnectionList(sender);
        //not blocked connections (subscription and friends)
        if (connections !=null && connections.size()>0) {
            responseSet =  connections.stream()
                .filter(r -> !r.isBlocked())
                .map(r -> r.getEmailTo())
                .collect(Collectors.toSet());
        }

        //@mentioned in the update
        responseSet.addAll(getMentionedEmails(text));

        return responseSet;
    }

    private boolean isFriendAlreadyExists(String email1, String email2){
        Set<String> friends  = retrieveFriend(email1);

        if(friends !=null && friends.size()>0) {
            long count = friends.stream().filter(f -> f.equalsIgnoreCase(email2)).count();
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    private static Set<String> getMentionedEmails(String text){
        Set<String> set = new TreeSet<>();
        //has any email
        if(text.contains("@")){
            String[] sa = text.split(" |,|;"); //consider email separated by space or comma (,) or semicolon (;)
            for (String str: sa) {
                //System.out.println(str);
                if(str.contains("@") && str.contains(".")){
                    set.add(str);
                }
            }
        }
        return set;
    }

}
