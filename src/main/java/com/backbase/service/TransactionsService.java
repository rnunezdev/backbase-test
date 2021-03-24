package com.backbase.service;

import java.util.List;

import com.backbase.pojos.BackBaseTransaction;

public interface TransactionsService {
	
	public List<BackBaseTransaction> getTransactionsByBankIdAndAccountId(String bankId, String accountId);
	public List<BackBaseTransaction> getTransactionsByBankIdAndAccountIdFilteredByType(String bankId, String accountId, String transactionType);
	public Double getTotalAmountByTransactionType(String bankId, String accountId, String transactionType);
	

}
