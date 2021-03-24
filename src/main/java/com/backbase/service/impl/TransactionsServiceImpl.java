package com.backbase.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backbase.pojos.BackBaseTransaction;
import com.backbase.pojos.TransationBody;
import com.backbase.service.TransactionsService;
import com.backbase.transformers.TransactionsTransformer;

@Service
public class TransactionsServiceImpl implements TransactionsService {
	
	private static Logger logger = Logger.getLogger(TransactionsServiceImpl.class);
	
	@Autowired
	ProducerTemplate producerTemplate;

	@Override
	public List<BackBaseTransaction> getTransactionsByBankIdAndAccountId(String bankId, String accountId) {
		List<BackBaseTransaction> bbTransactionList = getTransactionListByBankIdAndAccountId(bankId, accountId);
		logger.debug(String.format("List of transactions after data mapping: %s", bbTransactionList.toString()));
		return bbTransactionList;
	}

	@Override
	public List<BackBaseTransaction> getTransactionsByBankIdAndAccountIdFilteredByType(String bankId, String accountId,
			String transactionType) {
		List<BackBaseTransaction> bbTransactionList = getTransactionListByBankIdAndAccountId(bankId, accountId);
		logger.debug(String.format("List of transactions after data mapping: %s", bbTransactionList.toString()));
		return bbTransactionList
				.stream()
				.filter(transaction -> transaction.getTransactionType().equals(transactionType))
				.collect(Collectors.toList());
	}

	@Override
	public Double getTotalAmountByTransactionType(String bankId, String accountId, String transactionType) {
		List<BackBaseTransaction> bbTransactionList = getTransactionListByBankIdAndAccountId(bankId, accountId);
		logger.debug(String.format("List of transactions after data mapping: %s", bbTransactionList.toString()));
		return bbTransactionList
				.stream()
				.filter(transaction -> transaction.getTransactionType().equals(transactionType))
				.collect(Collectors.summingDouble(BackBaseTransaction::getTransactionAmount));
	}
	
	private List<BackBaseTransaction> getTransactionListByBankIdAndAccountId(String bankId, String accountId) {
		return producerTemplate.requestBody("direct:transactions", 
				TransationBody.builder().accountId(accountId).bankId(bankId).build(), List.class);
	}

}
