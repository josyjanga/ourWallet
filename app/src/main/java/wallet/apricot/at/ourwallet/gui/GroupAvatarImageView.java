package wallet.apricot.at.ourwallet.gui;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import wallet.apricot.at.ourwallet.R;

/**
 * Created by andy on 08.11.15.
 */
public class GroupAvatarImageView extends RelativeLayout {

  private ImageView mLeftAvatar;
  private ImageView mTopAvatar;
  private ImageView mBottomAvatar;

  public GroupAvatarImageView(Context context) {
    super(context);
    init();
  }

  public GroupAvatarImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public void init() {
    inflate(getContext(), R.layout.item_group_avatar, this);

    mLeftAvatar = (ImageView) findViewById(R.id.img_avatar1);
    ClipDrawable avatarDrawable1 = (ClipDrawable) mLeftAvatar.getDrawable();
    avatarDrawable1.setLevel(5000);

    mTopAvatar = (ImageView) findViewById(R.id.img_avatar2);
    ClipDrawable avatarDrawable2 = (ClipDrawable) mTopAvatar.getDrawable();
    avatarDrawable2.setLevel(5000);

    mBottomAvatar = (ImageView) findViewById(R.id.img_avatar3);
    ClipDrawable avatarDrawable3 = (ClipDrawable) mBottomAvatar.getDrawable();
    avatarDrawable3.setLevel(5000);
  }


}
