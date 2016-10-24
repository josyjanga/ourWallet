package wallet.apricot.at.ourwallet.gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.interfaces.CallbackAdapter;
import wallet.apricot.at.ourwallet.modules.EntityManager;
import wallet.apricot.at.ourwallet.modules.PairManager;
import wallet.apricot.at.ourwallet.util.AppConstants;
import wallet.apricot.at.ourwallet.util.LayoutUtil;
import wallet.apricot.at.ourwallet.util.Util;

public class CreateSharedWalletActivity extends AppCompatActivity {
  private final String TAG = CreateSharedWalletActivity.class.getSimpleName();
  private final int PAIR_REQUEST = 1;

  UserListAdapter mMemberAdapter;
  EditText mWalletName;

  private final CallbackAdapter mCallback = new CallbackAdapter() {
    @Override
    public void walletSaved(boolean success) {
      if (success) {
        Toast.makeText(getApplicationContext(), "Wallet applied", Toast.LENGTH_SHORT).show();
        Intent data = new Intent();
        data.putExtra("newWalletAdded", true);
        if (getParent() == null) {
          setResult(Activity.RESULT_OK, data);
        } else {
          getParent().setResult(Activity.RESULT_OK, data);
        }
        CreateSharedWalletActivity.this.finish();
      } else {
        Toast.makeText(getApplicationContext(), "Wallet not applied, error", Toast.LENGTH_SHORT).show();
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_shared_wallet);

    mWalletName = (EditText) findViewById(R.id.edit_wallet_name);
    mWalletName.getBackground().setColorFilter(
        getResources().getColor(R.color.colorGreyBackground), PorterDuff.Mode.SRC_ATOP);
    mWalletName.setSingleLine(true);

    ListView walletMemberList = (ListView) findViewById(R.id.list_wallet_members);
    mMemberAdapter = new UserListAdapter(this, new ArrayList<Member>());
    mMemberAdapter.addMember(EntityManager.INSTANCE.getMyUser());
    walletMemberList.setAdapter(mMemberAdapter);


    View btnAddMember = findViewById(R.id.btn_add_member);
    btnAddMember.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //String walletName=((EditText) findViewById(R.id.edit_wallet_name)).getText().toString();
        if (mWalletName.getText().toString().isEmpty()) {
          Toast.makeText(getApplicationContext(), "Wallet name is missing", Toast.LENGTH_SHORT).show();
        } else {
          Intent pairActivity = new Intent(getApplication(), PairMemberActivity.class);
          //pairActivity.putExtra(AppConstants.PAIR_MODE, AppConstants.CREATOR);
          PairManager.INSTANCE.setMode(PairManager.PairMode.CREATOR);
          PairManager.INSTANCE.setWalletName(mWalletName.getText().toString());
          startActivityForResult(pairActivity, PAIR_REQUEST);
        }
      }
    });

    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  /* Called when the second activity's finished */
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      // load Info from new Member
      ParseQuery<Member> query = new ParseQuery<Member>("Member");
      String memberId = data.getExtras().getString("newMemberID");
      if (!mMemberAdapter.contains(memberId)) {
        Log.d(TAG, "add new member: " + memberId);
        query.getInBackground(memberId, new GetCallback<Member>() {
          public void done(Member object, ParseException e) {
            if (e == null) {
              // populate listView
              mMemberAdapter.addMember(object);
            } else {
              // something went wrong
            }
          }
        });
      } else {
        Toast.makeText(getApplicationContext(), "Already paired", Toast.LENGTH_SHORT).show();
      }

    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_create_wallet, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        this.finish();
        return true;
      case R.id.nav_wallet_create:
        //TODO save wallet
        if (mMemberAdapter.getCount() > 1) {
          if (mWalletName.getText().toString().isEmpty()) {
            mWalletName.setError("Wallet Name Missing");
            mWalletName.requestFocus();
          } else {
            EntityManager.INSTANCE.addWallet(new SharedAccount(mMemberAdapter.getMembers(), 200, mWalletName.getText().toString(), "Test", Util.getCurrentUserID(this)), mCallback);
          }
        } else {
          Toast.makeText(getApplicationContext(), "Nobody has joined yet..", Toast.LENGTH_SHORT).show();
        }
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
