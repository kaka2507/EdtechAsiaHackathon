/*
 *  Copyright 2015 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package act.muzikator.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.webrtc.RendererCommon.ScalingType;

import java.net.URISyntaxException;

import act.muzikator.R;
import act.muzikator.activity.CallActivity;
import act.muzikator.utils.SoundManager;

/**
 * Fragment for call control.
 */
public class CallFragment extends Fragment {
  private View controlView;
  private ImageButton disconnectButton;
  private ImageButton pianoButton;
  private ImageButton key_C, key_D, key_E, key_F, key_G, key_A, key_B,key_C5,
          key_CD, key_DE, key_FG, key_GA, key_AB;
  private RelativeLayout virtualInstrumentContainer;
  private OnCallEvents callEvents;
  private ScalingType scalingType;
  private boolean videoCallEnabled = true;
  private boolean isDisplayingPiano = false;
  private SoundManager soundManager;
  private Socket socket;

  /**
   * Call control interface for container activity.
   */
  public interface OnCallEvents {
    public void onCallHangUp();
    public void onCameraSwitch();
    public void onVideoScalingSwitch(ScalingType scalingType);
    public void onCaptureFormatChange(int width, int height, int framerate);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    controlView =
        inflater.inflate(R.layout.fragment_call_2, container, false);

    // Create UI controls for disconnectButton.
    disconnectButton =
        (ImageButton) controlView.findViewById(R.id.button_call_disconnect);

    // Add buttons click events for disconnectButton.
    disconnectButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        callEvents.onCallHangUp();
      }
    });

    // Create UI controls for pianoButton.
    this.pianoButton =
            (ImageButton) controlView.findViewById(R.id.button_toggle_piano);

    // Add buttons click events for pianoButton.
    pianoButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(virtualInstrumentContainer.isShown()) {
          virtualInstrumentContainer.setVisibility(View.INVISIBLE);
        } else {
          virtualInstrumentContainer.setVisibility(View.VISIBLE);
        }
      }
    });

    // Create UI controls for virtualInstrumentContainer.
    this.virtualInstrumentContainer =
            (RelativeLayout) controlView.findViewById(R.id.virtual_instrument_container);
    if(this.virtualInstrumentContainer.isShown()) {
      this.virtualInstrumentContainer.setVisibility(View.INVISIBLE);
    }

    // Load Sound System
    soundManager = new SoundManager();
    soundManager.initSounds(getActivity());
    soundManager.addSoundResource(R.raw.c4);
    // todo : add resource sound here

    // Create UI controls for Piano Keys.
    key_C = (ImageButton) controlView.findViewById(R.id.key_C);
    key_C.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d("FFF", "REACHED HERE");
        soundManager.playSoundResource(R.raw.c4);
      }
    });

//    key_D = (ImageButton) controlView.findViewById(R.id.key_D);
//    key_D.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyD();
//      }
//    });
//
//    key_E = (ImageButton) controlView.findViewById(R.id.key_E);
//    key_E.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyE();
//      }
//    });
//
//    key_F = (ImageButton) controlView.findViewById(R.id.key_F);
//    key_F.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyF();
//      }
//    });
//
//    key_G = (ImageButton) controlView.findViewById(R.id.key_G);
//    key_G.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyG();
//      }
//    });
//
//    key_A = (ImageButton) controlView.findViewById(R.id.key_A);
//    key_A.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyA();
//      }
//    });
//
//    key_B = (ImageButton) controlView.findViewById(R.id.key_B);
//    key_B.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyB();
//      }
//    });
//
//    key_C5 = (ImageButton) controlView.findViewById(R.id.key_C5);
//    key_C5.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyC5();
//      }
//    });
//
//    key_CD = (ImageButton) controlView.findViewById(R.id.key_CD);
//    key_CD.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyCD();
//      }
//    });
//
//    key_DE = (ImageButton) controlView.findViewById(R.id.key_DE);
//    key_DE.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyDE();
//      }
//    });
//
//    key_FG = (ImageButton) controlView.findViewById(R.id.key_FG);
//    key_FG.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyFG();
//      }
//    });
//
//    key_GA = (ImageButton) controlView.findViewById(R.id.key_GA);
//    key_GA.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyGA();
//      }
//    });
//
//    key_AB = (ImageButton) controlView.findViewById(R.id.key_AB);
//    key_AB.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        callEvents.onCallKeyAB();
//      }
//    });

    scalingType = ScalingType.SCALE_ASPECT_FILL;

    return controlView;
  }

  @Override
  public void onStart() {
    super.onStart();

    boolean captureSliderEnabled = false;
    Bundle args = getArguments();
    if (args != null) {
      videoCallEnabled = args.getBoolean(CallActivity.EXTRA_VIDEO_CALL, true);
      captureSliderEnabled = videoCallEnabled
          && args.getBoolean(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, false);
    }

//    try {
//      this.socket = IO.socket("http://192.168.10.119:23712/central");
//      this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//
//        @Override
//        public void call(Object... args) {
//          socket.emit("edtech-classroom-start-request", "hi");
//          socket.disconnect();
//        }
//
//      }).on("event", new Emitter.Listener() {
//
//        @Override
//        public void call(Object... args) {}
//
//      }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//
//        @Override
//        public void call(Object... args) {}
//
//      });
//      socket.connect();
//    } catch (URISyntaxException e) {
//      e.printStackTrace();
//    }

  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    callEvents = (OnCallEvents) activity;
  }

}
