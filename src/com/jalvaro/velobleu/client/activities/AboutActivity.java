package com.jalvaro.velobleu.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jalvaro.velobleu.client.R;
import com.jalvaro.velobleu.client.utils.AndroidUtils;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		ImageView imgLinkedin = (ImageView)findViewById(R.id.about_linkedin_imageView);
		imgLinkedin.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse(getString(R.string.text_about_linkedin_url)));
		        startActivity(intent);
		    }
		});
		
		ImageView imgGmail = (ImageView)findViewById(R.id.about_gmail_imageView);
		imgGmail.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		    	Intent i = new Intent(Intent.ACTION_SEND);
		    	i.setType("message/rfc822");
		    	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.text_about_gmail_url)});
		    	try {
		    	    startActivity(Intent.createChooser(i, ""));
		    	} catch (android.content.ActivityNotFoundException ex) {
		    		ex.printStackTrace();
		    	}
		    }
		});
		
		TextView aboutIcons = (TextView) findViewById(R.id.about_icons_textView);
		aboutIcons.setText(R.string.text_about_icons);
		AndroidUtils.addLink(aboutIcons, getString(R.string.text_about_vicing), getString(R.string.text_about_vicing_url));
	}

}
