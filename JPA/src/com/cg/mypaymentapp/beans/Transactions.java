package com.cg.mypaymentapp.beans;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Transactions1")
public class Transactions {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int Id;
	String phoneNumber;
	String transactionType;
	BigDecimal amount;
	String transactionStatus;
	String transactionDate;


	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Transactions() {
		super();
	}


	public Transactions(String phoneNumber, String transactionType, BigDecimal amount, String transactionStatus,
			String transactionDate) {
		super();
		this.phoneNumber = phoneNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.transactionStatus = transactionStatus;
		this.transactionDate = transactionDate;
	}
	@Override
	public String toString() {
		return "Transactions [Id=" + Id + ", phoneNumber=" + phoneNumber + ", transactionType=" + transactionType
				+ ", amount=" + amount + ", transactionStatus=" + transactionStatus + ", transactionDate="
				+ transactionDate + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transactions other = (Transactions) obj;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

}
