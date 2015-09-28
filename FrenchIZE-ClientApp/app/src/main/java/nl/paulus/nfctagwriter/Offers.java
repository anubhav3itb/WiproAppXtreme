package nl.paulus.nfctagwriter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Offers extends ActionBarActivity {

    ListView lv;
    String offers[] = {"Flat 15% off at Shoppers Stop Ltd for Citibank Card Holders", "Extra 20% off from Shoppers Stop Ltd on New Arrivals", "Flat 10% off from Shoppers Stop Ltd for Axis Bank Users", "Pay 50% less with daily deals at Shoppers Stop Ltd on FCUK Watches"};
    String locations[] = {"Forum Mall, 0.25 miles away", "Marathalli, 1.2 miles away", "M.G Road, 2 miles away"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        lv = (ListView) findViewById(R.id.lv1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                offers);

        lv.setAdapter(arrayAdapter);

        lv = (ListView) findViewById(R.id.lv2);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                locations);

        lv.setAdapter(arrayAdapter2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offers, menu);
        return true;
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
}
