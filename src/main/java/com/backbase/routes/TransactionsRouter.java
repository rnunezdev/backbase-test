package com.backbase.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.log4j.Logger;

import com.backbase.pojos.OpenBankTransactions;
import com.backbase.transformers.TransactionsTransformer;

public class TransactionsRouter extends RouteBuilder{
	
	private static Logger logger = Logger.getLogger(TransactionsRouter.class);

	@Override
	public void configure() throws Exception {
		logger.debug("Executing dynamic route TransactionsRouter");
		from("direct:transactions")
		.process(exchange -> exchange.getIn().setBody(exchange.getIn().getBody()))
		.setHeader(Exchange.HTTP_METHOD, simple("GET"))
		.toD("https://apisandbox.openbankproject.com/obp/v1.2.1/banks/${body.bankId}/accounts/${body.accountId}/public/transactions")
		.unmarshal(new JacksonDataFormat(OpenBankTransactions.class))
		.bean(new TransactionsTransformer());
	}

}
