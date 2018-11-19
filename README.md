# 2018-group-18
# Welcome to Food Mate World!

We have built the basic framework and implemented some core features. Feel free to play with our app!

## Back End

For back end we choose springboot and h2 database as server and database to store user and restaurant information. The whole project is under restful-web-services folder. To run the whole projects, user Intellij to import the whole project, waiting for dependency downloaded, and click run button, you are ready to go!

### Currently Implemented endpoint

**GET /users**

Acquire username, fullName, description and E-mail information of all users using view control (sensitive information like password, username etc. got filtered out – for security reason).

**POST /login**

Simple login functionality, for each user, check username and password, if login success, return userId as string. Otherwise return “-1”

**POST /register**

Simple register functionality. Take in user object (with username, password, personal informations etc in it) and save using userRepository. Return uri of created user (expose uri).

**GET /user/{id}**

This one is similar to /users but instead of getting information(of course, the view got filtered) of all users, if search user object by user id and after filtering return view for this user.

**DELETE /user/{id}**

Pretty self-explanatory. This one deletes user from userRepository by id.

**GET /user{id}/host/restaurants **

(The recommendation module comes into play) we used matching algorithm(collaborative filtering algorithm, https://en.wikipedia.org/wiki/Collaborative_filtering)  to recommend top k(by default, 10) restaurants for host. 

**GET /user/{id}/host/posts**

Get post history of host (referenced by ID). 

**POST /user/{id}/host/posts/{restaurantId}**

Host create POST end point. This one is used for host to post POST (e.g. which restaurant, time, preferences etc). The restaurant information is referenced by restaurant ID(from restaurantRepository) and the host information is referenced by user id(from userRepository). And the post containing all those information is stored in POST, which is persisted to postRepository 

**GET /user/{id}/host/posts/{postId}/guests**

Get guest information(referenced by user id) who responded to post(posted by host).

**POST /user/{id}/host/posts/{postId}/guests/{guestId}**

This end point is used by host to reject guest. The rejection method takes in host’s user id, guest’s user id, post id s.t. guest is rejected by host from post.

**DELETE /user/{id}/host/posts/{post_id}**

Host delete post.

**POST /user/{id}/guest/{post_id}**

Guest join POST (accept invitation from host). More concretely, post information is updated with new guest information stored in post. Weblistener is used such that this process could be asynchronized (for example, it doesn’t make sense to have host waiting in the whole invitation process, which is why a weblistener is used here).

**GET /user/{id}/guest/posts**

For guest to look up all joined posts.

There is an easy way to check our server! We deployed the whole server on Heroku, so you can eaily check the endpoint of our server by accessing: https://food-mate.herokuapp.com as the base url, the detail of endpoints are stored in FoodMate.postman_collection.json.


### Recommender System

We've implemented an SVD-based collaborative filtering system to perform ranking on all available hosts according to the history of the guest and his/her similarity with other guests. 

#### Introduction

We maintain an m by n matrix M where m denotes the numbre of users and n denotes the number of restaurants, the (i,j)-th element of which represents the rating of user i to restaurant j. The matrix will be sparse because the user will only have visited a few of all restaurants in our system. And the missing elements in that matrix are estimated based on a factorization M = P^T*Q. This method represents user i as a vector of preferences for a few factors and item j as a vector where each element expresses how much the item exhibits that factor. Then the rating of item j for user i is estimated by the inner product of these two vectors. 

#### Model Training and Testing

For model training, we only minimize loss over observed values and regularize P and Q. We use stochastic gradient descent  (SGD) to minimize the loss function. Since we don't have data for our system, we created some synthetic user history data which are generated from 5 independent distributions. We crawled information of ~300 restaurants and generated 100 users for testing. With 10% data of the whole matrix, we achieve a RMSE of 1.07 in the rating scale of 1 to 5. 

In production mode, we will periodically update the original matrix and perform new factorization on it. We haven't look into the cold start problem, which could be addressed in later iterations using content-based recommendation.

#### Implementation

The core code is currently using Python and called by Java ProcessBuilder class. We will consider rewrite it in Java and add more logic on top of that. 

Some python packages are needed to run the whole project. run

`pip install -r requirement.txt`

`pip install -r requirement1.txt`

Since the packages in requirement1.txt are depend on packages in requirement.txt, the order is important.

## Front End

Now let's look at our Android App!

We designed and implemented the UI sketch and implemented some core features for the interaction between user and server. The whole project is under FoodMate folder. 

To build the project, use Android studio to import the whole project, waiting for the project to compile, then click run button, you will be asked to choose the USB device or simulator. SInce the target sdk version is 21, we strongly recommend you to use Android version larger than this level. For most of the testing, we use Pixel 28 as the simulator, so it will give you the best user experience.

Now let's take a look at our app!

### Login
<img align="middle" src="./Image/login.png" alt="login" width="250"/><br/><br/>

The initial page is the login page. User need to enter username and password to login. You can use the registered account (it's in our database): username: Amy, password Amy to login.

<img  src="./Image/signup.png" alt="signup" width="250"/><br/><br/>


Wanna create your own account? No problem! Click create one will send you to registration page where you can fill your basic information to create a new account. Then you will jump to login page to login will your own account.

### Choose Role

<img  src="./Image/ChooseRole.png" alt="Choose Role" width="250"/><br/><br/>


User can choose to be host or guest.

### Host:

<img src="./Image/restaurant.png" alt="drawing" width="250"/><br/><br/>


After choosing to be host, the user can get a list of recommended restaurants (by our recommendation system!). Then the user can choose the restaurant he/she likes to make a post.

We have switched from ListView to RecyclerView in iteration4.

### Send Post

<img src="./Image/GuestList.png" alt="Send Post" width="250"/><br/><br/>

Then the user can fill the form and send the post to the server and wait for guests to join.


After finishing sending the post, users will enter a message box, where they could get messages from guests. They will also be able to retrive their history of posts by clicking the "history" button in the bottom of the page. We have switched from ListView to RecyclerView in iteration4.


### Guest

<img src="./Image/postRecommendation.png" alt="Post Recommendation" width="250"/><br/><br/>

After choosing to be guest, the user will receive a list of recommended posts. The guest can choose which post he/she likes to join. We have switched from ListView to RecyclerView in iteration4.

## Frontend test

We have several frontend test (instrumentation/UI testing)implemented through Espresso framework for Android. The tests are located in: /Users/vince/OOSE project/2018-group-18/FoodMate/app/src/androidTest/java/jhu/oose/group18/foodmate. 

To run these UI tests, after Gradle finish building the whole project, find the individual test file (each file is for an activity) you would like to run in jhu.oose.group18.foodmate(androidTest), and the test file can be run directly within Android Studio. 

Due to asyncronous work involved in testing, each test is not guarenteed to succeed each time. Multiple runs are recommened to get the correct behavior. Also Recyclerview testing library dependency issues need to be solved in the future iteration. 

## Backend test
Java JUnit test for JPA and web layer tests are placed in restful-web-services/src/test/java/com/oose/group18/Controller/JPAResourceTest.java. Most of the important endpoints (12/14, 85% coverage rate) has been tested. Backend unit tests have been deployed to Travis-CI and each time we push to master the backend unit tests are run automatically.

The end-points tests covered including:
1. GET /users, get all users.
2. POST /login, user login.
3. POST /register, add new user (register).
4. GET /user/{id}, get single user.
5. DELETE /user/{id}, delete single user.
6. GET /user/{id}/host/restaurants, get user's restaurants.
7. GET /user/{id}/host/posts, get user(as host)/s posts.
8. Get /user/{id}/host/posts/{postId}/guests, get the guests list in posts(referenced by post ID).
9. DELETE /user/{id}/host/posts/{post_id}, delete post referenced by post ID.
10. POST user/{id}/guest/posts, get all recommended posts.
11. POST /user/{id}/guest/{post_id}, guest join in post.
12. GET /user/{id}/guest/posts, get guests joined posts history.

<img src="./Image/backend_tests.jpg" alt="Post Recommendation" width="250"/><br/><br/>


# What need to Improve

1. We have successfully implemented whole functionality of app, the next iteration we will focus on code quality and implement extended feature to imcrease user experience.
2. We will set up pipeline of recommendation system, to let user add review of restaurant and update their user habbits.
3. Add more tests for backend and frontend to make the project more robust.

# Food Mate app

Lots of people have to eat alone and want company, so we want to develop a mobile app to help people look for “food-mates”. By using this app, we hope to connect strangers through food. We allow users to post invitations to dine together at specified times at restaurants they choose, and others can make requests to join. This app matches people who are in need of company and share the same love for food. In the not too distant future, we can imagine newer version of the app can serve as a social network for people sharing the same interests.
