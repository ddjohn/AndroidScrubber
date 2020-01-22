package se.avelon.daidalos.fragments.camera;

import android.hardware.camera2.CameraDevice;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import se.avelon.daidalos.Debug;

public class CameraDeviceCallback extends CameraDevice.StateCallback {
    private static final String TAG = CameraDeviceCallback.class.getSimpleName();
    private FrameLayout layout;

    public CameraDeviceCallback(FrameLayout layout) {
        this.layout = layout;
    }

    @Override
    public void onOpened(@NonNull CameraDevice camera) {

        Debug.w(TAG, "Create surface...");

        SurfaceView surface = new SurfaceView(layout.getContext());
        layout.addView(surface);

        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(new CameraSurfaceHolderCallback(camera));
    }

    @Override
    public void onDisconnected(@NonNull CameraDevice camera) {}

    @Override
    public void onError(@NonNull CameraDevice camera, int error) {}

}
