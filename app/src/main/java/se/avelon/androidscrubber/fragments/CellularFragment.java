/* (C) 2021 ddjohn@gmail.com */
package se.avelon.androidscrubber.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import se.avelon.androidscrubber.R;

public class CellularFragment extends AbstractFragment {
    private static final String TAG = CellularFragment.class.getSimpleName();

    private TextToSpeech tts = null;

    public String getTitle() {
        return "Cellular";
    }

    public int getIcon() {
        return R.drawable.cellular;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cellular, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onActivityResult(int request, int result, Intent data) {}
}
