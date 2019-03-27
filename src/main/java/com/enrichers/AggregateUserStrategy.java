package com.enrichers;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.log4j.Logger;

public class AggregateUserStrategy implements AggregationStrategy{

	final static Logger log = Logger.getLogger(AggregateUserStrategy.class);
	
	@Override
	public Exchange aggregate(Exchange original, Exchange result) {
		String originalBody = original.getIn().getBody(String.class);
		log.info(originalBody);
		return result;
	}

}
