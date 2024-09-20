// Generated by view binder compiler. Do not edit!
package com.example.nasaclient.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.nasaclient.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemNotificationBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView notificationIcon;

  @NonNull
  public final TextView notificationTime;

  @NonNull
  public final TextView notificationTitle;

  private ItemNotificationBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView notificationIcon, @NonNull TextView notificationTime,
      @NonNull TextView notificationTitle) {
    this.rootView = rootView;
    this.notificationIcon = notificationIcon;
    this.notificationTime = notificationTime;
    this.notificationTitle = notificationTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemNotificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemNotificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_notification, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemNotificationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.notificationIcon;
      ImageView notificationIcon = ViewBindings.findChildViewById(rootView, id);
      if (notificationIcon == null) {
        break missingId;
      }

      id = R.id.notificationTime;
      TextView notificationTime = ViewBindings.findChildViewById(rootView, id);
      if (notificationTime == null) {
        break missingId;
      }

      id = R.id.notificationTitle;
      TextView notificationTitle = ViewBindings.findChildViewById(rootView, id);
      if (notificationTitle == null) {
        break missingId;
      }

      return new ItemNotificationBinding((LinearLayout) rootView, notificationIcon,
          notificationTime, notificationTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
