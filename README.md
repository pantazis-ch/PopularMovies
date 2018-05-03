# Popular Movies

With the Popular Movies app you can browse through lists of movies sorted either by popularity or by rating. For each movie you can view information such as the rating, the release date and the overview. In addition, you can watch and share YouTube trailers related to the movie and view movie reviews. Finally, you can create a list of favorite movies which is accessible both online and offline.

<p align="center">
  <img src="https://drive.google.com/uc?id=1WPHaLyW16si8l5EUBxhKDDnJYVUjFj1U" width="300" height="450"> &nbsp<img src="https://drive.google.com/uc?id=1SvSqHTc_mibCLVNC1gMixu7R4EmRMGHn" width="300" height="450">
</p>

<p align="center">
  <img src="https://drive.google.com/uc?id=1pF8jspbw9TD9YkMrK71N-Co0J1kw_UJB" width="300" height="450">
</p>

<p align="center">
  <img src="https://drive.google.com/uc?id=11nGvuTIBqxxqhEnMuTH_BFj4Ajxvd-q2" width="300" height="450"> &nbsp<img src="https://drive.google.com/uc?id=1wgOS48gPVygj5Tm4bVX_gpJ6_9GdDG9u" width="300" height="450">
</p>
  

## Installation

In order to use the app you should enter your TMDB Api Key in the app/build.gradle file.

Replace MY_TMDB_API_KEY with your Api Key.

## Technical Details

1. In order to get the movie lists and the information about each movie the app makes [**network requests**](https://developer.android.com/training/basics/network-ops/) to the TMDb database and then [**parses the JSON data**](https://developer.android.com/reference/org/json/package-summary) that it receives. With every request the app gets a page of data which consists of twenty movies. When the user reaches at the end of the list then a new request is made for another page of data. Each network request is made on a seperate thread with the help of the [**AsyncTaskLoader**](https://developer.android.com/reference/android/content/AsyncTaskLoader).
2. The app uses a [**RecyclerView**](https://developer.android.com/guide/topics/ui/layout/recyclerview) to show the lists of movie posters. The movie posters are loaded with the help of the [**Picasso library**](http://square.github.io/picasso/).
3. When the user clicks on a movie poster the detail activity opens. The detail activity contains the following information: Title, Rating, Release Date, Overview, Trailers, Reviews.
4. The user can add a movie to the favorites list by clicking on the 'heart' symbol. All the data is stored in an [**SQLite Database**](https://developer.android.com/training/data-storage/sqlite) and is accessed with the help of a [**Content Provider**](https://developer.android.com/guide/topics/providers/content-providers). The images are stored in the [**Internal Storage**](https://developer.android.com/training/data-storage/files#WriteInternalStorage).
5. Both the trailers and the reviews use [**ExpandableListViews**](https://developer.android.com/reference/android/widget/ExpandableListView).
6. The app makes use of [**Implicit Intents**](https://developer.android.com/training/basics/intents/). When the user clicks on a trailer the YouTube app opens. If the user wants to share the trailer link he should click on the 'share' icon.
