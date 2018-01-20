# Popular Movies

With the Popular Movies app you can browse through lists of movies sorted either by popularity or by rating. For each movie you can view information such as the rating, the release date and the overview. In addition, you can watch and share YouTube trailers related to the movie and view movie reviews. Finally, you can create a list of favorite movies which is accessible both online and offline.

<p align="center">
  <img src="https://drive.google.com/uc?id=1WPHaLyW16si8l5EUBxhKDDnJYVUjFj1U" width="300" height="450"> &nbsp<img src="https://drive.google.com/uc?id=1SvSqHTc_mibCLVNC1gMixu7R4EmRMGHn" width="300" height="450">
</p>
  

## Installation

In order to use the app you should enter your TMDB Api Key in the app/build.gradle file.

Replace MY_TMDB_API_KEY with your Api Key.

## Technical Details

1. In order to get the movie lists and the information about each movie the app makes requests to the TMDb database and then parses the JSON data that it receives. With every request the app gets a page of data which consists of twenty movies. When the user reaches at the end of the list then a new request is made for another page of data.
2. The app uses a RecyclerView to show the lists of movie posters. The movie posters are loaded with the help of the Picasso library.
3. When the user clicks on a movie poster the detail activity opens. The detail activity contains the following cases:
..*Title
..*Rating
..*Release Date
..*Overview
..*Trailers
..*Reviews
