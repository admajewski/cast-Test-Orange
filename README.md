# cast-Test-Orange
Test which is searching for cast member list in given mowie using __Accessibility ID__ of categori and __name__ of the movie.

## Requirements
To run the test you need:
- [Node.js](https://nodejs.org/en/download/) 
- [Appium server](https://appium.io/downloads.html) (You can also use NPM),
- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html),
- [Android SDK](https://developer.android.com/studio/releases/platform-tools),
- Android device emulator or physical Android device.

## How to build test application
Enter the project directory and write below command in CLI:
```
gradlew build
```
In directory build/libs you will see __cast_test-1.0-all.jar__ test app i and config file named __config.properties__

## How to run a test
1. Run Appium Server.
2. Run Android emulator or connect Android phone with USB debuggin toggled on.
3. See and change configuration in __config.properties__ file.
4. In CLI type:
```
java -jar cast_test-1.0-all.jar
```

## Test Configuration
In __config.properties__ you can change below parameters:
- __CATEGORY__ - It's Accessibility ID of category in which the movie is located.
- __MOVIE__ - Name of the movie you look for.
- __URL__ - URL which Appium server is listening on.
- __PLATFORM_VERSION__ - Android version (as number)
- __DEVICE_NAME__ - Name of the device. Not use if __UDID__ parameter is used.
- __UDID__ - ID of device from _adb devices_ command
- __APPPACKAGE__ - Name of the app's package.
- __APPACTIVITY__ - Name of first run activity.
- __APPWAITACTIVITY__ - Name of activity for the test to wait for before doing any of script work.
- __APPFILEPATH__ - Absolute filepath to the app's apk. When __FULLRESET__ is set to _true_ test will install app before test and uninstall it after.
- __FULLRESET__ - When set to true will install the app before test and uninstall it after. Need __APPFILEPATH__ for installing purposes.

