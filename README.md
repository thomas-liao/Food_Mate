# 2018-group-18
# Current End Points (temporary, to be modified):
  1. localhost:8080  login and group chatting (with backend: simple message model(model), chatting controller,  front end: simple HTML and css.main, js.main in resources)
  2. localhost:8080/users,  list all users
  3. (GET) localhost:8080/users/{id}, look for user by id, return 404 if not found.
  4. (DELETE) localhost:8080/users/{id}, delete user by id, return 404 if not found.
  5. (POST) JSON to localhost:8080/users, add user (assigned uid sequentially, respond URI of added (User) user object - for future convenience.
  
  Backend test:
  src/test/java/com/oose/group18/Controller/SpringDataJpaTest.java
  which includes 5 tests:
  (1)    Find user by id
  (2)    Find user name
  (3)    Find restaurant by Id
  (4)    Find all user
  (5)    Find all restaurant


# Food Mate app

Lots of people have to eat alone and want company, so we want to develop a mobile app to help people look for “food-mates”. By using this app, we hope to connect strangers through food. We allow users to post invitations to dine together at specified times at restaurants they choose, and others can make requests to join. This app matches people who are in need of company and share the same love for food. In the not too distant future, we can imagine newer version of the app can serve as a social network for people sharing the same interests.
