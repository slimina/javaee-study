package org.spring.boot.demo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class User {

	private Long id;    

    private String name;
    
    @JsonSerialize(using = MyDateSerializer.class)  
    @JsonDeserialize(using = MyDateDeserializer.class) 
    private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}   
	
	
	private static class MyDateSerializer extends JsonSerializer<Date>{  
	    @Override  
	    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {  
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateStr = dateFormat.format(value);  
	        jgen.writeString(dateStr);  
	    }  
	}  
	  
	private static class MyDateDeserializer extends JsonDeserializer<Date>{  
	    @Override  
	    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {  
	        String value = jp.getValueAsString();  
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        try {  
	            return dateFormat.parse(value);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
	}  
}
