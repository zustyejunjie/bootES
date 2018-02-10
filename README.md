# SpringbootDemo
Springboot学习的Demo，以及整合ElasticSearch的例子。

#### 1. 创建索引

- PUT 127.0.0.1:9200/people 

- body:
	 
	
	    {
	  	  "settings":{
     	    "number_of_shards": 3,
     	    "number_of_replicas": 1
     	},
     	"mappings": {
     	    "man":{
     	        "properties":{
     	            "name":{
     	                "type": "text"
     	            },
     	            "country":{
     	                "type": "keyword"
     	            },
     	            "age": {
     	                "type": "integer"
     	            },
     	            "date": {
     	                    "type": "date",
     	                    "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd"
     	            }
     	        }
     	    }
     	}
 	 }
 
- 2.插入数据（指定ID）
 	
   PUT 127.0.0.1:9200/people/man/1
  

	 {
     	 "name": "小兰",
      	"country": "China",
     	"age": 30,
     	"date": "1988-03-11"
 	}
 
  插入数据（ES生成ID）
 
 POST 127.0.0.1:9200/people/man
	
	  {
       "name": "小明",
  	     "country": "China",
       "age": 30,
       "date": "1988-03-11"
     }
