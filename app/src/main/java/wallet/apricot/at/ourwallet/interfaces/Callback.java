package wallet.apricot.at.ourwallet.interfaces;

import java.util.List;

import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;

/**
 * Created by andy on 07.11.15.
 */
public interface Callback {

  void walletSaved(boolean success);

  void walletsLoaded(List<SharedAccount> wallets);

  void transactionsLoaded(List<SharedTransaction> wallets);
}
