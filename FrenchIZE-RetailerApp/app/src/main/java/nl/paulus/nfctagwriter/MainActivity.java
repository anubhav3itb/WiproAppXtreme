package nl.paulus.nfctagwriter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import nl.paulus.nfctagwriter.ServiceHandler;

import nl.paulus.nfctagwriter_Merchant.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.HttpsURLConnection;

/**
 * Activity to write NFC tags with own mimetype and ID
 * Based on the excellent tutorial by Jesse Chen
 * http://www.jessechen.net/blog/how-to-nfc-on-the-android-platform/
 */
public class MainActivity extends Activity {

    boolean mWriteMode = false;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;
    int index;
    String merchant_id = "amoeba";
    String text = new String();
    String rvalue;
    Button addData, readButton;
    EditText points;
    String rewardPoints;
    ProgressDialog pDialog;
    private final String USER_AGENT = "Mozilla/5.0";
    private  static  String URL= "http://192.168.181.50/Brainwaves/addTransactions.php?cid=119555&mid=58&age=21&sex=1&amount=576";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Selection of the spinner
    //    spinner = (Spinner) findViewById(R.id.spinner);

        addData = (Button) findViewById(R.id.button2);
        addData.setVisibility(View.INVISIBLE);
        points = (EditText) findViewById(R.id.editText);
        points.setVisibility(View.INVISIBLE);

        // Application of the Array to the Spinner

        readButton = ((Button) findViewById(R.id.button));
        readButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mNfcAdapter = NfcAdapter.getDefaultAdapter(MainActivity.this);
                mNfcPendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                        new Intent(MainActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                enableTagReadMode();

                Toast.makeText(getApplicationContext(), "Tap Card To the Device",
                        Toast.LENGTH_LONG).show();

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


        }
        else{
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            rvalue = readTag(detectedTag);
            ArrayList<String> your_array_list = new ArrayList<String>();
            System.out.println("Read value is :" + rvalue);
            for (String retval: rvalue.split(":")){
                your_array_list.add(retval);
                System.out.println("List value" + retval);
            }

            int save = getIndex(your_array_list, merchant_id);

            if (save != -1) {
                System.out.println("Success");
                readButton.setVisibility(View.INVISIBLE);
                addData.setVisibility(View.VISIBLE);
                //points = (EditText) findViewById(R.id.editText);
                points.setVisibility(View.VISIBLE);
                addData.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        rewardPoints = points.getText().toString();
                        URL= "http://192.168.181.50/Brainwaves/addTransactions.php?cid=119555&mid=58&age=21&sex=1&amount="+rewardPoints;
                        new Login().execute();

                        Toast.makeText(getApplicationContext(), "Reward Points has been updated",
                                Toast.LENGTH_LONG).show();

                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "Card Not Registered with "+merchant_id,
                        Toast.LENGTH_LONG).show();
            }
            //  listView1.setAdapter(arrayAdapter);

//            TextView myAwesomeTextView = (TextView)findViewById(R.id.textView2);

//in your OnCreate() method
  //          myAwesomeTextView.setText(rvalue);
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

    private void sendGet() throws Exception {

        String url = "http://192.168.181.50/Brainwaves/addTransactions.php?cid=119555&mid=58&age=21&sex=1&amount=576";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        //request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }


    public int getIndex(ArrayList<String> a,String s){
		for(int i=0;i<a.size();i++){
            System.out.println("Text:"+ a.get(i).split(",")[0]+s.compareTo(a.get(i).split(",")[0]));
			if(s.compareTo(a.get(i).split(",")[0])==0) {
                System.out.println("Text:" + a.get(i).split(",")[0] + s.compareTo(a.get(i).split(",")[0]));
                return i;
            }
		}
        System.out.println("Text:"+ a.get(0).split(",")[0]+s.compareTo(a.get(0).split(",")[0]));
		return -1;
	}


    private class Login extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Connecting To Server");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg) {

            // Preparing post params
            ServiceHandler serviceClient = new ServiceHandler();

            String json = serviceClient.makeServiceCall(URL,
                    ServiceHandler.GET);

            Log.d("Create Response: ", "> " + json);

            if (json != null) {


            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }}
