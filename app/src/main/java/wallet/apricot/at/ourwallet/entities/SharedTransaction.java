package wallet.apricot.at.ourwallet.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Christian on 07.11.2015.
 */
@ParseClassName("SharedTransaction")
public class SharedTransaction extends ParseObject {
  public SharedTransaction() {
  }

  public SharedTransaction(String sharedAccountID, double amount, String title) {
    setSharedAccountID(sharedAccountID);
    setTransactionAmount(amount);
    setTitle(title);
  }

  public void setPayingMember(Member member) {
    put("payingMember", member);
  }

  public Member getPayingMember(){
    return (Member) get("payingMember");
  }

  public void addTransaction(PrivateTransaction transaction) {
    getList("privateTransactions").add(transaction);
  }

  public List<String> getPaymentTagIDs() {
    return getList("paymentTagIDs");
  }

  public void setPaymentTagIDs(List<String> paymentTagIDs) {
    put("paymentTagIDs", paymentTagIDs);
  }

  public double getTransactionAmount() {
    return getDouble("transactionAmount");
  }

  public void setTransactionAmount(double transactionAmount) {
    put("transactionAmount", transactionAmount);
  }

  public List<PrivateTransaction> getPrivateTransactions() {
    return getList("privateTransactions");
  }

  public void setPrivateTransactions(List<PrivateTransaction> privateTransaction) {
    put("privateTransactions", privateTransaction);
  }


  public String getSharedAccountID() {
    return getString("sharedAccountID");
  }

  public void setSharedAccountID(String sharedAccountID) {
    put("sharedAccountID", sharedAccountID);
  }

  public Date getTransactionDate() {
    return getDate("transactionDate");
  }

  public void setTransactionDate(Date date) {
    put("transactionDate", date);
  }

  public void setTitle(String title) {
    put("title", title);
  }

  public String getTitle() {
    return getString("title");
  }

}
