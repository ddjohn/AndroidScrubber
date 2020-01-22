package se.avelon.daidalos.fragments.camera;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.view.Surface;

import se.avelon.daidalos.Debug;

public class CameraCaptureSessionCallback extends CameraCaptureSession.StateCallback {
    private static final String TAG = CameraCaptureSessionCallback.class.getSimpleName();
    private CaptureRequest.Builder builder;

    public CameraCaptureSessionCallback(CameraDevice device, Surface surface) {
        Debug.w(TAG, "opening camera...");
        try {
            builder = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        }
        catch(CameraAccessException e) {
            Debug.e(TAG, "exception", e);
        }

        builder.addTarget(surface);
    }

    @Override
    public void onConfigured(@NonNull CameraCaptureSession session) {
        Debug.w(TAG, "camera configured...");

        builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            HandlerThread thread = new HandlerThread("Camera Background");
            thread.start();
            Handler handler = new Handler(thread.getLooper());

            session.setRepeatingRequest(builder.build(), null, handler);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigureFailed(@NonNull CameraCaptureSession session) {}
}
