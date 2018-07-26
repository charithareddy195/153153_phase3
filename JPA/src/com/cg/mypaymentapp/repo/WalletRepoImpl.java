package com.cg.mypaymentapp.repo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.util.JPAUtil;

public class WalletRepoImpl implements WalletRepo {	
	private EntityManager entityManager;
	public WalletRepoImpl()
	{

		entityManager=JPAUtil.getEntityManager();
	}
	@Override
	public boolean save(Customer customer) {

		entityManager.persist(customer);			
		return true;
	}

	@Override
	public Customer findOne(String mobileNo) {

		Customer cust=entityManager.find(Customer.class, mobileNo);
		return cust;
	}
	@Override
	public Customer update(Customer customer,Transactions transaction) {

		entityManager.merge(customer);
		//		String str="insert into Transactions1 values:=pTrans";
		//		TypedQuery<Transactions> query=entityManager.createQuery(str,Transactions.class);
		//		query.setParameter("pTans", transaction);
		entityManager.persist(transaction);
		return customer;
	}
	@Override
	public List<Transactions> Transactions(String mobileNo) {

		String str="select transactions from Transactions transactions where transactions.phoneNumber=:pTrans";
		TypedQuery<Transactions> query=entityManager.createQuery(str,Transactions.class);
		query.setParameter("pTrans", mobileNo);
		return query.getResultList();
	}
	@Override
	public void startTransactions() {
		entityManager.getTransaction().begin();

	}
	@Override
	public void commitTransactions() {
		entityManager.getTransaction().commit();

	}

}
