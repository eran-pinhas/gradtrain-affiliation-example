package com.eranpinhas.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

public class AffiliateChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate_choose);
        AffiliateLayoutTitle = findViewById(R.id.affiliationLayoutTitle);

        // For the title to the be over the list (although it is defined later)
        AffiliateLayoutTitle.bringToFront();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        List<AffiliateOption> options = FetchAffiliatesOptions();
        ListView listView = (ListView) findViewById(R.id.ListView);
        CustomAdapter customAdapter = new CustomAdapter(options);
        listView.setAdapter(customAdapter);

        final AffiliateChooseActivity currentActivity = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                AffiliateOption item = (AffiliateOption)adapter.getItemAtPosition(position);

                Intent intent = new Intent(currentActivity,ProcessAffiliationActivity.class);

                intent.putExtra("youGet", item.youGet);
                intent.putExtra("from", item.from);

                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                int height = 0;
                if (firstVisibleItem > 0) {
                    height = getResources().getDimensionPixelSize(R.dimen.affiliate_textbar_minsize);
                } else if (visibleItemCount == 0){
                    height = getResources().getDimensionPixelSize(R.dimen.affiliate_textbar_maxsize);
                } else {
                    height = Math.max(view.getChildAt(0).getTop(), getResources().getDimensionPixelSize(R.dimen.affiliate_textbar_minsize));
                }

                final ConstraintLayout.LayoutParams layoutparams = (ConstraintLayout.LayoutParams) AffiliateLayoutTitle.getLayoutParams();
                layoutparams.height = height;
                AffiliateLayoutTitle.setLayoutParams(layoutparams);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    class AffiliateOption {
        AffiliateOption(String youGet, String forWhat, String from, String because) {
            this.youGet = youGet;
            this.forWhat = forWhat;
            this.from = from;
            this.because = because;
        }

        public String youGet;
        public String forWhat;
        public String from;
        public String because;
    }

    List<AffiliateOption> FetchAffiliatesOptions() {

        LinkedList<AffiliateOption> result = new LinkedList<AffiliateOption>();
        try {
            JSONArray options = new JsonLoader(this).loadJSONFromAsset("affiliates.json").getJSONArray("data");

            for (int i = 0; i < options.length(); i++) {
                JSONArray details = options.getJSONArray(i);
                result.add(new AffiliateOption(
                        details.getString(0),
                        details.getString(1),
                        details.getString(2),
                        details.getString(3))
                );
            }

        } catch (JSONException e) {

        }
        return result;
    }

    TextView AffiliateLayoutTitle;

    class CustomAdapter extends BaseAdapter {
        List<AffiliateOption> options;

        CustomAdapter(List<AffiliateOption> options) {
            this.options = options;
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return options.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            AffiliateOption option = options.get(position);

            view = getLayoutInflater().inflate(R.layout.item_layout, null);

            ((TextView) view.findViewById(R.id.title)).setText(option.forWhat);
            ((TextView) view.findViewById(R.id.bullet)).setText(option.youGet);
            ((TextView) view.findViewById(R.id.from)).setText(option.from);
            ((TextView) view.findViewById(R.id.because)).setText(option.because);


            return view;
        }
    }
}
