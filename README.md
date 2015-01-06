adbutil
=======

adb util on Android device (logcat dump etc.)

Using adb TCP mode, adb connection to an Android device via Wi-Fi from within its own Android device . As a result you will be able to or acquires a logcat of its own device .

1)connect the ADB client and Android device via USB cable 

2)run "adb tcpip 5555" in ADB client. 

　Once the USB debugging confirmation dialog appears , and then approved by pressing the OK button .
　
3)unplug the USB cable

4)start the ADBUtil app

5)tap "restart" button.

 Once the USB debugging confirmation dialog appears , and then approved by pressing the OK button .
 
6)tap "adb devices" button to show connected devices.

7)tap "adb logcat -d" button to show logcat logs.

8)In order to finally return to the adb USB mode , that you do not forget to press the "adb usb" button


--
Android Studio 1.0.2
