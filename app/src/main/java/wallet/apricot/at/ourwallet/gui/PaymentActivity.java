package wallet.apricot.at.ourwallet.gui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.interfaces.INFCCallback;
import wallet.apricot.at.ourwallet.modules.EntityManager;
import wallet.apricot.at.ourwallet.modules.PairManager;
import wallet.apricot.at.ourwallet.nfc.CardReader;
import wallet.apricot.at.ourwallet.util.Util;

public class PaymentActivity extends AppCompatActivity implements INFCCallback {
  // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
  // activity is interested in NFC-A devices (including other Android devices), and that the
  // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
  public static int READER_FLAGS =
      NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

  //@TargetApi(19)
  private CardReader mCardReader;
  private final String TAG = PaymentActivity.class.getSimpleName();

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if(Util.hasKitKat()) {
      mCardReader = new CardReader(this);
      // Disable Android Beam and register our card reader callback
      enableReaderMode();
    }else{
      Toast.makeText(getApplicationContext(), "NFC Pay not supported on this device. Requires KitKat", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  protected void onResume(){
    super.onResume();
    if(Util.hasKitKat())
      enableReaderMode();
  }

  @Override
  protected  void onPause(){
    super.onPause();
    if(Util.hasKitKat())
      disableReaderMode();
  }
  //EntityManager.INSTANCE.createAndSendSharedTransaction(mCurWallet, 12, "Shared",
  //new Date(), EntityManager.INSTANCE.getMyUser());

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        this.finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
  @TargetApi(19)
  private void enableReaderMode() {
    Log.i(TAG, "Enabling reader mode");
    NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
    if (nfc != null) {
      nfc.enableReaderMode(this, mCardReader, READER_FLAGS, null);
    }
  }

  @TargetApi(19)
  private void disableReaderMode() {
    Log.i(TAG, "Disabling reader mode");
    NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
    if (nfc != null) {
      nfc.disableReaderMode(this);
    }
  }
  public static final String TRANS_SUCCESS = "newTransaction";
  @Override
  public void onReadTag(Tag tag) {
    Log.d(TAG, "Read tag: " + tag.getId());
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        findViewById(R.id.pay_progress).setVisibility(View.GONE);
        findViewById(R.id.pay_check).setVisibility(View.VISIBLE);
        // save trans in main
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
                Intent data = new Intent();
                data.putExtra(TRANS_SUCCESS, true);
                if (getParent() == null) {
                  setResult(Activity.RESULT_OK, data);
                } else {
                  getParent().setResult(Activity.RESULT_OK, data);
                }
                finish();
              }
        }, 1500);
      }
    });


  }
}
