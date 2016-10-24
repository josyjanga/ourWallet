package wallet.apricot.at.ourwallet.gui;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.parse.ParsePush;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;
import wallet.apricot.at.ourwallet.interfaces.CallbackAdapter;
import wallet.apricot.at.ourwallet.modules.EntityManager;
import wallet.apricot.at.ourwallet.modules.PairManager;
import wallet.apricot.at.ourwallet.util.LayoutUtil;
import wallet.apricot.at.ourwallet.util.Util;

public class Main2Activity extends AppCompatActivity {
  private final String TAG = Main2Activity.class.getSimpleName();
  private List<SharedAccount> mMyWallets;
  private TransactionListFragment mTransactionListFragment;
  private SharedAccount mCurWallet;

  private enum NavDrawerItems {
    PAYMENTS, STATISTICS, DIVIDER1, CREATE_WALLET, JOIN_WALLET, DIVIDER2, SETTINGS, HELP
  }

  private final CallbackAdapter mCallback = new CallbackAdapter() {
    @Override
    public void walletsLoaded(List<SharedAccount> wallets) {
      mTransactionListFragment.setLoadingIndicator(false);
      if (wallets != null && !wallets.isEmpty()) {
        mMyWallets = wallets;
        subscribeOnAllWallets();
        mDrawerHeader.setProfiles(LayoutUtil.convertToDrawerItems(getApplicationContext(), mMyWallets));
        ProfileDrawerItem activeProfile = (ProfileDrawerItem) mDrawerHeader.getActiveProfile();
        mCurWallet = getWalletById((String) activeProfile.getTag());
        if (mCurWallet != null) EntityManager.INSTANCE.loadTransactions(mCurWallet, mCallback);
      }
    }

    @Override
    public void transactionsLoaded(List<SharedTransaction> transactions) {
      if (transactions != null && mTransactionListFragment != null) {
        mTransactionListFragment.loadTransactionList(transactions);
        mTransactionListFragment.setLoadingIndicator(false);
      }
    }
  };
  private Drawer mDrawer;
  private AccountHeader mDrawerHeader;

  public void subscribeOnAllWallets() {
    for (SharedAccount curWallet : mMyWallets) {
      ParsePush.subscribeInBackground(curWallet.getObjectId());
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    checkUserLogin();

    mMyWallets = new ArrayList<>();
    initFab();
    mDrawerHeader = initWalletNavDrawer();
    mDrawer = initNavDrawerSelections(toolbar, mDrawerHeader);
    mTransactionListFragment = new TransactionListFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, mTransactionListFragment).commit();

    EntityManager.INSTANCE.getMyWallets(getApplicationContext(), mCallback);

  }

  @Override
  /* Called when the second activity's finished */
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data != null && data.getExtras().getBoolean("newWalletAdded")) {
      // refresh
      EntityManager.INSTANCE.getMyWallets(getApplicationContext(),mCallback);//mCurWallet, mCallback);
    }
    if (data != null && data.getExtras().getBoolean(PaymentActivity.TRANS_SUCCESS)) {
      int amount = 250;
      String description = "Billa";
      Date curDate = new Date();
      Member myUser = EntityManager.INSTANCE.getMyUser();
      EntityManager.INSTANCE.createAndSendSharedTransaction(mCurWallet, amount, description, curDate, myUser);
      Util.sendPushNotification(mCurWallet.getObjectId(), amount, myUser.getUserName(), description);
    }
  }

  private void checkUserLogin() {
    if (!Util.isUserLoggedIn(getApplicationContext())) {
      startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
  }

  private Drawer initNavDrawerSelections(Toolbar toolbar, AccountHeader headerResult) {
    PrimaryDrawerItem itemPayments = new PrimaryDrawerItem().withName(R.string.drawer_payments).withIcon(FontAwesome.Icon.faw_list);
    PrimaryDrawerItem itemStatistics = new PrimaryDrawerItem().withName(R.string.drawer_statistics).withIcon(FontAwesome.Icon.faw_pie_chart);

    SecondaryDrawerItem itemJoinwallet = new SecondaryDrawerItem().withName(R.string.drawer_join_wallet)
        .withSelectable(false).withIcon(FontAwesome.Icon.faw_retweet);
    ;
    SecondaryDrawerItem itemCreatewallet = new SecondaryDrawerItem().withName(R.string.drawer_create_wallet)
        .withSelectable(false).withIcon(FontAwesome.Icon.faw_plus);

    SecondaryDrawerItem itemProfile = new SecondaryDrawerItem().withName(R.string.drawer_profile)
        .withSelectable(false).withIcon(FontAwesome.Icon.faw_cog);

    SecondaryDrawerItem itemSettings = new SecondaryDrawerItem().withName(R.string.drawer_settings)
        .withSelectable(false).withIcon(FontAwesome.Icon.faw_cog);
    ;
    SecondaryDrawerItem itemHelp = new SecondaryDrawerItem().withName(R.string.drawer_help)
        .withSelectable(false).withIcon(FontAwesome.Icon.faw_info);
    ;

    //create the drawer and remember the `Drawer` result object
    Drawer drawer = new DrawerBuilder()
        .withAccountHeader(headerResult)
        .withActivity(this)
        .withToolbar(toolbar)
        .addDrawerItems(
            itemPayments,
            itemStatistics,
            new DividerDrawerItem(),
            itemCreatewallet,
            itemJoinwallet,
            new DividerDrawerItem(),
            itemProfile,
            itemSettings,
            itemHelp
        )
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
          @Override
          public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            switch (position) {
              // payment
              case 1:
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, mTransactionListFragment).commit();
                if (mCurWallet != null) {
                  mTransactionListFragment.setLoadingIndicator(true);
                  EntityManager.INSTANCE.loadTransactions(mCurWallet, mCallback);
                  mDrawer.getDrawerLayout().closeDrawers();
                }
                break;
              // stats
              case 2:
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new StatisticsFragment()).commit();
                mDrawer.closeDrawer();
                break;

              // create wallet
              case 4:
                startActivityForResult(new Intent(getApplicationContext(), CreateSharedWalletActivity.class), 1);
                startActivity(new Intent(getApplicationContext(), CreateSharedWalletActivity.class));
                mDrawer.closeDrawer();
                break;

              // join wallet
              case 5:
                PairManager.INSTANCE.setMode(PairManager.PairMode.BINDER);
                startActivity(new Intent(Main2Activity.this, PairMemberActivity.class));
                mDrawer.closeDrawer();
                break;

              // profile
              case 7:
                startActivity(new Intent(Main2Activity.this, ProfileActivity.class));
                mDrawer.closeDrawer();
                break;
              // settings
              case 8:
                startActivity(new Intent(Main2Activity.this, OptionsActivity.class));
                mDrawer.closeDrawer();
                break;

              // settings
              case 9:
                startActivity(new Intent(Main2Activity.this, HelpActivity.class));
                mDrawer.closeDrawer();
                break;
            }
            return true;
          }
        })
        .build();
    return drawer;
  }

  private AccountHeader initWalletNavDrawer() {
    ArrayList<IProfile> drawerItems = LayoutUtil.convertToDrawerItems(getApplicationContext(), mMyWallets);
    //if you want to update the items at a later time it is recommended to keep it in a variable
    return new AccountHeaderBuilder()
        .withActivity(this)
        .withHeaderBackground(R.drawable.header)
        .withProfiles(drawerItems)
        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
          @Override
          public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
            //TODO is now hashcode, should be parse object id
            ProfileDrawerItem drawerItem = (ProfileDrawerItem) profile;
            Log.d("uuu", "Hey" + drawerItem);
            mCurWallet = getWalletById((String) drawerItem.getTag());
            mDrawer.getDrawerLayout().closeDrawers();
            if (mCurWallet != null) {
              mTransactionListFragment.setLoadingIndicator(true);
              EntityManager.INSTANCE.loadTransactions(mCurWallet, mCallback);
              //ParsePush.subscribeInBackground(mCurWallet.getObjectId());
              return true;
            }
            return false;
          }
        })
        .build();
  }

  private SharedAccount getWalletById(String walletId) {
    for (SharedAccount curWallet : mMyWallets) {
      if (curWallet.getObjectId().equals(walletId)) return curWallet;
    }
    return null;
  }

  private void initFab() {
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        startActivityForResult(new Intent(Main2Activity.this, PaymentActivity.class), 1);
        /*EntityManager.INSTANCE.createAndSendSharedTransaction(mCurWallet, 12, "Shared",
            new Date(), EntityManager.INSTANCE.getMyUser());*/
      }
    });
  }

}
