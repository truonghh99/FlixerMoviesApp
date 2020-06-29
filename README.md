# Project 2 - *What's in the theater?*

**What's in the theater?** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **23** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings and popularity within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [x] Add a searchbar for user to search movies from the current list
* [x] Add the ability to sort movie list based on rating / release date
* [x] Add a customized library
  * [x] Create a storage class to store selected movies 
  * [x] Create a separate activity to show library when requested
  * [x] Allow user to modify their library (add and delete movies)
* [x] Add the ability to authenticate user
  * [x] Add Login & Register activity
  * [x] Connect to Firebase to store users' information


## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src="https://github.com/truonghh99/Flixster/blob/master/Demolight.gif" title='Video Walkthrough' alt='Video Walkthrough' />

In landscape mode:

<img src="https://github.com/truonghh99/Flixster/blob/master/landscape2.gif" title='Video Walkthrough' alt='Video Walkthrough' />


GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

This is the first time I've ever used database. Even though Firebase is user friendly, the experience as a whole is definitely challenging. I've also had a tough time exploring the toolbar and menu items, yet I'm glad that I finally got a general idea of how to deal with those things. 

## Open-source libraries usedI 

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
