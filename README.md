# FriendsManagement
Friends Management system (FMS) provides different APIs for social networking 

You can use either of ways to call APIs -

Public Cloud (AWS)
------------------
These API has been deployed into my AWS public cloud 
- Swagger URL : http://52.74.189.137:8080/swagger-ui.html#/fms-api

- Use these IP to call the API-
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"friends":["swapnil@example.com","reyom@example.com"]}' \
  http://52.74.189.137:8080/create-friend 
  
NOTE: If API service goes down, please contact me I will start the service again.

Launch locally using Jar (attached in repo)
-------------------------------------------
1. Download the attached jar and unzip it your local machine. (https://github.com/swapnilsuhane/FriendsManagement/blob/master/FMS.jar.zip)
2. run command: java -jar FMS.jar 
3. Service should be up : http://localhost:8080/swagger-ui.html#/fms-api


Setup/Launch project locally in IDE
------------------------------------

Steps to Setup/Launch project Locally-
1. Clone this repository into your local.
2. Import the cloned directory into your IDE (IntelliJ or Eclipse)
3. Import the maven dependencies
4. Launch the application by running Spring boot FriendsManagementSystemApplication.java
5. Check the local URL : http://localhost:8080/swagger-ui.html#/fms-api (swagger page)


