# gliderun-server

Private server for Gliderun Android build 310+

https://play.google.com/store/apps/details?id=com.tincore.and.gliderun

Stores and synchronizes all metrics from and to Gliderun.
You can host the server at home or private network.

# Installation

You will need Java 8 to execute the packages

Temporarily you can find the two built packages on /build

Authorization server:
tincore-gsp-authorization-<VERSION>.jar

Resource server:
tincore-gsp-resource-<VERSION>.jar

Execute both with java -jar <packagename.jar>

When they start succesfully you can access the authorisation server on 
http://localhost:7679/uaa/login

You can access as user 'admin' and password 'admin'

# Usage
Run Gliderun 
https://play.google.com/store/apps/details?id=com.tincore.and.gliderun

Synchronize your data from:
 Synchronize menu option > Gliderun SRV
 
Click preferences in that screen menu to enable autodiscover server and upload data to server.

Send me an email if you need more details.






