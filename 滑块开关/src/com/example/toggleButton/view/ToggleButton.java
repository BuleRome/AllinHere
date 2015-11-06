package com.example.toggleButton.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ToggleButton extends View{

	private ToggleState tstate;
	private Bitmap switchbg;
	private Bitmap slidebg;
	private int currentX;
	private boolean isSliding = false;
	private OnToggleButtonChangeListener listener;
	private int startX;
	
	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public ToggleButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}



	public enum ToggleState{
		Open,Close
	}
//   ���û����� �ı���ͼƬ
	public void setSlideBackgroundResource(int checkboxBg) {
		slidebg = BitmapFactory.decodeResource(getResources(), checkboxBg);
	}
//  ���ÿ��صı���ͼƬ
	public void setSwitchBackgroundResource(int checkboxSwich) {
		switchbg = BitmapFactory.decodeResource(getResources(), checkboxSwich);
	}
	
	public void setToggleState(ToggleState state){
		tstate = state;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		���õ�ǰ�ؼ���ʾ����Ļ�ϵĿ��
		setMeasuredDimension(slidebg.getWidth(), slidebg.getHeight());
	}
//	��������Ļ�ϵľ�������
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(slidebg, 0, 0, null);
		if(isSliding){
			int left = currentX-switchbg.getWidth()/2;
			if(left<0)
				left = 0;
			if(left>(slidebg.getWidth()-switchbg.getWidth()))
				left = slidebg.getWidth()-switchbg.getWidth();
			canvas.drawBitmap(switchbg, left, 0, null);
		}
		else{
			if(ToggleState.Open == tstate){
				
				canvas.drawBitmap(switchbg, 0, 0, null);
			}
			else{
				canvas.drawBitmap(switchbg, slidebg.getWidth()-switchbg.getWidth(), 0, null);
			}
		}
		
	}
	
	
	
//	���ô�������
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		currentX = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();
			int slideX = Math.abs(startX-moveX);
//			slideX��ʾ��ָ������Ļʱ������ƫ������������ָ������Ļʱ�����Ѳ������ƶ������������ĵ����¼�������ƫ����Ϊ0��������
//			ʵ����ʵ�֣�Ҫ���õ����¼��ļ�������ֻ�ܶ�ƫ���������жϣ��϶�ƫ������һ����Χ֮�������ڵ����¼���
//			System.out.println("ƫ����"+slideX);
			if(slideX<5)
//				ƫ����С��5���ж��ǵ����¼�
				isSliding = false;
			else
				isSliding = true;
			break;
		case MotionEvent.ACTION_UP:
//			���isSliding��ֵΪtrue��˵���������ƶ���ִ�����·���
			if(isSliding){
				if(currentX<slidebg.getWidth()/2){
//					��������С��һ�룬���ÿ���Ϊ����
					if(tstate!=ToggleState.Open){
						tstate = ToggleState.Open;
						if(listener!=null){
							listener.onToggleButtonStateChange(tstate);
							System.out.println("����1");
						}
					}
				}
				else{
					if(tstate!=ToggleState.Close){
						tstate = ToggleState.Close;
						if(listener!=null){
							listener.onToggleButtonStateChange(tstate);
							System.out.println("�ر�1");
						}
					}
				}
				isSliding = false;
			}
			else{
//				û�л���������˵���ǵ����¼�
				if(tstate==ToggleState.Open){
//					�������λ�����ߣ�
					tstate = ToggleState.Close;
					if(listener!=null){
						listener.onToggleButtonStateChange(tstate);
						System.out.println("�ر�2");
					}
				}
				else{
					tstate = ToggleState.Open;
					if(listener!=null){
						listener.onToggleButtonStateChange(tstate);
						System.out.println("����2");
					}
				}
//				Toast.makeText(getContext(), "�����¼�", 0).show();
			}
			
			break;

		default:
			break;
		}
		invalidate();
		return true;
	}
	
	
	
//	���ýӿڹ������������
	
	
	public void setOnToggleButtonChangeListener(OnToggleButtonChangeListener listener){
		this.listener = listener;
	}
	public interface OnToggleButtonChangeListener{
		void onToggleButtonStateChange(ToggleState state);
	}
}
