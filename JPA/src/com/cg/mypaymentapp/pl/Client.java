package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	private WalletService service;
	public Client(){
		service=new WalletServiceImpl();
	}
	List<Transactions> trans;
	public void menu() {
		System.out.println("1.Create Account");
		System.out.println("2.Show Balance");
		System.out.println("3.Transfer funds");
		System.out.println("4.Deposit Amount");
		System.out.println("5.Withdraw Amount");
		System.out.println("6.Print Transactions");
		Scanner console=new Scanner(System.in);
		System.out.println("Please Select an Option");
		int choice=console.nextInt();
		switch (choice) {
		case 1:
			Customer c=new Customer();
			System.out.println("1.Enter name:");
			String name=console.next();
			System.out.println("2.Enter Mobile number:");
			String phone=console.next();
			System.out.println("3.Enter Amount:");
			BigDecimal amount=console.nextBigDecimal();
			service.createAccount(name, phone, amount);
			System.out.println("Account created succesfully");
			break;
		case 2:
			System.out.println("Enter Mobile number to check balance:");

			String mobile=console.next();
			try {
				Customer c1=service.showBalance(mobile);
				System.out.println("Name of the customer:"+c1.getName());
				System.out.println("Balance:"+c1.getWallet().getBalance());
			}
			catch(InvalidInputException e){
				e.printStackTrace();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter Mobile number to transfer funds:");
			String sm=console.next();
			System.out.println("Enter Mobile number to receive funds:");
			String tm=console.next();
			System.out.println("Enter Amount to transfer funds:");
			BigDecimal amount1=console.nextBigDecimal();
			c=service.fundTransfer(sm, tm, amount1);
			System.out.println("Funds Transferred succesfully");
			break;
		case 4:
			System.out.println("Enter  mobile number:");
			String mobile1=console.next();
			System.out.println("Enter Amount to deposit:");
			BigDecimal amount2=console.nextBigDecimal();
			c=service.depositAmount(mobile1, amount2);
			System.out.println("Deposit completed");
			break;
		case 5:
			System.out.println("Enter  mobile number:");
			String mobile2=console.next();
			System.out.println("Enter Amount to withdraw:");
			BigDecimal amount3=console.nextBigDecimal();
			c=service.withdrawAmount(mobile2, amount3);
			System.out.println("withdraw completed");
			break;	
		case 6:
			System.out.println("Enter mobile number:");
			String mobileNo=console.next();
			trans=service.Transactions(mobileNo);
			System.out.println(trans);
			trans.clear();
			break;
		default:
			System.out.println("invalid option");
		}
	}
	public static void main( String[] args ){
		Client client=new Client();
		while(true) {
			client.menu();
		}
	}
}
