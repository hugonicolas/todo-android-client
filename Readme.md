## Todo App Client

Android client for the todo app.
Before building the app :

 1. Edit "app/src/main/res/xml/network_security_config.xml and replace 192.168.1.12 with the correct IP address of the backend. This is necessary since we will be serving content over HTTP and not HTTPS.
 2. Edit "app/src/main/java/network/RetroFitClientInstance.java" and replace http://192.168.1.12:3000/ with the correct IP adress and  port of the backend.
 3. Compile and launch using android studio.


