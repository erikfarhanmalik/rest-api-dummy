# Description #
A simple tool to create a mock api based on json files. The json files can become a contract between the front-end and back-end developer to standardized the messages transfered from back-end to front-end.  
  
  
  
  
## How to run ##
mvn spring-boot:run -Drun.jvmArguments="-Ddummyapi.folder=D:\\eclipse-workspace\\rms-web-application\\src\\main\\resources\\templates\\natural\\ajax-contract\\" 

note: -Ddummyapi.folder is the folder location of json files




## How to consume ##
localhost:9000/api/{file-name-of-a-json} 
ex. localhost:9000/api/employee (will read employee.json in the specified folder)




## Sample with thymeleaf ##
 ```javascript
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