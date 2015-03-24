package afzkl.development.colorpickerview.preference;

import afzkl.development.colorpickerview.R;
import afzkl.development.colorpickerview.view.ColorPanelView;
import afzkl.development.colorpickerview.view.ColorPickerView;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class ColorPickerPreference extends DialogPreference implements ColorPickerView.OnColorChangedListener{


	private ColorPickerView				mColorPickerView;
	private ColorPanelView				mOldColorView;
	private ColorPanelView				mNewColorView;

	private int							mColor;
	
	private boolean						alphaChannelVisible = false;
	private String						alphaChannelText = null;
	private boolean						showDialogTitle = false;
	private boolean						showPreviewSelectedColorInList = true;
	private int							colorPickerSliderColor = -1;
	private int							colorPickerBorderColor = -1;
	
	
	public ColorPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	public ColorPickerPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);		
		init(attrs); 
		
	}
	

	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerPreference);
		
		showDialogTitle = a.getBoolean(R.styleable.ColorPickerPreference_showDialogTitle, false);
		showPreviewSelectedColorInList = a.getBoolean(R.styleable.ColorPickerPreference_showSelectedColorInList, true);
		
		a.recycle();	
		a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerView);
		
		alphaChannelVisible = a.getBoolean(R.styleable.ColorPickerView_alphaChannelVisible, false);
		alphaChannelText = a.getString(R.styleable.ColorPickerView_alphaChannelText);		
		colorPickerSliderColor = a.getColor(R.styleable.ColorPickerView_colorPickerSliderColor, -1);
		colorPickerBorderColor = a.getColor(R.styleable.ColorPickerView_colorPickerBorderColor, -1);
		
		a.recycle();
		
		if(showPreviewSelectedColorInList) {
			setWidgetLayoutResource(R.layout.preference_preview_layout);
		}
		
		if(!showDialogTitle) {
			setDialogTitle(null);
		}
				
		setDialogLayoutResource(R.layout.dialog_color_picker);
		
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);		
		
		setPersistent(true);		
	}
		
	@Override
	protected Parcelable onSaveInstanceState() {
		 final Parcelable superState = super.onSaveInstanceState();

		 // Create instance of custom BaseSavedState
		 final SavedState myState = new SavedState(superState);
		 // Set the state's value with the class member that holds current setting value
		 
		 
		 if(getDialog() != null && mColorPickerView != null) {
			 myState.currentColor = mColorPickerView.getColor();
		 }
		 else {
			 myState.currentColor = 0;
		 }

		 return myState;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
	    // Check whether we saved the state in onSaveInstanceState
	    if (state == null || !state.getClass().equals(SavedState.class)) {
	        // Didn't save the state, so call superclass
	        super.onRestoreInstanceState(state);
	        return;
	    }

	    // Cast state to custom BaseSavedState and pass to superclass
	    SavedState myState = (SavedState) state;
	    super.onRestoreInstanceState(myState.getSuperState());
	    

	    // Set this Preference's widget to reflect the restored state
	    if(getDialog() != null && mColorPickerView != null) {
	    	Log.d("mColorPicker", "Restoring color!");	    	
	    	mColorPickerView.setColor(myState.currentColor, true);
	    }
	    
	   
	    
	}
	
	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		
		ColorPanelView preview = (ColorPanelView) view.findViewById(R.id.preference_preview_color_panel);
		
		if(preview != null) {
			preview.setColor(mColor);
		}
		
	}
	
	@Override
	protected void onBindDialogView(View layout) {
		super.onBindDialogView(layout);
		
		boolean isLandscapeLayout = false;
		
		mColorPickerView = (ColorPickerView) layout.findViewById(R.id.color_picker_view);
		
		LinearLayout landscapeLayout = (LinearLayout) layout.findViewById(R.id.dialog_color_picker_extra_layout_landscape);
		
		if(landscapeLayout != null) {
			isLandscapeLayout = true;
		}
		
				
		mColorPickerView = (ColorPickerView) layout
				.findViewById(R.id.color_picker_view);
		mOldColorView = (ColorPanelView) layout.findViewById(R.id.color_panel_old);
		mNewColorView = (ColorPanelView) layout.findViewById(R.id.color_panel_new);

		if(!isLandscapeLayout) {
			((LinearLayout) mOldColorView.getParent()).setPadding(Math
					.round(mColorPickerView.getDrawingOffset()), 0, Math
					.round(mColorPickerView.getDrawingOffset()), 0);
			
		}
		else {
			landscapeLayout.setPadding(0, 0, Math.round(mColorPickerView.getDrawingOffset()), 0);
		}

		mColorPickerView.setAlphaSliderVisible(alphaChannelVisible);
		mColorPickerView.setAlphaSliderText(alphaChannelText);		
		mColorPickerView.setSliderTrackerColor(colorPickerSliderColor);
		
		if(colorPickerSliderColor != -1) {
			mColorPickerView.setSliderTrackerColor(colorPickerSliderColor);
		}
		
		if(colorPickerBorderColor != -1) {
			mColorPickerView.setBorderColor(colorPickerBorderColor);
		}

		
		mColorPickerView.setOnColorChangedListener(this);
		
		//Log.d("mColorPicker", "setting initial color!");
		mOldColorView.setColor(mColor);
		mColorPickerView.setColor(mColor, true);
	}
	
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if(positiveResult) {
			mColor = mColorPickerView.getColor();
			persistInt(mColor);
			
			notifyChanged();
			
		}
	}
	
	@Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {		
		if(restorePersistedValue) {
			mColor = getPersistedInt(0xFF000000);
			//Log.d("mColorPicker", "Load saved color: " + mColor);
		}
		else {
			mColor = (Integer)defaultValue;
			persistInt(mColor);
		}
	}
	
	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return a.getInteger(index, 0xFF000000);
	}
	
	
	@Override
	public void onColorChanged(int newColor) {
		mNewColorView.setColor(newColor);
	}

	

	private static class SavedState extends BaseSavedState {
	    // Member that holds the setting's value
	    int currentColor;

	    public SavedState(Parcelable superState) {
	        super(superState);
	    }

	    public SavedState(Parcel source) {
	        super(source);
	        // Get the current preference's value
	        currentColor = source.readInt(); 
	    }

	    @Override
	    public void writeToParcel(Parcel dest, int flags) {
	        super.writeToParcel(dest, flags);
	        // Write the preference's value
	        dest.writeInt(currentColor);
	    }

	    // Standard creator object using an instance of this class
	    public static final Parcelable.Creator<SavedState> CREATOR =
	            new Parcelable.Creator<SavedState>() {

	        public SavedState createFromParcel(Parcel in) {
	            return new SavedState(in);
	        }

	        public SavedState[] newArray(int size) {
	            return new SavedState[size];
	        }
	    };
	}

}
