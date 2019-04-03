package com.eranpinhas.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ProcessAffiliationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle b = getIntent().getExtras();
        String youGet = "";
        String from = "";// or other values
        if (b != null) {
            youGet = b.getString("youGet");
            from = b.getString("from");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_affiliation);

        ((TextView) findViewById(R.id.youGotTextView)).setText(String.format("Awesome!\nYou got yourself %s at %s", youGet.toLowerCase(), from));
    }


    public void DoneProcessingAffiliation(View v) {
        finish();
    }
}
