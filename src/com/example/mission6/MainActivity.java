package com.example.mission6;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	boolean isPageOpen = false;
	
	Animation translateDownAnim;
	Animation translateUpAnim;
	LinearLayout slidingPage01;
	LinearLayout slidingPage02;
	
	Button down;
	Button open;
	Button back;
	Button prev;
	EditText text;
	
	private WebView webView;
	private Handler mHandler = new Handler();
	String prevUrl;
	String backUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//------------------------------ 페이지 슬라이딩 ------------------------------//
		slidingPage01 = (LinearLayout)findViewById(R.id.slidingPage01);
		slidingPage02 = (LinearLayout)findViewById(R.id.slidingPage02);
		translateDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down);
		translateUpAnim = AnimationUtils.loadAnimation(this, R.anim.translate_up);
		
		SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
		translateDownAnim.setAnimationListener(animListener);
		translateUpAnim.setAnimationListener(animListener);
		
		down = (Button)findViewById(R.id.button1);
		down.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(isPageOpen){			// 페이지가 열려있으면 위로 슬라이드
					slidingPage01.startAnimation(translateUpAnim);
					slidingPage02.setTop(100);
				}
				else{		// 페이지가 열려있지 않으면 아래로 슬라이드
					slidingPage01.setVisibility(View.VISIBLE);
					slidingPage01.startAnimation(translateDownAnim);
					slidingPage02.setTop(200);
				}
			}
		});
		
		//----------------------------------- 웹뷰 -----------------------------------//
		webView = (WebView)findViewById(R.id.webView1);
		
		WebSettings webSettings = webView.getSettings();
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(false);
		webSettings.setSupportZoom(false);

		webView.setWebViewClient(new WebClient());
		
		text = (EditText)findViewById(R.id.editText1);
		open = (Button)findViewById(R.id.button2);
		open.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				webView.loadUrl(text.getText().toString());
				backUrl = text.getText().toString();
				prevUrl = text.getText().toString();
				slidingPage01.startAnimation(translateUpAnim);
			}
			
		});
		back = (Button)findViewById(R.id.button3);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				webView.loadUrl(backUrl);			
			}
			
		});
		prev = (Button)findViewById(R.id.button4);
		prev.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				webView.loadUrl(text.getText().toString());
			}
			
		});
	}

	//------------------------------ 페이지 슬라이딩 ------------------------------//
	private class SlidingPageAnimationListener implements AnimationListener{

		@Override
		public void onAnimationEnd(Animation arg0) {
			if(isPageOpen){
				slidingPage01.setVisibility(View.INVISIBLE);
				
				down.setText("↓");
				isPageOpen = false;
			}
			else{
				down.setText("↑");
				isPageOpen = true;
			}
		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//----------------------------------- 웹뷰 -----------------------------------//
	class WebClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view,String url){
			view.loadUrl(url);
			return true;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
