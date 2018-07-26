package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{
	private WalletRepo repo=new WalletRepoImpl();
	//WalletRepo repo1=new WalletRepoImpl();
	Customer customer;
	Wallet wallet;
	public WalletServiceImpl(WalletRepo repo) {
		this.repo=repo;
	}

	public WalletServiceImpl() {
		repo=new WalletRepoImpl();
	}
	public Customer createAccount(String name, String mobileNo, BigDecimal amount){

		if(isNameValid(name)&&isPhoneNumbervalid(mobileNo)&&isAmountValid(amount)){
			customer = new Customer();
			wallet = new Wallet();
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			wallet.setBalance(amount);
			customer.setWallet(wallet);
			repo.startTransactions();
			boolean b=repo.save(customer);
			repo.commitTransactions();
			if(b){
				repo.startTransactions();
				repo.update(customer, new Transactions(customer.getMobileNo(), "CREATE ACCOUNT", customer.getWallet().getBalance(), "Success", new Date().toString()));
				repo.commitTransactions();
				return customer;
			}
		}
		throw new InvalidInputException("Account is already existing");

	}

	public Customer showBalance(String mobileNo) {
		repo.startTransactions();
		Customer customer=repo.findOne(mobileNo);
		repo.commitTransactions();
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");	
	}
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {

		Customer customer=null;
		Customer customer1=null;
		if(sourceMobileNo.equals(targetMobileNo)){
			throw new InvalidInputException("Source and target mobile numbers are equal");
		}
		if(isPhoneNumbervalid(sourceMobileNo)&&isPhoneNumbervalid(targetMobileNo)){
			customer=repo.findOne(sourceMobileNo);
			customer1=repo.findOne(targetMobileNo);
		}
		else{
			throw new InvalidInputException("Invalid mobile number");
		}
		BigDecimal balance1;
		BigDecimal balance2;

		balance1=customer.getWallet().getBalance();
		balance2=customer1.getWallet().getBalance();
		if(isAmountValid(amount))
			if(( balance1).compareTo(amount)>=0) {
				balance1=balance1.subtract(amount);
				repo.startTransactions();
				repo.update(customer, new Transactions(customer.getMobileNo(), "FUND TRANSFER", customer.getWallet().getBalance(), "Success", new Date().toString()));
				repo.commitTransactions();
				customer.setWallet(new Wallet(balance1));

				balance2=balance2.add(amount);	
				customer1.setWallet(new Wallet(balance2));
				repo.startTransactions();
				repo.update(customer, new Transactions(customer.getMobileNo(), "FUND TRANSFER", customer.getWallet().getBalance(), "Success", new Date().toString()));
				repo.commitTransactions();
				return customer ;
			}
			else
				throw new  InsufficientBalanceException("Insufficient balance");
		else throw new InvalidInputException("Invalid mobile number");
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		Customer customer=null;
		if(isPhoneNumbervalid(mobileNo)) {
			customer=repo.findOne(mobileNo);
			BigDecimal dbalance;
			dbalance=customer.getWallet().getBalance();
			if(isAmountValid(amount))
				dbalance=dbalance.add(amount);
			customer.setWallet(new Wallet(dbalance));
			repo.startTransactions();
			repo.update(customer, new Transactions(customer.getMobileNo(), "DEPOSITED", customer.getWallet().getBalance(), "Success", new Date().toString()));
			repo.commitTransactions();
			return customer ;
		}
		else throw new InvalidInputException("Invalid mobile number");

	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer customer=null;
		if(isPhoneNumbervalid(mobileNo)) {
			customer=repo.findOne(mobileNo);
			BigDecimal wbalance;
			wbalance=customer.getWallet().getBalance();
			if(isAmountValid(amount))
				wbalance=wbalance.subtract(amount);
			customer.setWallet(new Wallet(wbalance));
			repo.startTransactions();
			repo.update(customer, new Transactions(customer.getMobileNo(), "WITHDRAWN", customer.getWallet().getBalance(), "Success", new Date().toString()));
			repo.commitTransactions();
			return customer;
		}
		else throw new InvalidInputException("Invalid mobile number");
	}

	public List<Transactions> Transactions(String mobileNo) {
		repo.startTransactions();
		List<Transactions> trans=repo.Transactions(mobileNo);
		repo.commitTransactions();
		if(trans!=null) {
			return trans;
		}
		else throw new InvalidInputException("No transactions to display"); 	
	}
	public boolean isPhoneNumbervalid( String phoneNumber ){
		if(phoneNumber.matches("[1-9][0-9]{9}")) {
			return true;
		}		
		return false;
	}
	public boolean isNameValid(String name){
		if(name.matches("^[a-zA-Z]{2,14}$") || name.equals(null)){
			return true;
		}
		return false;
	}
	public boolean isAmountValid(BigDecimal amount){
		int val=amount.compareTo(new BigDecimal("0"));
		if(val==0|| val<0)
			return false;
		return true;
	}
}