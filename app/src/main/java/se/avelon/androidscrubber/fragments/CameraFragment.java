/* (C) 2021 ddjohn@gmail.com */
package se.avelon.androidscrubber.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.app.ActivityCompat;
import se.avelon.androidscrubber.Debug;
import se.avelon.androidscrubber.R;
import se.avelon.androidscrubber.fragments.camera.CameraDeviceCallback;

public class CameraFragment extends AbstractFragment {
    private static final String TAG = CameraFragment.class.getSimpleName();

    public String getTitle() {
        return "Camera";
    };

    public int getIcon() {
        return R.drawable.camera;
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Debug.w(TAG, "Camera permisson granted");
        } else {
            Debug.e(TAG, "Camera permissions where not granted");
            return;
        }

        if (this.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Debug.w(TAG, "Camera system feature");
        } else {
            Debug.e(TAG, "Camera was NOT found");
            return;
        }

        CameraManager manager =
                (CameraManager) this.getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            String cam = manager.getCameraIdList()[0];

            FrameLayout preview = (FrameLayout) view.findViewById(R.id.cameraPreview);
            manager.openCamera(cam, new CameraDeviceCallback(preview), null);

            /*
            manager.openCamera(cam, new CameraDevice.StateCallback() {

                @Override
                public void onOpened(@NonNull final CameraDevice camera) {

                    Debug.w(TAG, "Create surface...");

                    SurfaceView surface = new SurfaceView(view.getContext());
                    FrameLayout preview = (FrameLayout)view.findViewById(R.id.cameraPreview);
                    preview.addView(surface);

                    SurfaceHolder holder = surface.getHolder();
                    holder.addCallback(new CameraSurfaceHolderCallback(camera));
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {}

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {}
            }, null);*/
        } catch (CameraAccessException e) {
            Debug.e(TAG, "exception", e);
        }
    }
}
