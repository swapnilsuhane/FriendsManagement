package com.fms.friendsmanagementsystem.dao;

import com.fms.friendsmanagementsystem.domain.FMSRelationDO;
import com.fms.friendsmanagementsystem.util.MessageConfig;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FMSDao implements BaseDao{
    ConcurrentHashMap<String, Set<FMSRelationDO>> map = new ConcurrentHashMap<>();

    //create a new connection (friend, subscription)
    public String createConnection(String fromEmail, FMSRelationDO fmsRelationDO){
        String response = null;
        try {
            if(map.get(fromEmail)!= null && map.get(fromEmail).size()>0){
                Set<FMSRelationDO> set = map.get(fromEmail);
                boolean connectionExists = false;

                if(set.size() > 0) {
                    long count = set.stream().filter(
                        e -> e.getEmailTo().equalsIgnoreCase(fmsRelationDO.getEmailTo())).count();
                    connectionExists = count >0? true: false;
                }

                //add only when not present - idempotent
                if(!connectionExists) {
                    set.add(fmsRelationDO);
                    map.put(fromEmail, set);
                } else {
                    FMSRelationDO relationDO= queryConnection(fromEmail, fmsRelationDO.getEmailTo());
                    if(relationDO.isBlocked()){
                        response = MessageConfig.getMessage("FRIEND_BLOCKED_ERROR");
                    } else if(relationDO.isSubscribed()){
                        relationDO.setFriend(true);
                        updateConnection(fromEmail, relationDO);
                    } else {
                        response = MessageConfig.getMessage("ALREADY_FRIEND_ERROR");
                    }
                }

            } else {
                Set<FMSRelationDO> set = new LinkedHashSet<>();
                set.add(fmsRelationDO);
                map.put(fromEmail, set);
            }

        } catch (Exception e){
            return e.getMessage();
        }

        return response;
    }

    //Query connections list for an email address.
    public Set<FMSRelationDO> queryConnectionList(String email){
        return map.get(email);
    }

    //Query the connection for an email address.
    public FMSRelationDO queryConnection(String email1, String email2){
        if(map.get(email1)!=null && map.get(email1).size()>0) {
            Optional<FMSRelationDO> optionalRelationDO = map.get(email1).stream()
                .filter(f -> f.getEmailTo().equalsIgnoreCase(email2))
                .findFirst();

            if(optionalRelationDO.isPresent()){
                return optionalRelationDO.get();
            } else {
                return null;
            }
        } else{
            return null;
        }
    }

    //update the connection relation attributes (friend, subscription, block etc)
    public String updateConnection(String fromEmail, FMSRelationDO fmsRelationDO){
        try {
            map.get(fromEmail).stream().forEach( r -> {
                                        if(r.getEmailTo().equalsIgnoreCase(fmsRelationDO.getEmailTo())){
                                            r.setFriend(fmsRelationDO.isFriend());
                                            r.setSubscribed(fmsRelationDO.isSubscribed());
                                            r.setBlocked(fmsRelationDO.isBlocked());
                                        }
            });
        } catch (Exception e){
            return e.getMessage();
        }
        return null;
    }
}
