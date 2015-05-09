# ColorPickerView
##### A simple good looking color picker component for Android

A color picker is something that has always been missing from the standard set of components which developers can build their user interface in Android with. This is a color picker which I wrote for one of my own project which I decided to release as open source.

### Screenshots
<img src="https://cloud.githubusercontent.com/assets/5458667/7549887/8f6ec3da-f64a-11e4-9c38-cf75c890351f.png" alt="Screenshot1" width="220">
<img src="https://cloud.githubusercontent.com/assets/5458667/7549888/8f7248b6-f64a-11e4-9f55-cfec2a90409e.png" alt="Screenshot2" width="440">

### How to use
The color picker library can be used as a library project, please checkout the code and the demo app for information on how to use it.

There are basicly three different ways to use this color picker. You can add it to your preferences using the ColorPreference class. You can also use it as a DialogFragment using the ColorPickerDialogFragment. Or you can simply use the ColorPickerView to add the color picker anywhere you want in you application. All three cases are demonstrated in the demo app, please refer to the demo for information on how to use it.

### Changelog

##### Version 1.2
- Api level 13 (Android 3.2) is now required by the library.
- The ColorPickerDialog which was based on an AlertDialog has been replaced by ColorPickerDialogFragment which is based on a DialogFragment.
- New layout on the color picker dialog, should look good on all screen sizes and orientations.
- ColorPickerPreferences was replaced by ColorPreference. The ColorPreference does NOT take care of showing the ColorPickerDialogFragment, you will have to do that yourself, see the demo app. This is due to the fact that we don't have access to the fragment manager from the Preference class.
- ColorPickerView now automatically saves it state on orientation change etc.
