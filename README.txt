Zalego(Android) & zalego_backend(php) =>>Check at https://youtu.be/Zc1ndxSeVpc
The App is designed to allow user to register and login to view the data they entered when registering
Contains:
I.	Zalego (Android App)
II.	Zalego_backend(PHP backend)
III.	Zalego_db.sql(Mariadb table export)
Setup instructions:
1)	Separate folder Zalego, folder zalego_backend and file zalego_db.sql in folder zalego_test.
2)	Import Zalego project into Android Studio.
3)	Create a hotspot WIFI network with your Android device and connect the computer (hosting the server), to the hotspot WIFI.
Alternatively, using any other existing network which both devices can connect.
4)	In BgSync.java, edit the host IP andress; 
public static final String LOGIN_URL  = "http://192.168.43.58/zalego_backend/login.php";
String reg_url = "http://192.168.43.58/zalego_backend/register.php";
String login_url = "http://192.168.43.58/zalego_backend/login.php";
to reflect your computer(host) IP address. This varies with the network the computer is connected to. 
5)	Launch XAMPP/WAMPP server running MariaDB, create database, named zalego_db and import zalego_db.sql from inside zalego_db.
6)	Go into htdocs folder in XAMPP/WAMPP server and paste zalego_backend folder there.
7)	Edit db connection statement inside zalego_backend/include/ db_connection.php to match your db credentials.
8)	Connect Android device to the computer with a USB and fire the app from Android Studio. Test the app after it Installs on the device. 
P.S: To know your host IP, execute an ipconfig command from Windows Powershell(cmd) or ifconfig from a Linux terminal.
