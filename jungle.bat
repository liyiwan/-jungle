call mvn clean
call mvn package
call net stop jetty8
call del D:\Jetty8\webapps\root.war
call copy E:\github\jungle\target\jungle.war D:\Jetty8\webapps\root.war
call net start jetty8
pause
