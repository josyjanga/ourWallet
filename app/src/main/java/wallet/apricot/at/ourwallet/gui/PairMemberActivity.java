package wallet.apricot.at.ourwallet.gui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.modules.PairManager;
import wallet.apricot.at.ourwallet.util.Util;

public class PairMemberActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback,
    NfcAdapter.OnNdefPushCompleteCallback {
  private final String TAG = PairMemberActivity.class.getSimpleName();
  private enum Mode{
    CREATOR,BINDER
  }
  //private Mode mPairMode;
  private NfcAdapter mNfcAdapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pair);
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    findViewById(R.id.pair_progress).setVisibility(View.VISIBLE);

    initNFC();
    setUI();

  }
  private void setUI(){
    switch (PairManager.INSTANCE.getMode()){
      case BINDER:
        ((TextView)findViewById(R.id.hint)).setText("Join to new Wallet");
        break;
      case CREATOR:
        ((TextView)findViewById(R.id.hint)).setText("Add new member");
        break;
    }
  }
  /*private void setMode(){
    String mode=getIntent().getExtras().getString(AppConstants.PAIR_MODE);
    if(mode!=null){
      if(mode.equals(AppConstants.CREATOR)){
        mPairMode = Mode.CREATOR;
      }else{
        mPairMode = Mode.BINDER;
      }
    }else{
      mPairMode=Mode.BINDER;
    }
  }*/

  private void initNFC(){
    // TODO: The NFC stuff
    mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    if (mNfcAdapter == null) {
      // If the device does not support NFC, stop the app (we definitely need NFC)
      Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
      finish();
      return;
    }
    if (!mNfcAdapter.isEnabled()) {
      //mTextView.setText("NFC is disabled.");
      Toast.makeText(this, "NFC is disabled.",Toast.LENGTH_LONG).show();
    } else {
      //mTextView.setText(R.string.explanation);
      // Register callback to set NDEF message
      mNfcAdapter.setNdefPushMessageCallback(this, this);
      // Register callback to listen for message-sent success
      mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
    }
  }

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

  @Override
  public void onNewIntent(Intent intent) {
    // onResume gets called after this to handle the intent
    setIntent(intent);
  }

  /**
   * Implementation for the CreateNdefMessageCallback interface
   */
  @Override
  public NdefMessage createNdefMessage(NfcEvent event) {
    NdefMessage msg = new NdefMessage(NdefRecord.createMime("application/at.apricot.wallet.beam",  Util.getCurrentUserID(this).getBytes()));//, null));

    return msg;

  }

  /**
   * Implementation for the OnNdefPushCompleteCallback interface
   */
  @Override
  public void onNdefPushComplete(NfcEvent arg0) {
    // A handler is needed to send messages to the activity when this
    // callback occurs, because it happens from a binder thread
    mHandler.obtainMessage(1).sendToTarget();
  }

  /** This handler receives a message from onNdefPushComplete */
  private final Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if(msg!=null) {
        switch (msg.what) {
          case 1:
            Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
            finishPairing("");
            break;
        }
      }
    }
  };

  @Override
  protected void onResume(){
    super.onResume();
    // Check to see that the Activity started due to an Android Beam
    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
      processIntent(getIntent());
    }
  }


  /**
   * Parses the NDEF Message from the intent and prints to the TextView
   */
  void processIntent(Intent intent) {
    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
        NfcAdapter.EXTRA_NDEF_MESSAGES);
    // only one message sent during the beam
    NdefMessage msg = (NdefMessage) rawMsgs[0];
    String newMemberID = new String(msg.getRecords()[0].getPayload());

    Log.d(TAG, "Beam msg: " + newMemberID);

    // do something?
    finishPairing(newMemberID);

  }

  private void finishPairing(final String newMemberID){
    // show feedback
    findViewById(R.id.pair_progress).setVisibility(View.GONE);
    findViewById(R.id.join_check).setVisibility(View.VISIBLE);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        switch (PairManager.INSTANCE.getMode()) {
          case BINDER:
            // TODO button for binder -> wait until user creates account really
            break;
          case CREATOR:
            Intent data = new Intent();
            data.putExtra("newMemberID", newMemberID);
            if (getParent() == null) {
              setResult(Activity.RESULT_OK, data);
            } else {
              getParent().setResult(Activity.RESULT_OK, data);
            }
            break;
        }
        finish();
      }
    }, 2500);


  }


}
