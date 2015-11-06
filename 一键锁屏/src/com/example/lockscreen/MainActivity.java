package com.example.lockscreen;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DevicePolicyManager mPMD;
	private ComponentName mCN;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
     // ��ȡ�豸���Է���
		mPMD = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mCN = new ComponentName(this, AdminReceiver.class);
        active();
    }
//	�����豸������
	public void active(){
//		�����豸����������ʵ������ת�������豸��activity
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mCN);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "�����豸������");
		startActivity(intent);
	}
//  һ������
	public void click2(View v){
		if(mPMD.isAdminActive(mCN)){
//			�ж��豸�������Ƿ񼤻�
			mPMD.lockNow();
//			������������
			mPMD.resetPassword("", 0);
			finish();
		}
		else{
			Toast.makeText(this, "�������뼤���豸������", 0).show();
		}
	}
//	ж��
	public void click3(View v){
		mPMD.removeActiveAdmin(mCN);
//		ж�س���
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setData(Uri.parse("package:"+getPackageName()));
		startActivity(intent);
	}
//  �������
	public void click4(View v){
		if(mPMD.isAdminActive(mCN)){
//			�ж��豸�������Ƿ񼤻�
//			mPMD.wipeData(0);
			finish();
		}
		else{
			Toast.makeText(this, "�������뼤���豸������", 0).show();
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
