// Generated by view binder compiler. Do not edit!
package com.example.nasaclient.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public final class ActivityHelpAndSupportBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnback;

  @NonNull
  public final LinearLayout main;

  @NonNull
  public final TextView titleText;

  private ActivityHelpAndSupportBinding(@NonNull LinearLayout rootView, @NonNull Button btnback,
      @NonNull LinearLayout main, @NonNull TextView titleText) {
    this.rootView = rootView;
    this.btnback = btnback;
    this.main = main;
    this.titleText = titleText;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHelpAndSupportBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHelpAndSupportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_help_and_support, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHelpAndSupportBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnback;
      Button btnback = ViewBindings.findChildViewById(rootView, id);
      if (btnback == null) {
        break missingId;
      }

      LinearLayout main = (LinearLayout) rootView;

      id = R.id.titleText;
      TextView titleText = ViewBindings.findChildViewById(rootView, id);
      if (titleText == null) {
        break missingId;
      }

      return new ActivityHelpAndSupportBinding((LinearLayout) rootView, btnback, main, titleText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
