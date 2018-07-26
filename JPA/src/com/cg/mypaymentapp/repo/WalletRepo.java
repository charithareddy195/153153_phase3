package com.cg.mypaymentapp.repo;

import java.util.List;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;

public interface WalletRepo {

	public boolean save(Customer customer);
	public Customer findOne(String mobileNo);
	public Customer update(Customer customer,Transactions transaction);
	public List<Transactions> Transactions(String mobileNo);
	public void startTransactions();
	public void commitTransactions();
}
