package com.router;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

import com.enrichers.AggregateUserStrategy;
import com.jackson.classes.User;

public class Builder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat jackson = new JacksonDataFormat(User.class);
		jackson.setPrettyPrint(true);
		
		from("file:files/incoming?noop=true")
		.setHeader("userId").jsonpath("$.id")
		.enrich("direct:getUser", new AggregateUserStrategy())
		.marshal(jackson)
		.to("file:files/outgoing");
		
		from("direct:getUser")
		.setHeader(Exchange.HTTP_METHOD,  constant(org.apache.camel.component.http.HttpMethods.GET))
		.toD("http://localhost:8080/user/${headers.userId}")
		.unmarshal(jackson);
	}

}
