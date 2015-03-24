package afzkl.development.colorpickerview.demo;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AboutDialog extends AlertDialog {

	private ImageView			mIconView;
	private TextView			mAppNameText;
	private TextView			mAboutText;
	private TextView			mVersionText;
	
	
	public AboutDialog(Context context) {
		super(context);
			
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_about, null);
		
		mAboutText = (TextView) layout.findViewById(android.R.id.text2);
		mVersionText = (TextView) layout.findViewById(android.R.id.text1);
		mAppNameText = (TextView) layout.findViewById(android.R.id.title);
		mIconView = (ImageView) layout.findViewById(android.R.id.icon);
		
		setView(layout);
		
		loadAbout();
		
		setTitle("About");
		
		
		
		mIconView.setOnClickListener(new View.OnClickListener() {
			
			int mClickCount = 0;
			
			@Override
			public void onClick(View v) {
				mClickCount++;
				
				if(mClickCount == 5) {
					Toast.makeText(getContext(), "Upgraded to Pro Version!", Toast.LENGTH_SHORT).show();
					
					new Thread(new Runnable() {

						@Override
						public void run() {
							SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();							
							edit.putBoolean("is_pro", true);
							edit.commit();
						}
						
					}).start();
					
					
				}
				
			}
		});
		
		
		
		setButton(DialogInterface.BUTTON_POSITIVE, getContext().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
	}
	
	private void loadAbout(){
		
		PackageInfo pi = null;
		try {
			pi = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
        
		mAppNameText.setText("ColorPickerView");	
		mVersionText.setText("Version" + " " + (pi != null ? pi.versionName : "null"));
			
		String s = "<b>Developed By:</b><br>Daniel Nilsson<br>";		
		mAboutText.setText(Html.fromHtml(s));
	
	}
	
}
