# Changelog
All notable changes to this project will be documented in this file.

## [Unreleased]
### Added
- the remaining function implementations: 
### Changed
- code refactoring and improvement

## [0.3.0] - 2018-12-9

### Added

- implemented pipeline 
- implemented rating function for user to add rating for restaurants
- implemented pipeline to update user preference based on review
- added detailed description about posts to let guest review
- implemented review history function in front-end to let user review the posts they posted or joined
- refactor code to be extendable

### Changed

- change getGuest function to return userView
- change getPost function to return postView


## [0.2.0] - 2018-11-18
### Added
- implemented post recommendation system for guests
- added frontend interface, including post recommendation, review history
- added frontend-backend communication, including send post, get recommendation posts, join post, review history
- add backend test
- add frontend test
### Changed
- change return type of GET recommendation to postView
- change listView to recycleView

## [0.1.0] - 2018-10-29
### Added
- functional frontend with all major intended functionalities
- restaurant recommendation algorithm on the backend 
- functional backend with all major intended app functionalities
- implemented the following backend endpoints regarding user login functions: user login, user register, user information review, delete user, get all user information
- implemented the following backend endpoints regarding host functions: get restaurant list, review post, create post, reject guest, get guest list, delete post
- implemented the following backend endpoints regarding guest functions: get post, join post, review post
