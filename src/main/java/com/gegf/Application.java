package com.gegf;

import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


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

    @ResponseBody
    @DeleteMapping("/delete/people/man")
	public ResponseEntity del(@RequestParam(name="id") String id) {
		DeleteResponse response = this.client.prepareDelete("people", "man", id).get();
		return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
	}

	@ResponseBody
	@PutMapping("/update/people/man")
	public ResponseEntity update(@RequestParam(name="id") String id,
							   @RequestParam(name="name", required = false) String name,
							   @RequestParam(name="country", required = false) String country) {
		UpdateRequest updateRequest = new UpdateRequest("people", "man", id);
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
			if(name != null){
				builder.field("name", name);
			}
			if(country != null){
				builder.field("country", country);
			}
			builder.endObject();
			updateRequest.doc(builder);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			this.client.update(updateRequest).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(HttpStatus.OK);
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
