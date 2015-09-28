package nl.paulus.nfctagwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import nl.paulus.nfctagwriter.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity to write NFC tags with own mimetype and ID
 * Based on the excellent tutorial by Jesse Chen
 * http://www.jessechen.net/blog/how-to-nfc-on-the-android-platform/
 */
public class MainActivity extends AppCompatActivity {

	boolean mWriteMode = false;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mNfcPendingIntent;
	String OutString = new String();
	int index;
	String merchants[] = {"DMart","ShoppersStop","Amoeba","Four Points","Reliance"};
	String number[] = {"12356", "75856","98698","76798","97976"};
	Spinner spinner;
	String text = new String();
	String rvalue;
	ListView listView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



		// Selection of the spinner
		spinner = (Spinner) findViewById(R.id.spinner);



		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, merchants);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		listView1 = (ListView) findViewById(R.id.listView);


		((Button) findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mNfcAdapter = NfcAdapter.getDefaultAdapter(MainActivity.this);
				mNfcPendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
						new Intent(MainActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
				enableTagReadMode();

				new AlertDialog.Builder(MainActivity.this).setTitle("Tap to Check all accounts")
						.setOnCancelListener(new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								disableTagWriteMode();
							}

						}).create().show();
			}
		});


		((Button) findViewById(R.id.button)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mNfcAdapter = NfcAdapter.getDefaultAdapter(MainActivity.this);
				mNfcPendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
						new Intent(MainActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
				text = spinner.getSelectedItem().toString();
				index = getIndex(merchants, text);
				OutString += merchants[index] + ",";
				System.out.println("Selected string is" + index);

				OutString += ((TextView) findViewById(R.id.value)).getText().toString() + ":";
				enableTagWriteMode();

				new AlertDialog.Builder(MainActivity.this).setTitle("Touch to add "+merchants[index] +" Account")
						.setOnCancelListener(new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								disableTagWriteMode();
							}

						}).create().show();
			}
		});

	}
	
	private void enableTagWriteMode() {
	    mWriteMode = true;
	    IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
	    IntentFilter[] mWriteTagFilters = new IntentFilter[] { tagDetected };
	    mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);		
	}
	private void enableTagReadMode() {
		mWriteMode = false;
		IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter[] mWriteTagFilters = new IntentFilter[]{tagDetected};
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
	}

	private void disableTagWriteMode() {
	    mWriteMode = false;
		mNfcAdapter.disableForegroundDispatch(this);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    // Tag writing mode
	    if (mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
	        Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	       // NdefRecord record = NdefRecord.createMime(((TextView) findViewById(R.id.mime)).getText().toString(), ((TextView) findViewById(R.id.value)).getText().toString().getBytes());//NdefMessage message = new NdefMessage(new NdefRecord[] { record });

			NdefRecord addmessage = NdefRecord.createMime(OutString,(OutString).toString().getBytes());
			NdefMessage Newmessage = new NdefMessage(new NdefRecord[] { addmessage });



	        if (writeTag(Newmessage, detectedTag)) {
	            Toast.makeText(this, "Success: Wrote placeid to nfc tag", Toast.LENGTH_LONG)
	                .show();
	        }

			}
		else{
			Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			rvalue = readTag(detectedTag);
			ArrayList<String> your_array_list = new ArrayList<String>();
//            your_array_list.add("Test Object");
			System.out.println("Read value is :" + rvalue);
			for (String retval: rvalue.split(":")){
				your_array_list.add(retval.split(",")[0]);
				System.out.println("List value" + retval);
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					this,
					android.R.layout.simple_list_item_1,
					your_array_list);

			listView1.setAdapter(arrayAdapter);
            listView1.setLongClickable(true);
			listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
											   final int arg2, long arg3) {
                    Intent intent2 = new Intent(MainActivity.this, Offers.class);
                    startActivity(intent2);
                    return true;
				}
			});

//			TextView myAwesomeTextView = (TextView)findViewById(R.id.textView2);

//in your OnCreate() method
//s			myAwesomeTextView.setText(rvalue);
	    }
	}

	/*
	* Writes an NdefMessage to a NFC tag
	*/
	public boolean writeTag(NdefMessage message, Tag tag) {
	    int size = message.toByteArray().length;
	    try {
	        Ndef ndef = Ndef.get(tag);
	        if (ndef != null) {
	            ndef.connect();
	            if (!ndef.isWritable()) {
					Toast.makeText(getApplicationContext(),
					"Error: tag not writable",
					Toast.LENGTH_SHORT).show();
	                return false;
	            }
	            if (ndef.getMaxSize() < size) {
					Toast.makeText(getApplicationContext(),
					"Error: tag too small",
					Toast.LENGTH_SHORT).show();
	                return false;
	            }
	            ndef.writeNdefMessage(message);
	            return true;
	        } else {
	            NdefFormatable format = NdefFormatable.get(tag);
	            if (format != null) {
	                try {
	                    format.connect();
	                    format.format(message);
	                    return true;
	                } catch (IOException e) {
	                    return false;
	                }
	            } else {
	                return false;
	            }
	        }
	    } catch (Exception e) {
	        return false;
	    }
	}

	public String readTag(Tag tag) {
		//int size = message.toByteArray().length;
		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();
				if (!ndef.isWritable()) {
					Toast.makeText(getApplicationContext(),
							"Error: tag not writable",
							Toast.LENGTH_SHORT).show();
					return "-1";
				}

				NdefRecord[] v = ndef.getNdefMessage().getRecords();
				System.out.println("Size is" + v.length);
				for(int j=0; j<v.length;j++){
					System.out.println("Curent :"+ v[j].toMimeType().toString());
				}
				return v[0].toMimeType().toString();
			} else {
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						//format.format(message);
						return "-2";
					} catch (IOException e) {
						return "-1";
					}
				} else {
					return "-2";
				}
			}
		} catch (Exception e) {
			return "-1";
		}
	}

	public int getIndex(String[] a,String s){
		for(int i=0;i<a.length;i++){
			if(a[i].compareTo(s)==0)
				return i;
		}
		return -1;
	}
}
