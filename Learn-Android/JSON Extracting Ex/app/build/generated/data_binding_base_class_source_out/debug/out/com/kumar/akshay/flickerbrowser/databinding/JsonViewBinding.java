// Generated by view binder compiler. Do not edit!
package com.kumar.akshay.flickerbrowser.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.kumar.akshay.flickerbrowser.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class JsonViewBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView textView;

  @NonNull
  public final ImageView thumbnail;

  private JsonViewBinding(@NonNull LinearLayout rootView, @NonNull TextView textView,
      @NonNull ImageView thumbnail) {
    this.rootView = rootView;
    this.textView = textView;
    this.thumbnail = thumbnail;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static JsonViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static JsonViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.json_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static JsonViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      TextView textView = rootView.findViewById(R.id.textView);
      if (textView == null) {
        missingId = "textView";
        break missingId;
      }
      ImageView thumbnail = rootView.findViewById(R.id.thumbnail);
      if (thumbnail == null) {
        missingId = "thumbnail";
        break missingId;
      }
      return new JsonViewBinding((LinearLayout) rootView, textView, thumbnail);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}