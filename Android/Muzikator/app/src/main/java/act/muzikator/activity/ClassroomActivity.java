package act.muzikator.activity;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ScrollView;

import java.util.Random;

import act.muzikator.R;
import butterknife.Bind;
import butterknife.OnClick;

public class ClassroomActivity extends BaseActivity {
    private final static String TAG = "ClassroomActivity";
    @Bind(R.id.popularButton)  Button     popularButton;
    @Bind(R.id.historyButton)  Button     historyButton;
    @Bind(R.id.popularWrapper) ScrollView popularWrapper;
    @Bind(R.id.historyWrapper) ScrollView historyWrapper;

    private boolean isPopularSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        showPopularTab();
    }

    @OnClick(R.id.popularButton)
    public void showPopularTab() {
        if (!isPopularSelected) {
            isPopularSelected = true;

            popularWrapper.setVisibility(View.VISIBLE);
            popularButton.setBackgroundResource(R.drawable.ic_class_tab_popular_selected);

            historyWrapper.setVisibility(View.INVISIBLE);
            historyButton.setBackgroundResource(R.drawable.ic_class_tab_history);
        }
    }

    @OnClick(R.id.historyButton)
    public void showHistoryTab() {
        if (isPopularSelected) {
            isPopularSelected = false;

            popularWrapper.setVisibility(View.INVISIBLE);
            popularButton.setBackgroundResource(R.drawable.ic_class_tab_popular);

            historyWrapper.setVisibility(View.VISIBLE);
            historyButton.setBackgroundResource(R.drawable.ic_class_tab_history_selected);
        }
    }

    @OnClick(R.id.tutor1)
    public void tutor1Selected() {
        videoCallWithTutor(1);
    }

    @OnClick(R.id.tutor2)
    public void tutor2Selected() {
        videoCallWithTutor(2);
    }

    @OnClick(R.id.tutor3)
    public void tutor3Selected() {
        videoCallWithTutor(3);
    }

    @OnClick(R.id.tutor4)
    public void tutor4Selected() {
        videoCallWithTutor(4);
    }

    private void videoCallWithTutor(int idx) {
        connectToRoom(false, 0, idx);
    }

    private void connectToRoom(boolean loopback, int runTimeMs, int idx) {
        // Get room name (random for loopback).
        String roomId = "muzikator_" + idx;
        String roomUrl = getString(R.string.pref_room_server_url_default);

        // Video call enabled flag.
        boolean videoCallEnabled = Boolean.valueOf(getString(R.string.pref_videocall_default));

        // Get default codecs.
        String videoCodec = getString(R.string.pref_videocodec_default);
        String audioCodec = getString(R.string.pref_audiocodec_default);

        // Check HW codec flag.
        boolean hwCodec = Boolean.valueOf(getString(R.string.pref_hwcodec_default));

        // Check Capture to texture.
        boolean captureToTexture = Boolean.valueOf(getString(R.string.pref_capturetotexture_default));

        // Check Disable Audio Processing flag.
        boolean noAudioProcessing = Boolean.valueOf(getString(R.string.pref_noaudioprocessing_default));

        // Check Disable Audio Processing flag.
        boolean aecDump = Boolean.valueOf(getString(R.string.pref_aecdump_default));

        // Check OpenSL ES enabled flag.
        boolean useOpenSLES = Boolean.valueOf(getString(R.string.pref_opensles_default));

        // Get video resolution from settings.
        int videoWidth = 0;
        int videoHeight = 0;
        String resolution = getString(R.string.pref_resolution_default);
        String[] dimensions = resolution.split("[ x]+");
        if (dimensions.length == 2) {
            try {
                videoWidth = Integer.parseInt(dimensions[0]);
                videoHeight = Integer.parseInt(dimensions[1]);
            } catch (NumberFormatException e) {
                videoWidth = 0;
                videoHeight = 0;
                Log.e(TAG, "Wrong video resolution setting: " + resolution);
            }
        }

        // Get camera fps from settings.
        int cameraFps = 0;
        String fps = getString(R.string.pref_fps_default);
        String[] fpsValues = fps.split("[ x]+");
        if (fpsValues.length == 2) {
            try {
                cameraFps = Integer.parseInt(fpsValues[0]);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Wrong camera fps setting: " + fps);
            }
        }

        // Check capture quality slider flag.
        boolean captureQualitySlider = Boolean.valueOf(getString(R.string.pref_capturequalityslider_default));

        // Get video and audio start bitrate.
        int videoStartBitrate = Integer.parseInt(getString(R.string.pref_startvideobitratevalue_default));
        String bitrateType = getString(R.string.pref_startvideobitrate_default);
        int audioStartBitrate = Integer.parseInt(getString(R.string.pref_startaudiobitratevalue_default));
        bitrateType = getString(R.string.pref_startaudiobitrate_default);

        // Start AppRTCDemo activity.
        Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
        if (validateUrl(roomUrl)) {
            Uri uri = Uri.parse(roomUrl);
            Intent intent = new Intent(this, CallActivity.class);
            intent.setData(uri);
            intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
            intent.putExtra(CallActivity.EXTRA_LOOPBACK, loopback);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
            intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
            intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
            intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                    captureQualitySlider);
            intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
            intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
            intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
            intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
            intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED,
                    noAudioProcessing);
            intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
            intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
            intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
            intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
            intent.putExtra(CallActivity.EXTRA_RUNTIME, runTimeMs);

            startActivityForResult(intent, 1);
        }
    }

    private boolean validateUrl(String url) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return true;
        }
        Log.e(TAG, "Url:" + url + " is invalid");
        return false;
    }
}
