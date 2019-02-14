# FerociousKalman
The front end, back end, and the Android app that together make a Kalman demonstration. 

# Description
The demonstration makes users interact with parameters of Kalman filter and see the results in real time in a graphical environment. To see it in action please visit www.koohifar.com .

## Back End
The back end is a REST server using Nodejs and Express. It has two main APIs. Issuing a POST on /api/rotation with a JSON body that contains the rotation quaternion of the target, will update the rotation resource. Issuing a GET on /api/rotation will result in a JSON response that has the latest quaternion.

## Front End
The front end is a graphical environment using THREE.js and DOT gui. In the front end, users can choose different process noise and measurement noise sources. Furthermore, they can change the Kalman filter's knowledge of the process noise and measurement noise covariance matrix be inacurate. If the user chooses to use external process noise source, the process will be changed by calling GET on /api/rotation and they get the process noise from beck end.

## Android App
The android app uses the Android API to get the rotation quaternion of the users phone and sends POST requests to the back end to update the rotation resource. The async posts are handled by google's Voley library.
