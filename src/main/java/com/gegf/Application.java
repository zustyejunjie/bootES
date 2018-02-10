package com.gegf;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;


@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private TransportClient client;

	@GetMapping("/")
	public String index(){
		return "index";
	}

	@ResponseBody
	@GetMapping("/get/people/man")
	public ResponseEntity get(@RequestParam(name="id", defaultValue = "") String id){
		if(id == null){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		GetResponse result = this.client.prepareGet("people", "man", id).get();
		if(!result.isExists()){
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(result.getSource(), HttpStatus.OK);
	}

	@ResponseBody
    @PostMapping("/add/people/man")
    public ResponseEntity add(@RequestParam(name="name") String name,
                              @RequestParam(name="country") String country,
                              @RequestParam(name="age") int age,
                              @RequestParam(name="date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date){
        try {
           XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject()
                    .field("name", name)
                    .field("country", country)
                    .field("age", age)
                    .field("date", date).endObject();
            IndexResponse response = this.client.prepareIndex("people", "man").setSource(contentBuilder).get();
            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
