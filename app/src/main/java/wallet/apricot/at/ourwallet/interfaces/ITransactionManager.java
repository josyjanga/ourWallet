package wallet.apricot.at.ourwallet.interfaces;

import java.util.Date;
import java.util.List;

import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;

/**
 * Created by andy on 07.11.15.
 */
public interface ITransactionManager {


  void createAndSendSharedTransaction(SharedAccount wallet, double amount,
                                      String title, Date transactionDate, Member payingMember);
}
