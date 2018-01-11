# Description #
A simple tool to create a mock api based on json files. The json files can become a contract between the front-end and back-end developer to standardized the messages transfered from back-end to front-end.  
  
  
  
  
## How to run ##
mvn spring-boot:run -Drun.jvmArguments="-Ddummyapi.folder=D:\\eclipse-workspace\\rms-web-application\\src\\main\\resources\\templates\\natural\\ajax-contract\\" 

note: -Ddummyapi.folder is the folder location of json files




## How to consume ##
use http://localhost:9000/api/{file-name-of-a-json}   
ex. http://localhost:9000/api/employee (will read employee.json in the specified folder)




## Sample with thymeleaf ##
 ```javascript
 <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.js"></script>
 <script type="text/javascript" th:inline="javascript">
	function doSomeAjaxCall() {
		$.ajax({
			method : "GET",
			url : /*[[@{/real-api/employee/2}]]*/ "http://localhost:9000/api/employee"
		}).done(function(response) {
			$("#ajax-response").html(JSON.stringify(response));
		});
	}
</script>
```

This script will get to dummy api server when using natural templating and will get data to real api when load from template engine.

All HTTP Verb are supported (GET, POST, PUT, DELETE, PATCH, etc)


## Note ##
This tools is based on spring boot, every configuration can be configured the spring boot way.  
ex. to change port you can change `server.port=9000` in `/dummy-api/src/main/resources/application.properties`