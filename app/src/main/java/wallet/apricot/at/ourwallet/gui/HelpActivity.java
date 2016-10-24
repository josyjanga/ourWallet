package wallet.apricot.at.ourwallet.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.util.Util;


public class HelpActivity extends AppCompatActivity {
  private final String TAG = HelpActivity.class.getSimpleName();
  private static final int SELECT_PHOTO = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);

    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
