package wallet.apricot.at.ourwallet.util;

import android.content.Context;
import android.graphics.Color;

import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.entities.SharedAccount;

/**
 * Created by andy on 07.11.15.
 */
public class LayoutUtil {
  private LayoutUtil() {
  }

  public static ArrayList<IProfile> convertToDrawerItems(Context context, List<SharedAccount> wallets) {
    ArrayList<IProfile> profiles = new ArrayList<>();
    int index = 0;
    for (SharedAccount cur : wallets) {
      int walletColor = getWalletColor(context, index);
      index++;
      profiles.add(cur.asDrawerItem(walletColor));
    }
    return profiles;
  }

  private static int getWalletColor(Context context, int index) {
    switch (index) {
      case 0:
        return context.getResources().getColor(R.color.colorWallet1);
      case 1:
        return context.getResources().getColor(R.color.colorWallet2);
      case 2:
        return context.getResources().getColor(R.color.colorWallet3);
      case 3:
        return context.getResources().getColor(R.color.colorWallet4);
      case 4:
        return context.getResources().getColor(R.color.colorWallet5);
      case 5:
        return context.getResources().getColor(R.color.colorWallet6);
      case 6:
        return context.getResources().getColor(R.color.colorWallet7);
    }
    return context.getResources().getColor(R.color.colorWallet1);
  }

}
