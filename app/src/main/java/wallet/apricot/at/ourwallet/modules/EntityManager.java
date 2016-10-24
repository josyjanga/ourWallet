package wallet.apricot.at.ourwallet.modules;

import android.content.Context;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;
import wallet.apricot.at.ourwallet.interfaces.Callback;
import wallet.apricot.at.ourwallet.interfaces.ITransactionManager;
import wallet.apricot.at.ourwallet.interfaces.IUserManager;
import wallet.apricot.at.ourwallet.interfaces.IWalletManager;
import wallet.apricot.at.ourwallet.util.Util;

/**
 * Created by andy on 07.11.15.
 */
public enum EntityManager implements IWalletManager, IUserManager, ITransactionManager {
  INSTANCE {
    @Override
    public void createAndSendSharedTransaction(SharedAccount wallet, double amount,
                                               String title, Date transactionDate, Member payingMember) {
      SharedTransaction sharedTransaction =
          new SharedTransaction(wallet.getObjectId(), amount, title);
      sharedTransaction.setTransactionDate(transactionDate);
      sharedTransaction.setPayingMember(payingMember);
      sharedTransaction.saveInBackground();
    }

    @Override
    public void saveMyUser(Member user) {
      mUser = user;
      // store local
      mUser.pinInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          Log.d(TAG, "user stored ");
        }
      });
    }

    @Override
    public Member getMyUser() {
      return mUser;
    }

    @Override
    public void addWallet(SharedAccount newWallet, final Callback callback) {
      newWallet.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            callback.walletSaved(true);
          }else
            callback.walletSaved(false);
        }
      });

    }

    @Override
    public void getMyWallets(Context context, final Callback callback) {
      ParseQuery<SharedAccount> query = ParseQuery.getQuery("SharedAccount");
      query.whereEqualTo("groupMembers", Util.getCurrentUserID(context));
      query.findInBackground(new FindCallback<SharedAccount>() {
        @Override
        public void done(List<SharedAccount> objects, com.parse.ParseException e) {
          callback.walletsLoaded(objects);
        }
      });
    }

    @Override
    public void loadTransactions(SharedAccount wallet, final Callback callback) {
      ParseQuery<SharedTransaction> query = ParseQuery.getQuery("SharedTransaction");
      query.addDescendingOrder("transactionDate");
      query.whereEqualTo("sharedAccountID", wallet.getObjectId()).findInBackground(new FindCallback<SharedTransaction>() {
        @Override
        public void done(List<SharedTransaction> objects, com.parse.ParseException e) {
          if (e == null) {
            callback.transactionsLoaded(objects);
          } else {
            Log.d("score", "Error: " + e.getMessage());
          }
        }
      });
    }
  };

  Member mUser;
  final String TAG = EntityManager.class.getSimpleName();

  public void init(Context context) {
    // check if user can be loaded
    String userID = Util.getCurrentUserID(context);

    if (!userID.isEmpty()) {
      ParseQuery<Member> query = ParseQuery.getQuery("Member");
      query.fromLocalDatastore();

      query.getInBackground(userID, new GetCallback<Member>() {
        public void done(Member object, ParseException e) {
          if (e == null) {
            // object will be your game score
            mUser = object;
            mUser.fetchInBackground(new GetCallback<ParseObject>() {
              public void done(ParseObject object, ParseException e) {
                if (e == null) {
                  // Success!
                  mUser.pinInBackground();
                } else {
                  // Failure!
                }
              }
            });
          } else {
            // something went wrong
            Log.w(TAG, "User couldn't be loaded");
          }
        }
      });
    }
  }
}
