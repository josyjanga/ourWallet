package wallet.apricot.at.ourwallet.gui;


import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.entities.SharedAccount;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;

/**
 * Created by andy on 07.11.15.
 */
public class TransactionListFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {

  private ListView mTransactionListView;
  private MaterialProgressBar mProgressBar;
  private ImageView mImgEmptyList;

  public TransactionListFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
    mTransactionListView = (ListView) view.findViewById(R.id.list_transaction);
    mProgressBar = (MaterialProgressBar) view.findViewById(R.id.loading_transactions);
    mImgEmptyList = (ImageView) view.findViewById(R.id.img_empty_list);
    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d("Huu", "Attached!!");
  }

  public void loadTransactionList(List<SharedTransaction> transactions) {
    if (transactions != null) {
      mTransactionListView.setAdapter(new TransactionListAdapter(getActivity(), transactions));
      mTransactionListView.setOnItemClickListener(TransactionListFragment.this);
    }
    mImgEmptyList.setVisibility(transactions.isEmpty() ? View.VISIBLE : View.INVISIBLE);
  }

  public void setLoadingIndicator(boolean enabled) {
    if (mProgressBar != null) {
      mProgressBar.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    //Toast.makeText(getActivity(), "Item: " + i, Toast.LENGTH_SHORT).show();
    //final String item = (String) parent.getItemAtPosition(i);
    SharedTransaction curTransaction = (SharedTransaction) adapterView.getItemAtPosition(i);
    Intent intent = new Intent(this.getContext(), TransactionDetailActivity.class);
    //intent.putExtra("wallet.apricot.at.ourwallet.entities.SharedTransaction", curTransaction);
    startActivity(intent);

//    view.animate().setDuration(2000).alpha(0)
//        .withEndAction(new Runnable() {
//          @Override
//          public void run() {
//            Intent intent = new Intent(this.getContext(), TransactionDetailActivity.class);
//            startActivity(intent);
//          }
//        });

  }

}
