package com.backbase.transformers;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.backbase.pojos.Account;
import com.backbase.pojos.BackBaseTransaction;
import com.backbase.pojos.Details;
import com.backbase.pojos.Holder;
import com.backbase.pojos.Metadata;
import com.backbase.pojos.OpenBankTransaction;
import com.backbase.pojos.OpenBankTransactions;
import com.backbase.pojos.Value;

public class TransactionsTransformerTest {
	
	@Test
	public void mapTest() {
		List<BackBaseTransaction> expectedList = new ArrayList<BackBaseTransaction>();
		expectedList.add(BackBaseTransaction.builder()
				.id("someId")
				.accountId("accountId")
				.counterpartyAccount("otherAccountNumber")
				.counterpartyName("holderName")
				.counterPartyLogoPath("urlImage")
				.instructedAmount("30")
				.instructedCurrency("USD")
				.transactionAmount(30d)
				.transactionCurrency("USD")
				.transactionType("someType")
				.description("description").build());
		
		OpenBankTransactions transactions = new OpenBankTransactions();
		List<OpenBankTransaction> transactionList = new ArrayList<OpenBankTransaction>();
		transactionList.add(OpenBankTransaction.builder()
				.id("someId")
				.thisAccount(Account.builder()
						.id("accountId").build())
				.otherAccount(Account.builder()
						.id("otherAccountId")
						.number("otherAccountNumber")
						.holder(Holder.builder()
								.name("holderName").build())
						.metadata(Metadata.builder()
								.imageUrl("urlImage").build()).build())
				.details(Details.builder()
						.value(Value.builder()
								.amount("30")
								.currency("USD").build())
						.type("someType")
						.description("description").build())
				.build());
				
		transactions.setTransactions(transactionList);
		
		List<BackBaseTransaction> transformedList = TransactionsTransformer.map(transactions);
		
		Assertions.assertNotNull(transformedList);
		Assertions.assertEquals(expectedList, transformedList);
		
		
	}

}
