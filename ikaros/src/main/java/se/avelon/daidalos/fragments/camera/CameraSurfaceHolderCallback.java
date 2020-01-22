package se.avelon.daidalos.fragments.camera;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.SurfaceHolder;

import java.util.Arrays;

import se.avelon.daidalos.Debug;

public class CameraSurfaceHolderCallback implements SurfaceHolder.Callback {
    private static final String TAG = CameraSurfaceHolderCallback.class.getSimpleName();

    private CameraDevice device;

    public CameraSurfaceHolderCallback(CameraDevice device) {
        this.device = device;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Debug.w(TAG, "opening camera...");
            final CaptureRequest.Builder builder = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.addTarget(holder.getSurface());

            device.createCaptureSession(Arrays.asList(holder.getSurface()), new CameraCaptureSessionCallback(device, holder.getSurface()), null);
        }
        catch(CameraAccessException e) {
            Debug.e(TAG, "exception", e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}
