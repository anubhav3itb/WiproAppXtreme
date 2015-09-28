package nl.paulus.nfctagwriter;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class Merchant extends Activity {
    boolean mWriteMode = false;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;
    String rvalue;
    String merchant_name = "Amoeba";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        ((Button) findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mNfcAdapter = NfcAdapter.getDefaultAdapter(Merchant.this);
                mNfcPendingIntent = PendingIntent.getActivity(Merchant.this, 0,
                        new Intent(Merchant.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                enableTagReadMode();

                new AlertDialog.Builder(Merchant.this).setTitle("Touch tag to Verify  Ticket")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                disableTagWriteMode();
                            }

                        }).create().show();
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Tag writing mode
        if (!mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            rvalue = readTag(detectedTag);


            ArrayList<String> your_array_list = new ArrayList<String>();
            System.out.println("Read value is :" + rvalue);
            for (String retval: rvalue.split(":")){
                your_array_list.add(retval);
                System.out.println("List value"+ retval);
            }

            int save = getIndex(your_array_list, merchant_name);
            if (save != -1){
                System.out.print("Success");
            }

            //TextView myAwesomeTextView = (TextView)findViewById(R.id.textView2);

//in your OnCreate() method
            //myAwesomeTextView.setText(rvalue);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_merchant, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getIndex(ArrayList<String> a,String s){
        for(int i=0;i<a.size();i++){
            if(a.get(i).compareTo(s)==0)
                return i;
        }
        return -1;
    }
}
