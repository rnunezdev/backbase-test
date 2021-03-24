package com.backbase.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backbase.pojos.BackBaseTransaction;
import com.backbase.service.TransactionsService;

@RestController
@RequestMapping(value = "/transactions", produces = "application/json;charset=UTF-8")
public class TransactionsController {
	private Logger logger = Logger.getLogger(TransactionsController.class);

	@Autowired
	private TransactionsService transactionsService;

	@GetMapping("/{bankId}/{accountId}")
	public List<BackBaseTransaction> getAllTransactionsByBankIdAndAccountId(@PathVariable String bankId,
			@PathVariable String accountId) {
		logger.debug(
				String.format("Retrieving list of all transactions for bank: %s and account: %s", bankId, accountId));
		return transactionsService.getTransactionsByBankIdAndAccountId(bankId, accountId);
	}

	@GetMapping("/{bankId}/{accountId}/filter/{transactionType}")
	public List<BackBaseTransaction> getTransactionsByBankIdAndAccountIdFilteredByTransactionType(@PathVariable String bankId,
			@PathVariable String accountId, @PathVariable String transactionType) {
		logger.debug(String.format("Retrieving list of transactions for bank: %s and account: %s. Filtered by type: %s",
				bankId, accountId, transactionType));
		return transactionsService.getTransactionsByBankIdAndAccountIdFilteredByType(bankId, accountId, transactionType);
	}
	
	@GetMapping("/{bankId}/{accountId}/filter/{transactionType}/sum")
	public BigDecimal getTotalByBankIdAndAccountIdFilteredByTransactionType(@PathVariable String bankId,
			@PathVariable String accountId, @PathVariable String transactionType) {
		logger.debug(String.format("Total amount of transactions for bank: %s and account: %s. Filtered by type: %s",
				bankId, accountId, transactionType));
		return new BigDecimal(transactionsService.getTotalAmountByTransactionType(bankId, accountId, transactionType))
				.setScale(2, RoundingMode.HALF_UP);
	}

}
