/* (C) 2021 ddjohn@gmail.com */
package se.avelon.androidscrubber.fragments;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import se.avelon.androidscrubber.R;
import se.avelon.androidscrubber.Utilities;

public class AudioFragment extends AbstractFragment {
    private static final String TAG = AudioFragment.class.getSimpleName();

    public String getTitle() {
        return "Battery";
    };

    public int getIcon() {
        return R.drawable.audio;
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.audio, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AudioManager manager =
                (AudioManager) this.getActivity().getSystemService(Context.AUDIO_SERVICE);

        /* Devices */
        AudioDeviceInfo devices[] = manager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
        ArrayList list = new ArrayList();
        list.add("Audio Devices:");
        for (AudioDeviceInfo device : devices) {
            Log.i(TAG, "audio=" + "" + device.getProductName() + " (" + device.getId() + ")");
            list.add("" + device.getProductName() + " (" + device.getId() + ")");
        }
        Utilities.spinner(view, R.id.audioSources, list);
    }
}
