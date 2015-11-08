# Noble

## Description

Noble is a Wifi based robot control system initially developed by FRC team 5806 for the Barnes & Noble mini-makerfaire outreach event.  Robots have Redboards and ESP8266s for communication with a driver station over sockets.  The entire system is centralized through a wifi router, which both the driver station and ESP8266s connect to.

## Proposed functionality

The driver station should be able to take in take in multiple Logitech F310 gamepads over USB and send motor commands to multiple robots through sockets.  A  visualization of joystick inputs would be nice, as well as an emergency stop function to disable all robots (set motors to 0).

An additional safety function could be consistently sending motor values with an additional "key value" computed from the current timestamp.  If no new commands are detected robot-side for a period of time or key values are found to be invalid, robots should emergency stop.

## Current Functionality

Currently the system can functionally control any number of robots using arcade drive, however reliability is iffy among other issues.  The system was tested semi-successfully at the Bay Plaza B&N mini-makerfaire.

## To-do
- Code cleanup and style guide compliance
- Reliability upgrades
- GUI enhancements (see number of robots connected, connection status, names, etc)
- Tank drive
