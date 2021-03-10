# Gymific
<img src="/previews/preview_2.gif" align="right" width="33%"/>

Gymific is a home workout app illustrating Android development best practices: Hilt, Preferences DataStore, WorkManager, DataBinding, Kotlin Coroutines & Flow.

## Overview
  + Gymific has 3 main destinations that can be accessible from anywhere in the app by the ```Bottom Navigation``` set with Navigation Components.
  + The Home and Workout screens start with lists of pre-populated data. Loading raw data into Room database is scheduled with WorkManager's ```OneTimeWorkRequest```.
  + The Workout screen consists of ```ViewPager2``` with Tabs that organize workouts across 3 different screens basing on workout's categories.
  + The Favourite screen displays list of user's selections. The list of favourite has an option to sort data. UserPreferenceRepository class holds the sort order, defined as an enum. The current sort order is saved in ```Jetpack DataStore```.
  + The Detail screen demonstrates custom ProgressBar that displays running time in the center of the "moving" ring. The timer has an option to pause the time, which is then stored in ```Jetpack DataStore```.
  + WorkoutRepository is responsible for providing the workouts and exposes them via ```Flow```, which are converted to ```LiveData``` in the ViewModels and the UiModel objects wrap the objects that needs to be displayed in the UI.
  + Data Binding Library is used to display recurrent lists and workout details, handle clickListeners and manage buttons behaviours. Where data binding was not an intuitive option, the UI components were bind programmatically.

## Tech stack & Open-source libraries
### Android Architecture Components & good practices: </b>
  - DataBinding - the app binds the UI components in the XML layout to data sources using a DataBinding and custom BindingAdapters.
  - Room Persistent Library - to access app's SQLite database.
  - DataStore - used to persist sort order and paused the workout time.
  - ViewModel - hold all the elements necessary to build the data that needs to be displayed in the UI.
  - Lifecycles - create a UI that automatically responds to lifecycle events.
  - WorkManager - to pre-populate Room database with raw data.
  - LiveData - build data objects that notify views when the underlying database changes.
  - Navigation for navigation between different screens. 
  - SafeArgs for passing data between fragments.
  - Dagger-Hilt for dependency injection.
  
### Third party:
<img src="/previews/preview_1.gif" align="right" width="33%"/>

  + Kotlin Coroutines and Flow for managing background threads with simplified code and reducing needs for callbacks
  + Timber for logs.
  
### Architecture:
  - MVVM Architecture 
  - Repository pattern
  
## Testing 
###  Device Tests:
  - <b>App Navigation Test</b> - Navigation between screens is tested using Espresso UI framework and ```ActivityScenario``` for lifecycle state. Hilt provides test version of Repository and automatically generates a new set of components for each test. This is done with use of a ```CustomTestRunner``` that uses an Application configured with ```Hilt```. In order to make Espresso aware of data binding ```DataBindingIdlingResource``` is registered for UI test.
  - <b>Database Testing</b> - Database is tested with small instrumented unit tests. The project creates an in memory database for each database test but still runs them on the device.
  - <b>WorkManager Testing</b> - The app tests CoroutineWorkers with ```TestListenableWorkerBuilder```. WorkerFactory is injected with use of ```HiltAndroidRule```.

###  Local Tests:
  - <b>ViewModel Tests</b> - ViewModels are tested using local unit tests with implementation of Repository mocked using ```Mockito```.
  - <b>Repository Tests</b> - Repository is tested using local unit tests with mock versions of dao.

## Design
+ Gymific is built with Material Components for Android.
+ The app has a Bottom Navigation, which provides access to main destinations.
+ The home screen displays horizontally and vertically oriented lists. 
+ The Workout screen has swipe views with tabs (using ViewPager2) and top app bars, that hides on scroll.
+ The detail screen demonstrates custom ProgressBar with gradient ring.
+ The sort menu is built with checkable menu items and the only one item from the group can be checked with use of radio buttons.
+ Adding a workout to the list of favourite takes place by pressing the icon in the menu. The icon will changed appropriately indicating an option to remove the.
+ The buttons, menus and progress indicators are customized for colors and shapes.
+ All clickable components behave intuitively changing their appearance when they are pressed.
                                  
## Resource
Gymific uses graphic resources from [freepik](https://www.freepik.com).

## Preview
<img src="/previews/preview_3.gif" width="33%"/>
<br/><img src="/previews/preview_4.gif" width="33%"/>
<br/><img src="/previews/screen_1.png" width="33%"/>  <img src="/previews/screen_2.png" width="33%"/> <img src="/previews/screen_3.png" width="33%"/>

## License

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

```
Copyright 2020 Ersiver

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
