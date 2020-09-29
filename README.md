# Gymific
<img src="/previews/preview_1.gif" align="right" width="33%"/>

Gymific is a home workout app illustrating Android development best practices: Hilt, Preferences DataStore, WorkManager, DataBinding, Kotlin Coroutines & Flow.

## Features
  + Gymific has 3 main destinations that can be accessible from anywhere in the app by Bottom Navigation.
  + The Home and Workout screens start with lists of pre-populated data. Loading raw data into Room database is scheduled with WorkManager's OneTimeWorkRequest.
  + The Workout screen consists of ViewPager2 with Tabs that organize workouts across 3 different screens basing on workout's categories.
  + The Favourite screen displays list of user's selections. Adding a workout to the list of favourite takes place by pressing the icon in the menu. The icon will changed appropriately indicating an option to remove the.
  + The list of favourite has an option to sort data. Jetpack DataStore is used to persist sort order.
  + The Detail screen demonstrates custom ProgressBar that displays running time in the center of the "moving" ring.
  

## Tech stack & Open-source libraries
### Android Architecture Components & good practices: </b>
  - DataBinding - the app binds the UI components in the XML layout to data sources using a DataBinding rather than programmatically.
  - Room - Access app's SQLite database with in-app objects and compile-time checks.
  - DataStore - used to persist sort order.
  - ViewModel - UI related data holder, lifecycle aware.
  - Lifecycles - Create a UI that automatically responds to lifecycle events.
  - LiveData - Build data objects that notify views when the underlying database changes.
  - Navigation for navigation between different screens. 
  - SafeArgs for passing data between fragments.
  - Dagger-Hilt for dependency injection.
  
### Third party:
<img src="/previews/preview_2.gif" align="right" width="33%"/>

  - Kotlin Coroutines  and Flow for managing background threads with simplified code and reducing needs for callbacks
  - Timber for logs.
  
### Architecture:
  - MVVM Architecture 
  - Repository pattern
  
## Testing 
  - Under development.

## Design
+ Gymific is built with Material Components for Android.
+ The app has a Bottom Navigation, which provides access to main destinations.
+ The home screen displays horizontally and vertically oriented lists. 
+ The Workout screen has swipe views with tabs (using ViewPager2) and top app bars, that hides on scroll.
+ The detail screen demonstrates custom ProgressBar with gradient ring.
+ The buttons, menus and progress indicators are customized for colors and shapes.
+ All clickable components behave intuitively changing their appearance when they are pressed.

## Preview
<img src="/previews/screen_1.png" width="33%" /> <img src="/previews/screenshot_2.png" width="33%" /> <img src="/previews/screenshot_3.png" width="33%"/>
                                  
## Resource
Gymific uses graphic resources from [freepik](https://www.freepik.com).