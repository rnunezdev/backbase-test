package com.backbase.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backbase.pojos.BackBaseTransaction;
import com.backbase.service.TransactionsService;

@ExtendWith(MockitoExtension.class)
public class TransactionsControllerTest {
	@Mock
	private TransactionsService transactionsService;
	
	
	@InjectMocks
	private TransactionsController transactionsController;
	
	
	
	@Test
	public void testGetAllTransactionsByBankIdAndAccountId() {
		List<BackBaseTransaction> mockedList = new ArrayList<BackBaseTransaction>();
		mockedList.add(BackBaseTransaction.builder()
				.id("someId")
				.description("someDesc")
				.transactionType("someType").build());
		mockedList.add(BackBaseTransaction.builder()
				.id("otherId")
				.description("someDesc")
				.transactionType("someType").build());
		mockedList.add(BackBaseTransaction.builder()
				.id("anotherId")
				.description("someDesc")
				.transactionType("otherType").build());
		Mockito.when(transactionsService
					.getTransactionsByBankIdAndAccountId(Mockito.anyString(), Mockito.anyString()))
					.thenReturn(mockedList);
		
		List<BackBaseTransaction> listReturned = transactionsController.getAllTransactionsByBankIdAndAccountId("bankId", "accountId");
		
		Assertions.assertNotNull(listReturned);
		Assertions.assertEquals(mockedList, listReturned);
			
	}
	
	@Test
	public void testGetAllTransactionsByBankIdAndAccountIdFilteredByType() {
		List<BackBaseTransaction> mockedList = new ArrayList<BackBaseTransaction>();
		mockedList.add(BackBaseTransaction.builder()
				.id("someId")
				.description("someDesc")
				.transactionType("someType").build());
		mockedList.add(BackBaseTransaction.builder()
				.id("otherId")
				.description("someDesc")
				.transactionType("someType").build());
		
		Mockito.when(transactionsService
					.getTransactionsByBankIdAndAccountIdFilteredByType(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
					.thenReturn(mockedList);
		
		List<BackBaseTransaction> listReturned = transactionsController.getTransactionsByBankIdAndAccountIdFilteredByTransactionType("bankId", "accountId", "someType");
		
		Assertions.assertNotNull(listReturned);
		Assertions.assertEquals(mockedList, listReturned);
		
		
	}
	
	@Test
	public void testGetTotalAmountFromAllTransactionsByBankIdAndAccountIdFilteredByType() {
		Mockito.when(transactionsService
					.getTotalAmountByTransactionType(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
					.thenReturn(new BigDecimal("30.5").doubleValue());
		
		BigDecimal total = transactionsController.getTotalByBankIdAndAccountIdFilteredByTransactionType("bankId", "accountId", "txType");
		
		Assertions.assertNotNull(total);
		Assertions.assertEquals(total,new BigDecimal("30.50")
				.setScale(2, RoundingMode.HALF_UP));
		
		
	}

}
