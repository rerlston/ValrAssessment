Requirements:
* Application should run on any JavaEE/Web container, tested on Apache Tomcat 9.0.14
* Apache Maven
* git
* access to the Internet for maven packages

git clone 
cd ValrAssessment
mvn clean package
copy target/ValrAssessment.war to the JavaEE/Web container
	e.g. cp target/ValrAssessment.war ~/tomcat/webapps
Start JavaEE/Web container
	e.g. ~/tomcat/bin/catalina.sh start
Launch browser
set browser URL to http://localhost:8080/ValrAssessment/

Assumptions made:
Buying: is always ZAR to the second selected currency no matter what the first currency is selected as
Selling: is between the 2 selected currencies