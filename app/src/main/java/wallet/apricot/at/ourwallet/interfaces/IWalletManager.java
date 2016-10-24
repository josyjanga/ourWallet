package wallet.apricot.at.ourwallet.interfaces;

import android.content.Context;

import java.util.List;

import wallet.apricot.at.ourwallet.entities.SharedAccount;

/**
 * Created by andy on 07.11.15.
 */
public interface IWalletManager {

  void addWallet(SharedAccount newWallet, Callback callback);

  void getMyWallets(Context context, Callback callback);

  void loadTransactions(SharedAccount wallet, Callback callback);
}
