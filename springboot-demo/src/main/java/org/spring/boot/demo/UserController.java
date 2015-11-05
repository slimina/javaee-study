package org.spring.boot.demo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/{id}")
	public User view(@PathVariable("id") Long id) {

		User user = new User();

		user.setId(id);

		user.setName("zhang");
		user.setDate(new Date());
		return user;
	}
}
