package afzkl.development.colorpickerview.demo;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.menu_color_picker_dialog:
			onClickColorPickerDialog(item);
			return true;
		case R.id.menu_about:
			new AboutDialog(this).show();
			return true;
		}
		
		
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickColorPickerDialog(MenuItem item) {
		//The color picker menu item as been clicked. Show 
		//a dialog using the custom ColorPickerDialog class.
		
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int initialValue = prefs.getInt("color_2", 0xFF000000);
		
		Log.d("ColorPickerView", "Initial value:" + initialValue);
				
		final ColorPickerDialog colorDialog = new ColorPickerDialog(this, initialValue);
		
		colorDialog.setAlphaSliderVisible(true);
		colorDialog.setTitle("Pick a Color!");
		
		colorDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "Selected Color: " + colorToHexString(colorDialog.getColor()), Toast.LENGTH_LONG).show();
							
				//Save the value in our preferences.
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt("color_2", colorDialog.getColor());
				editor.commit();
			}
		});
		
		colorDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Nothing to do here.
			}
		});
		
		colorDialog.show();
	}
	
	
	private String colorToHexString(int color) {
		return String.format("#%06X", 0xFFFFFFFF & color);
	}

}
 