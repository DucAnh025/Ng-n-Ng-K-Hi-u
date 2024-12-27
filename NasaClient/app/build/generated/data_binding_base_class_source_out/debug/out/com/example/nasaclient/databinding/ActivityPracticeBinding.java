// Generated by view binder compiler. Do not edit!
package com.example.nasaclient.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.nasaclient.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPracticeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView btnCameraFlip;

  @NonNull
  public final ImageView btnCapture;

  @NonNull
  public final ImageView btnFlash;

  @NonNull
  public final CardView cameraContainer;

  @NonNull
  public final PreviewView cameraPreview;

  @NonNull
  public final TextView tvCaption;

  private ActivityPracticeBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView btnCameraFlip, @NonNull ImageView btnCapture, @NonNull ImageView btnFlash,
      @NonNull CardView cameraContainer, @NonNull PreviewView cameraPreview,
      @NonNull TextView tvCaption) {
    this.rootView = rootView;
    this.btnCameraFlip = btnCameraFlip;
    this.btnCapture = btnCapture;
    this.btnFlash = btnFlash;
    this.cameraContainer = cameraContainer;
    this.cameraPreview = cameraPreview;
    this.tvCaption = tvCaption;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPracticeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPracticeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_practice, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPracticeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCameraFlip;
      ImageView btnCameraFlip = ViewBindings.findChildViewById(rootView, id);
      if (btnCameraFlip == null) {
        break missingId;
      }

      id = R.id.btnCapture;
      ImageView btnCapture = ViewBindings.findChildViewById(rootView, id);
      if (btnCapture == null) {
        break missingId;
      }

      id = R.id.btnFlash;
      ImageView btnFlash = ViewBindings.findChildViewById(rootView, id);
      if (btnFlash == null) {
        break missingId;
      }

      id = R.id.cameraContainer;
      CardView cameraContainer = ViewBindings.findChildViewById(rootView, id);
      if (cameraContainer == null) {
        break missingId;
      }

      id = R.id.cameraPreview;
      PreviewView cameraPreview = ViewBindings.findChildViewById(rootView, id);
      if (cameraPreview == null) {
        break missingId;
      }

      id = R.id.tvCaption;
      TextView tvCaption = ViewBindings.findChildViewById(rootView, id);
      if (tvCaption == null) {
        break missingId;
      }

      return new ActivityPracticeBinding((ConstraintLayout) rootView, btnCameraFlip, btnCapture,
          btnFlash, cameraContainer, cameraPreview, tvCaption);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
