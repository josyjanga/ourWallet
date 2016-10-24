package wallet.apricot.at.ourwallet.interfaces;

import android.nfc.Tag;

/**
 * Created by Christian on 08.11.2015.
 */
public interface INFCCallback {
  void onReadTag(Tag tag);
}
