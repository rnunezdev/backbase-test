# backbase-test
Repo for backase home assignment test



# Prerequisites

- JDK 8 installed
- Maven 3.6.3 or above installed
- Internet connection for downloading the dependencies


# Instructions

- Clone the git repo of the project in your local machine

2. Execute:

	$ mvn clean install 
to download all dependencies

- Execute:

	$ mvn package 
to create artifacts for embedded Tomcat execution

- Execute
	
	$ sh target/bin/webapp

to execute shell to start the web application
	
	Note: in case you have windows execute C:/> target/bin/webapp.bat

- Once you see application up and running you can execute the following command to get yourself authenticated with JWT:

	curl --location --request POST 'http://localhost:8080/authenticate' \
	--header 'Content-Type: application/json' \
	--data-raw '{
	"username":"rnunez",
	"password":"rnunezpwd"
	}'
- Then please copy the token and replace it in the following commands so you can execute the 3 endpoints for transactions:

	curl --location --request GET 'http://localhost:8080/transactions/rbs/savings-kids-john/filter/SEPA/sum' \
	--header 'Authorization: Bearer [TOKEN]'
	
	curl --location --request GET 'http://localhost:8080/transactions/rbs/savings-kids-john/filter/SEPA' \
	--header 'Authorization: Bearer [TOKEN]'
	
	curl --location --request GET 'http://localhost:8080/transactions/rbs/savings-kids-john' \
	--header 'Authorization: Bearer [TOKEN]'

- You can also try to send the requests without the token and you'll see 401 Unauthorized responses. You can import the commands to postman for a more graphic experience.


Enjoy  :) 