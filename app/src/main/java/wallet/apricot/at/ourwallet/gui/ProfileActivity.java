package wallet.apricot.at.ourwallet.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import wallet.apricot.at.ourwallet.R;
import wallet.apricot.at.ourwallet.entities.Member;
import wallet.apricot.at.ourwallet.modules.EntityManager;
import wallet.apricot.at.ourwallet.util.CircleTransform;
import wallet.apricot.at.ourwallet.util.TextFormatUtil;
import wallet.apricot.at.ourwallet.util.Util;

/**
 * Created by andy on 08.11.15.
 */
public class ProfileActivity extends AppCompatActivity {
  private static final int SELECT_PHOTO = 100;
  private ImageView mImgAvatar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    Member myUser = EntityManager.INSTANCE.getMyUser();

    TextView textUser = (TextView) findViewById(R.id.text_user_name);
    textUser.setText(myUser.getFirstName() + " " + myUser.getLastName());

    mImgAvatar = (ImageView) findViewById(R.id.img_avatar);
    try {
      Picasso.with(getApplicationContext()).load(myUser.getAvatarURL())
              .resize(300, 300).centerInside().transform(new CircleTransform())
              .placeholder(R.drawable.account_placeholder).into(mImgAvatar);
    }catch (IllegalArgumentException e){
      e.printStackTrace();
    }
    TextView textBirthday = (TextView) findViewById(R.id.text_birthday);
    textBirthday.setText("Born on " + TextFormatUtil.formatBirthDay(myUser.getBirthday()));

    TextView textMail = (TextView) findViewById(R.id.text_mail);
    textMail.setText("Mail: " + myUser.getMail());

    findViewById(R.id.btn_edit_avatar).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
      }
    });

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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SELECT_PHOTO:
        if (resultCode == RESULT_OK) {
          Uri selectedImage = data.getData();
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
              ParseQuery<Member> query = ParseQuery.getQuery("Member");
              query.getInBackground(Util.getCurrentUserID(getApplicationContext()), new GetCallback<Member>() {
                public void done(final Member member, ParseException e) {
                  if (e == null) {
                    member.put("profileImage", file);
                    member.saveInBackground(new SaveCallback() {
                      @Override
                      public void done(ParseException e) {
                        if (e == null) {
                          Picasso.with(getApplicationContext()).load(member.getAvatarURL())
                              .resize(300, 300).centerInside().transform(new CircleTransform())
                              .placeholder(R.drawable.account_placeholder).into(mImgAvatar);
                        }
                      }
                    });
                  }
                }
              });

              Toast.makeText(ProfileActivity.this, "Image is Uploading...", Toast.LENGTH_SHORT).show();
            }
          });


        }
    }
  }
}
