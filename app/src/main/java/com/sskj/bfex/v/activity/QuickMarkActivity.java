package com.sskj.bfex.v.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.quickmark.camera.CameraManager;
import com.sskj.bfex.quickmark.decoding.CaptureActivityHandler;
import com.sskj.bfex.quickmark.decoding.InactivityTimer;
import com.sskj.bfex.quickmark.view.ViewfinderView;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.io.IOException;
import java.util.Vector;

import butterknife.BindView;

/**
 * ProjectName：qb
 * DESC: (二维码扫描)
 * Created by 李岩 on 2018/5/7 0007
 * updateName:(修改人名称)
 * updateTime:(修改时间)
 * updateDesc:(修改内容)
 */
public class QuickMarkActivity extends BaseActivity implements Callback {

	private CaptureActivityHandler handler; //y

	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats; //y
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;  // 滴滴声
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	/**
	 * 闪光灯标记
	 */
	private boolean isOpen = false;
	private boolean mIsDelivery;

	@BindView(R.id.viewfinder_view)
	ViewfinderView viewfinderView;
	@BindView(R.id.preview_view)
	SurfaceView mSurfaceView;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CameraManager.init(this);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		requestPermission();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_capture;
	}

	@Override
	public BasePresenter getPresenter() {
		return new BasePresenter();
	}

	@Override
	public void onResume() {
		super.onResume();
		SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
		//必须要判断 hasSurface 当surFaceView 创建成功后才能开启相机
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	/**
	 * 闪光灯开关
	 */
	public void mask(View v) {
		if (!isOpen) {
			CameraManager.get().enableFlash();
			isOpen = true;
		} else {  // 关灯
			CameraManager.get().disableFlash();
			isOpen = false;
		}
	}

	/**
	 * 返回开关
	 */
	public void back(View v) {
		finish();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (inactivityTimer != null){
			inactivityTimer.shutdown();
		}
		if (handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}

	/**
	 * TODO 二维码扫描结果回调
	 *
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (TextUtils.isEmpty(resultString)) {
			restartPreviewAndDecode();
			ToastUtil.showLong("扫描识别失败!");
		} else {
			//二维码扫描结果回调
//			ToastUtil.showLong("结果" + resultString);
            Intent intent = new Intent();
            intent.putExtra("scan_result", resultString);
            setResult(RESULT_OK, intent);
			finish();
		}

	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	/**
	 * 扫描过程中的滴滴声
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			if (!mIsDelivery){
				mediaPlayer.start();
			}
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	/**
	 * 开启二次扫描
	 */
	void restartPreviewAndDecode(){
		if (handler != null){
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (handler != null) {
						handler.restartPreviewAndDecode();
					}
				}
			}, 1000);

		}
	}

	/**
	 * 请求权限
	 *
	 * @return
	 */
	boolean requestPermission() {
		if (ContextCompat.checkSelfPermission(QuickMarkActivity.this, Manifest.permission.CAMERA)
				== PackageManager.PERMISSION_GRANTED) {
			return true;
		} else {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_REQUEST_CAMERA);
			Toast.makeText(QuickMarkActivity.this, "权限未开启，我们需要这个权限给你提供扫码服务", Toast.LENGTH_LONG).show();
			return false;
		}
	}

	/**
	 * 权限请求回调
	 *
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		if (requestCode != Constants.PERMISSION_REQUEST_CAMERA) {
			return;
		}

		if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//开通权限

		}
	}

}