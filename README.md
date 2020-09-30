# Gymific
<img src="/previews/preview_2.gif" align="right" width="33%"/>

Gymific is a home workout app illustrating Android development best practices: Hilt, Preferences DataStore, WorkManager, DataBinding, Kotlin Coroutines & Flow.

## Overview
  + Gymific has 3 main destinations that can be accessible from anywhere in the app by the Bottom Navigation set with Navigation Components.
  + The Home and Workout screens start with lists of <b>pre-populated data</b>. Loading raw data into Room database is scheduled with <b>WorkManager's OneTimeWorkRequest</b>.
  + The Workout screen consists of <b>ViewPager2 with Tabs</b> that organize workouts across 3 different screens basing on workout's categories.
  + The Favourite screen displays list of user's selections. The list of favourite has an option to sort data. UserPreferenceRepository class holds the sort order, defined as an enum. The current sort order is saved in <b>Jetpack DataStore</b>.
  + The Detail screen demonstrates <b>custom ProgressBar</b> that displays running time in the center of the "moving" ring.
  + WorkoutRepository is responsible for providing the workouts and exposes it via <b>Flow</b> and <b>LiveData</b>.
  + Data Binding Library is used to display recurrent lists and workout details, handle clickListeners and manage buttons behaviours. Where data binding was not an intuitive option, the UI components were bind programmatically.

## Tech stack & Open-source libraries
### Android Architecture Components & good practices: </b>
  - DataBinding - the app binds the UI components in the XML layout to data sources using a DataBinding and custom BindingAdapters.
  - Room Persistent Library - to access app's SQLite database.
  - DataStore - used to persist sort order.
  - ViewModel - hold all the elements necessary to build the data that needs to be displayed in the UI.
  - Lifecycles - Create a UI that automatically responds to lifecycle events.
  - LiveData - Build data objects that notify views when the underlying database changes.
  - Navigation for navigation between different screens. 
  - SafeArgs for passing data between fragments.
  - Dagger-Hilt for dependency injection.
  
### Third party:
<img src="/previews/preview_1.gif" align="right" width="33%"/>
  - Kotlin Coroutines and Flow for managing background threads with simplified code and reducing needs for callbacks
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
+ Adding a workout to the list of favourite takes place by pressing the icon in the menu. The icon will changed appropriately indicating an option to remove the.
+ The buttons, menus and progress indicators are customized for colors and shapes.
+ All clickable components behave intuitively changing their appearance when they are pressed.
                                  
## Resource
Gymific uses graphic resources from [freepik](https://www.freepik.com).

## Preview
<img src="/previews/preview_3.gif" align="left" width="33%"/>
