// Generated by view binder compiler. Do not edit!
package com.kumar.akshay.v_player.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.kumar.akshay.v_player.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ControllerViewBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ImageButton backwardButton;

  @NonNull
  public final ImageButton forwardButton;

  @NonNull
  public final ImageButton pauseButton;

  @NonNull
  public final ImageButton playButton;

  @NonNull
  public final SeekBar seekBar;

  @NonNull
  public final Toolbar toolbar2;

  @NonNull
  public final AppBarLayout videoAppBarLayout;

  private ControllerViewBinding(@NonNull FrameLayout rootView, @NonNull ImageButton backwardButton,
      @NonNull ImageButton forwardButton, @NonNull ImageButton pauseButton,
      @NonNull ImageButton playButton, @NonNull SeekBar seekBar, @NonNull Toolbar toolbar2,
      @NonNull AppBarLayout videoAppBarLayout) {
    this.rootView = rootView;
    this.backwardButton = backwardButton;
    this.forwardButton = forwardButton;
    this.pauseButton = pauseButton;
    this.playButton = playButton;
    this.seekBar = seekBar;
    this.toolbar2 = toolbar2;
    this.videoAppBarLayout = videoAppBarLayout;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ControllerViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ControllerViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.controller_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ControllerViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backwardButton;
      ImageButton backwardButton = rootView.findViewById(id);
      if (backwardButton == null) {
        break missingId;
      }

      id = R.id.forwardButton;
      ImageButton forwardButton = rootView.findViewById(id);
      if (forwardButton == null) {
        break missingId;
      }

      id = R.id.pauseButton;
      ImageButton pauseButton = rootView.findViewById(id);
      if (pauseButton == null) {
        break missingId;
      }

      id = R.id.playButton;
      ImageButton playButton = rootView.findViewById(id);
      if (playButton == null) {
        break missingId;
      }

      id = R.id.seekBar;
      SeekBar seekBar = rootView.findViewById(id);
      if (seekBar == null) {
        break missingId;
      }

      id = R.id.toolbar2;
      Toolbar toolbar2 = rootView.findViewById(id);
      if (toolbar2 == null) {
        break missingId;
      }

      id = R.id.videoAppBarLayout;
      AppBarLayout videoAppBarLayout = rootView.findViewById(id);
      if (videoAppBarLayout == null) {
        break missingId;
      }

      return new ControllerViewBinding((FrameLayout) rootView, backwardButton, forwardButton,
          pauseButton, playButton, seekBar, toolbar2, videoAppBarLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}