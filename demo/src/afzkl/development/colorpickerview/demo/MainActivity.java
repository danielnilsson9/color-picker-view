package afzkl.development.colorpickerview.demo;

import afzkl.development.colorpickerview.dialog.ColorPickerDialogFragment;
import afzkl.development.colorpickerview.dialog.ColorPickerDialogFragment.ColorPickerDialogListener;
import afzkl.development.colorpickerview.preference.ColorPreference;
import afzkl.development.colorpickerview.preference.ColorPreference.OnShowDialogListener;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity implements ColorPickerDialogListener{
	
	private static final int DIALOG_ID = 0;
	private static final int PREFERENCE_DIALOG_ID = 1;
	
	
	private ColorPreference mColorPickerPreference;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main);

		init();
	}

	private void init() {
		mColorPickerPreference = (ColorPreference) findPreference("color");
		mColorPickerPreference.setOnShowDialogListener(new OnShowDialogListener() {
			
			@Override
			public void onShowColorPickerDialog(String title, int currentColor) {				
				
				// Preference was clicked, we need to show the dialog.
				
				ColorPickerDialogFragment f = ColorPickerDialogFragment
						.newInstance(PREFERENCE_DIALOG_ID, "Color Picker", null, currentColor, false);
				
				f.setStyle(DialogFragment.STYLE_NORMAL, R.style.LightPickerDialogTheme);
				f.show(getFragmentManager(), "pre_dialog");
			}
		});
		
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
		
		// The color picker menu item has been clicked. Show 
		// a dialog using the custom ColorPickerDialogFragment class.
		
		ColorPickerDialogFragment f = ColorPickerDialogFragment
				.newInstance(DIALOG_ID, "Color Picker", null, -1, true);
		
		f.setStyle(DialogFragment.STYLE_NORMAL, R.style.LightPickerDialogTheme);
		//f.setTitle("Color Picker");
		f.show(getFragmentManager(), "d");

	}
	
	
	private String colorToHexString(int color) {
		return String.format("#%06X", 0xFFFFFFFF & color);
	}


	
	
	@Override
	public void onColorSelected(int dialogId, int color) {
		switch(dialogId) {
		case PREFERENCE_DIALOG_ID:
			// We got result back from preference picker dialog. We need to save it.
			mColorPickerPreference.saveValue(color);
			break;
		case DIALOG_ID:
			// We got result from the other dialog, the one that is
			// shown when clicking on the icon in the action bar.
			
			Toast.makeText(MainActivity.this, "Selected Color: " + colorToHexString(color), Toast.LENGTH_SHORT).show();
			break;
		}
		
		
	}

	@Override
	public void onDialogDismissed(int dialogId) {
		// nothing to do
	}

}
 