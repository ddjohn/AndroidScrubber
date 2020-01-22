package se.avelon.daidalos.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.io.IOException;
import java.util.Arrays;

import se.avelon.daidalos.Debug;
import se.avelon.daidalos.R;

public class CameraFragment extends AbstractFragment implements SurfaceHolder.Callback {
    private static final String TAG = CameraFragment.class.getSimpleName();
    private Camera camera;

    public String getTitle() {return "Camera";};
    public int getIcon() {return R.drawable.camera;};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Debug.w(TAG, "Camera permisson granted");

            if(this.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Debug.w(TAG, "Camera system feature");

                CameraManager manager = (CameraManager)this.getContext().getSystemService(Context.CAMERA_SERVICE);
                try {
                    String cam = manager.getCameraIdList()[0];
                    manager.openCamera(cam, new CameraDevice.StateCallback() {

                        @Override
                        public void onOpened(@NonNull final CameraDevice camera) {

                            Debug.w(TAG, "Create surface...");

                            SurfaceView surface = new SurfaceView(view.getContext());
                            FrameLayout preview = (FrameLayout)view.findViewById(R.id.cameraPreview);
                            preview.addView(surface);

                            SurfaceHolder holder = surface.getHolder();
                            holder.addCallback(new SurfaceHolder.Callback() {

                                @Override
                                public void surfaceCreated(SurfaceHolder holder) {

                                    try {
                                        Debug.w(TAG, "opening camera...");
                                        final CaptureRequest.Builder builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                                        builder.addTarget(holder.getSurface());
                                        camera.createCaptureSession(Arrays.asList(holder.getSurface()), new CameraCaptureSession.StateCallback(){
                                            @Override
                                            public void onConfigured(@NonNull CameraCaptureSession session) {
                                                Debug.w(TAG, "camera configured...");

                                                builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                                                try {
                                                    HandlerThread thread = new HandlerThread("Camera Background");
                                                    thread.start();
                                                    Handler handler = new Handler(thread.getLooper());

                                                    session.setRepeatingRequest(builder.build(), null, handler);
                                                } catch (CameraAccessException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onConfigureFailed(@NonNull CameraCaptureSession session) {}
                                        }, null);
                                    }
                                    catch(CameraAccessException e) {
                                        Debug.e(TAG, "exception", e);
                                    }
                                }

                                @Override
                                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

                                @Override
                                public void surfaceDestroyed(SurfaceHolder holder) {}
                            });
                        }

                        @Override
                        public void onDisconnected(@NonNull CameraDevice camera) {}

                        @Override
                        public void onError(@NonNull CameraDevice camera, int error) {}
                    }, null);
                }
                catch(CameraAccessException e) {
                    Debug.e(TAG, "exception", e);
                }
            }
            else {
                Debug.e(TAG, "Camera was NOT found");
            }
        }
        else {
            Debug.e(TAG, "Camera permissions where not granted");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Debug.d(TAG, "Surface created");

            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }
        catch(IOException e) {}
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (holder.getSurface() == null){
            return;
        }

        try {
            camera.stopPreview();
        }
        catch(Exception e) {}

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch(Exception e) {

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}