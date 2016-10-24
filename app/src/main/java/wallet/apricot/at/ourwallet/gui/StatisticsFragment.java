package wallet.apricot.at.ourwallet.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wallet.apricot.at.ourwallet.R;

/**
 * Created by andy on 08.11.15.
 */
public class StatisticsFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_statistics, container, false);
    return view;
  }
}
