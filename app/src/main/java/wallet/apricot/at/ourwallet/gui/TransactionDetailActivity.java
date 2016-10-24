package wallet.apricot.at.ourwallet.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import wallet.apricot.at.ourwallet.entities.SharedTransaction;

import wallet.apricot.at.ourwallet.R;

public class TransactionDetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_detail);

    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }
}
