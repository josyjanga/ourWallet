package wallet.apricot.at.ourwallet.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.util.Util;


public class OptionsActivity extends AppCompatActivity {
  private final String TAG = OptionsActivity.class.getSimpleName();
  private static final int SELECT_PHOTO = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_options);

    Button btn_upload_img = (Button) findViewById(R.id.btn_upload_img);
    // Capture button clicks
    btn_upload_img.setOnClickListener(new View.OnClickListener() {
      @Override
        public void onClick(View arg0) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
      }
    });




    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
    super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

    switch(requestCode) {
      case SELECT_PHOTO:
        if(resultCode == RESULT_OK){
          Uri selectedImage = imageReturnedIntent.getData();
          InputStream imageStream = null;
          try {
            imageStream = getContentResolver().openInputStream(selectedImage);
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
          Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

          // Convert it to byte
          ByteArrayOutputStream stream = new ByteArrayOutputStream();

          // Compress image to lower quality scale 1 - 100
          yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
          byte[] image = stream.toByteArray();

          // Create the ParseFile & upload the image into Parse Cloud
          final ParseFile file = new ParseFile("profile.png", image);
          file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              ParseQuery<ParseObject> query = ParseQuery.getQuery("Member");
              query.getInBackground(Util.getCurrentUserID(getApplicationContext()), new GetCallback<ParseObject>() {
                public void done(ParseObject member, ParseException e) {
                  if (e == null) {
                    member.put("profileImage", file);
                    member.saveInBackground();
                  }
                }
              });

              Toast.makeText(OptionsActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
          });


        }
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
}
