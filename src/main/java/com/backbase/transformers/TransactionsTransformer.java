package com.backbase.transformers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.backbase.pojos.BackBaseTransaction;
import com.backbase.pojos.OpenBankTransactions;

public class TransactionsTransformer {
	
	private static Logger logger = Logger.getLogger(TransactionsTransformer.class);

	public static List<BackBaseTransaction> map(OpenBankTransactions transactions) {
		logger.debug(String.format("Transforming data: %s", transactions.toString()));
		
		return transactions.getTransactions().stream().map(transaction -> {
			BackBaseTransaction tx = new BackBaseTransaction();
			tx.setId(transaction.getId());
			tx.setAccountId(transaction.getThisAccount().getId());
			tx.setCounterpartyAccount(transaction.getOtherAccount().getNumber());
			tx.setCounterpartyName(transaction.getOtherAccount().getHolder().getName());
			tx.setCounterPartyLogoPath(transaction.getOtherAccount().getMetadata().getImageUrl());
			tx.setInstructedAmount(transaction.getDetails().getValue().getAmount());
			tx.setInstructedCurrency(transaction.getDetails().getValue().getCurrency());
			tx.setTransactionAmount(Double.valueOf(transaction.getDetails().getValue().getAmount()));
			tx.setTransactionCurrency(transaction.getDetails().getValue().getCurrency());
			tx.setTransactionType(transaction.getDetails().getType());
			tx.setDescription(transaction.getDetails().getDescription());
			return tx;
			
		}).collect(Collectors.toList());
	}

}
