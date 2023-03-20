# HTTP connections for API Call....

	Function parameter:- 
		
		 url  // api endpoint
		 payload  // request body to pass
		method  // HTTP method (like POST, GET, PUT, ....)
		contentType // format in which request passed (like application/JSON, application/xml, .......)
		flagForPayload // true for payload(POST) and false for without payload....
		
		ex:- 
		
		APICall("https://.......", payload, "POST", "application/json", true)
