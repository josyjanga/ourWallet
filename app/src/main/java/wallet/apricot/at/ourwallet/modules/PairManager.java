package wallet.apricot.at.ourwallet.modules;

import wallet.apricot.at.ourwallet.util.AppConstants;

/**
 * Created by Christian on 08.11.2015.
 */
public enum PairManager {
  INSTANCE  {

  };
  public enum PairMode{
    CREATOR,BINDER
  }

  public PairMode getMode() {
    return mMode;
  }

  public void setMode(PairMode mode) {
    mMode = mode;
  }

  public String getWalletName() {
    return mWalletName;
  }

  public void setWalletName(String walletName) {
    mWalletName = walletName;
  }

  String mWalletName;


  PairMode mMode;
}
