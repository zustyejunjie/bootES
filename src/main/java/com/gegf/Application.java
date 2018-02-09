package com.gegf;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private TransportClient client;

	@GetMapping("/")
	public String index(){
		return "index";
	}

	@GetMapping("/get/book/novel")
	public ResponseEntity get(@RequestParam(name="id", defaultValue = "") String id){
		if(id == null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		GetResponse result = this.client.prepareGet("book", "novel", id).get();
		if(!result.isExists()){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(result.getSource(), HttpStatus.OK);
	}




	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
