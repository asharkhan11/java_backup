package org.ashar.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController {

	@GetMapping("/greet")
	public String greet(){

		return "Hello";
	}

	@PostMapping("/greet")
	public String greet(@Value("${app.name}") String name){ //@RequestParam("name") String name){
		return "Hello "+name;
	}

	@GetMapping("/greet/{name}/{age}")
	public String greet(@PathVariable("name") String name, @PathVariable("age") int age){
		return "Hello "+name +" your age is "+ age;
	}

	@RequestMapping(path = "/headers", method = {RequestMethod.POST,RequestMethod.GET})
	public String getAllHeaders(@RequestHeader Map<String,String> headers){
		StringBuilder h = new StringBuilder();
		for(String key : headers.keySet()){
			h.append(key).append(" : ").append(headers.get(key)).append("\n");
		}
		return h.toString();
	}

	@GetMapping("/cookie")
	public String getCookie(@CookieValue(name = "JSESSIONID", defaultValue = "unknown") String cookieId){
		return "cookie : "+ cookieId;
	}

	@GetMapping("/error")
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User generated Error")
	public String getError(){
		return "Error";
	}

	@GetMapping("/exception")
	public String throwException() throws Exception {
		throw new ArithmeticException("divided by zero");
	}

	@InitBinder
	public void initBinder(){
		System.out.println("init binder called..............");
	}


}
