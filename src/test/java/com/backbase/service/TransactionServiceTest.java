package com.backbase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backbase.pojos.BackBaseTransaction;
import com.backbase.pojos.TransationBody;
import com.backbase.service.impl.TransactionsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	
	@Mock
	ProducerTemplate producerTemplate;
	
	@InjectMocks
	TransactionsServiceImpl transactionsService;
	
	List<BackBaseTransaction> mockedList;
	
	@BeforeEach
	void setUpRequestBodyCall() {
		mockedList = new ArrayList<BackBaseTransaction>();
		mockedList.add(BackBaseTransaction.builder()
				.id("someId")
				.description("someDesc")
				.transactionType("someType")
				.transactionAmount(20.2).build());
		mockedList.add(BackBaseTransaction.builder()
				.id("otherId")
				.description("someDesc")
				.transactionType("someType")
				.transactionAmount(30.2).build());
		mockedList.add(BackBaseTransaction.builder()
				.id("anotherId")
				.description("someDesc")
				.transactionType("otherType")
				.transactionAmount(50.1).build());
		
		Mockito.when(producerTemplate.requestBody(Mockito.anyString(), 
				Mockito.any(TransationBody.class), Mockito.any()))
					.thenReturn(mockedList);
		
	}
	
	
	@Test
	public void getTransactionsByBankIdAndAccountIdTest() {
		
		
		List<BackBaseTransaction> listReturned = transactionsService.getTransactionsByBankIdAndAccountId("bankId", "accountId");
		
		Assertions.assertNotNull(listReturned);
		Assertions.assertEquals(mockedList, listReturned);
	}
	
	@Test
	public void getTransactionsByBankIdAndAccountIdFilteredByType() {
		List<BackBaseTransaction> filteredMockedList = mockedList
				.stream()
				.filter(transaction -> transaction.getTransactionType().equals("otherType"))
				.collect(Collectors.toList());
		
		List<BackBaseTransaction> listReturned = transactionsService.getTransactionsByBankIdAndAccountIdFilteredByType("bankId", "accountId", "otherType");
		
		Assertions.assertNotNull(listReturned);
		Assertions.assertEquals(filteredMockedList, listReturned);
	}
	
	@Test
	public void getTotalTransactionsByBankIdAndAccountIdFilteredByType() {
		double mockedTotal = mockedList
				.stream()
				.filter(transaction -> transaction.getTransactionType().equals("someType"))
				.collect(Collectors.summingDouble(BackBaseTransaction::getTransactionAmount));
		
		double total = transactionsService.getTotalAmountByTransactionType("bankId", "accountId", "someType");
		
		Assertions.assertNotNull(total);
		Assertions.assertEquals(mockedTotal, total);
	}
	
	

}
