package wallet.apricot.at.ourwallet.interfaces;

import java.util.List;

import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;

/**
 * Created by andy on 07.11.15.
 */
public class CallbackAdapter implements Callback {

  @Override
  public void walletSaved(boolean success) {

  }

  @Override
  public void walletsLoaded(List<SharedAccount> wallets) {
  }

  @Override
  public void transactionsLoaded(List<SharedTransaction> wallets) {
  }
}
