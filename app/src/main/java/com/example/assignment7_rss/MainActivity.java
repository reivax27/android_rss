package com.example.assignment7_rss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    //private Button btnKnicksFeed, btnRaptorsFeed;
    private Spinner spTitleColor, spDateColor;
    private RadioGroup rgItems;
    private RadioButton rad3Items, rad5Items, rad10Items;
    private EditText etTextSize;
    private SharedPreferences sharedPreferences;
    private int textSize, spinnerTitlePosition, spinnerDatePoisition, selectedItem, selectedRadio;
    private String titleColor, dateColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("RSS_Shared_Pref", MODE_PRIVATE);
        spTitleColor = findViewById(R.id.spTitleColor);
        spDateColor = findViewById(R.id.spDateColor);
        etTextSize = findViewById(R.id.etTextSize);
        rgItems = findViewById(R.id.rgItems);
        rad3Items = findViewById(R.id.rad3Items);
        rad5Items = findViewById(R.id.rad5Items);
        rad10Items = findViewById(R.id.rad10Items);

        if(sharedPreferences.contains("selectedItem")) {
            selectedRadio = sharedPreferences.getInt("selectedItem", 10);
            if(selectedRadio == 3){
                rgItems.check(rad3Items.getId());
            }
            else if(selectedRadio == 5) {
                rgItems.check(rad5Items.getId());
            }
            else if(selectedRadio == 10) {
                rgItems.check(rad10Items.getId());
            }
        }


//        rgItems.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch(checkedId) {
//                    case R.id.rad3Items:
//                        selectedItem = Integer.valueOf(rad3Items.getText().toString());
//                        Log.d("radio", rad3Items.getText().toString());
//                        break;
//                    case R.id.rad5Items:
//                        selectedItem = Integer.valueOf(rad5Items.getText().toString());
//                        Log.d("radio", rad5Items.getText().toString());
//                        break;
//                    case R.id.rad10Items:
//                        selectedItem = Integer.valueOf(rad10Items.getText().toString());
//                        Log.d("radio", rad10Items.getText().toString());
//                        break;
//
//                }
//            }
//        });


        // creates the ArrayAdapter using the string array and a default layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.textColor_spinner, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when dropdown is selected
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply adapter to spinner
        spTitleColor.setAdapter(adapter);
        spDateColor.setAdapter(adapter);


        spTitleColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        titleColor = "#000000";
                        spinnerTitlePosition = position;
                        break;
                    case 1:
                        titleColor = "#FF0000";
                        spinnerTitlePosition = position;
                        break;
                    case 2:
                        titleColor = "#008000";
                        spinnerTitlePosition = position;
                        break;
                    case 3:
                        titleColor = "#0000FF";
                        spinnerTitlePosition = position;
                        break;
                    case 4:
                        titleColor = "#FFFF00";
                        spinnerTitlePosition = position;
                        break;
                    case 5:
                        titleColor = "#FFC0CB";
                        spinnerTitlePosition = position;
                        break;
                    case 6:
                        titleColor = "#FFFFFF";
                        spinnerTitlePosition = position;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDateColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        dateColor = "#000000";
                        spinnerDatePoisition = position;
                        break;
                    case 1:
                        dateColor = "#FF0000";
                        spinnerDatePoisition = position;
                        break;
                    case 2:
                        dateColor = "#008000";
                        spinnerDatePoisition = position;
                        break;
                    case 3:
                        dateColor = "#0000FF";
                        spinnerDatePoisition = position;
                        break;
                    case 4:
                        dateColor = "#FFFF00";
                        spinnerDatePoisition = position;
                        break;
                    case 5:
                        dateColor = "#FFC0CB";
                        spinnerDatePoisition = position;
                        break;
                    case 6:
                        dateColor = "#FFFFFF";
                        spinnerDatePoisition = position;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etTextSize.setText(String.valueOf(sharedPreferences.getInt("textSize",0)));
        spTitleColor.setSelection(sharedPreferences.getInt("spinnerTitlePosition", 0));
        spDateColor.setSelection(sharedPreferences.getInt("spinnerDatePosition", 0));


    }


    @Override
    public void onBackPressed() {

        Log.d("onback", "onbackpressed");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!etTextSize.getText().toString().isEmpty()) {
            textSize = Integer.valueOf(etTextSize.getText().toString());
            editor.putInt("textSize", textSize);
        } else
        {
            editor.putInt("textSize", 10);
        }

        if(rad3Items.isChecked()){
            selectedItem = Integer.valueOf(rad3Items.getText().toString());
        }
        else if(rad5Items.isChecked()) {
            selectedItem = Integer.valueOf(rad5Items.getText().toString());
        }
        else if(rad10Items.isChecked()) {
            selectedItem = Integer.valueOf(rad10Items.getText().toString());
        }

        editor.putString("titleColor", titleColor);
        editor.putString("dateColor", dateColor);
        editor.putInt("spinnerTitlePosition", spinnerTitlePosition);
        editor.putInt("spinnerDatePosition", spinnerDatePoisition);
        editor.putInt("selectedItem", selectedItem);
        editor.commit();

        if(sharedPreferences.contains("textSize") && sharedPreferences.contains("titleColor") && sharedPreferences.contains("dateColor") && sharedPreferences.contains("selectedItem")) {
            Log.d("onback", "onbackpressed");
            Intent intent = new Intent();
            intent.putExtra("textSize", sharedPreferences.getInt("textSize",7));
            intent.putExtra("titleColor", sharedPreferences.getString("titleColor", "#000000"));
            intent.putExtra("dateColor", sharedPreferences.getString("dateColor", "#000000"));
            intent.putExtra("selectedItem", sharedPreferences.getInt("selectedItem", 10));

            setResult(RESULT_OK, intent);
        }

        super.onBackPressed();
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
//                    TaskStackBuilder.create(this)
//                            // Add all of this activity's parents to the back stack
//                            .addNextIntentWithParentStack(upIntent)
//                            // Navigate up to the closest parent
//                            .startActivities();
                    upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
//                Intent intent = new Intent(this, NBAFeed.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    } */

    /*
    class EventHandler implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnKnicksFeed) {
                Intent intent = new Intent(MainActivity.this, NBAFeed.class);
                //intent.putExtra("feed", "knicks");
                startActivity(intent);
            } else if(v.getId() == R.id.btnRaptorsFeed) {
                Intent intent = new Intent(MainActivity.this, NBAFeed.class);
                //intent.putExtra("feed", "raptors");
                startActivity(intent);
            }
        }
    } */


    /*
    class RSSTask extends AsyncTask<Void, Void, Void> {
        private final String NBA_URL = "https://www.nba.com/knicks/rss.xml";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = null;

            try {
                saxParser = saxParserFactory.newSAXParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            URL url = null;
            try {
                url = new URL(NBA_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            InputStream inputStream = null;
            try {
                inputStream = url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            NBAHandler nbaHandler = new NBAHandler();
            try {
                saxParser.parse(inputStream, nbaHandler);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }


    } */


/*
    class NBAHandler extends DefaultHandler {

        //collect and store title, pubDate of the items passed in
        private ArrayList<String> title;
        private ArrayList<String> pubDate;
        private ArrayList<String> link;
        private ArrayList<String> description;


        private boolean inItem, inTitle, inPubDate, inLink, inDescription;

        private StringBuilder sbTitle, sbPubDate, sbLink, sbDescription;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();

            //creating the ArrayList
            title = new ArrayList<String>(30);
            pubDate = new ArrayList<String>(30);
            link = new ArrayList<String>(30);
            description = new ArrayList<String>(30);
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();

//            for (String object: title) {
//                Log.d("xavier", title.get(1));
//            }

//            Log.d("xavier", "TITLES!!!!");
//            for(int i = 0; i < title.size(); i++) {
//                Log.d("xavier", title.get(i));
//            }
            Intent intent = new Intent(MainActivity.this, NBAFeed.class);
            intent.putStringArrayListExtra("title", title);
            intent.putStringArrayListExtra("link", link);
            intent.putStringArrayListExtra("description", description);
            intent.putStringArrayListExtra("pubDate", pubDate);

            startActivity(intent);


//            Log.d("xavier", "PUBDATE!!!!");
//            for(int i = 0; i < pubDate.size(); i++) {
//                Log.d("xavier", pubDate.get(i));
//            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            //check if i'm in the current element of the rss feed
            if(qName.equals("item")) {
                inItem = true;
            }
            else if (inItem && qName.equals("title")) {
                inTitle = true;
                sbTitle = new StringBuilder(100);
            }
            else if(inItem && qName.equals("link")) {
                inLink = true;
                sbLink = new StringBuilder(100);
            }
            else if(inItem && qName.equals("description")) {
                inDescription = true;
                sbDescription = new StringBuilder(500);
            }
            else if(inItem && qName.equals("pubDate")) {
                inPubDate = true;
                sbPubDate = new StringBuilder(50);
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            //check if i'm in the current element of the rss feed
            if(qName.equals("item")) {
                inItem = false;
            }
            else if(inItem && qName.equals("title")) {
                inTitle = false;
                title.add(sbTitle.toString());
            }
            else if(inItem && qName.equals("link")) {
                inLink = false;
                link.add(sbLink.toString());
            }
            else if(inItem && qName.equals("description")) {
                inDescription = false;
                description.add(sbDescription.toString());
            }
            else if(inItem && qName.equals("pubDate")) {
                inPubDate = false;
                pubDate.add(sbPubDate.toString());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);

            if(inTitle) {
                sbTitle.append(ch, start, length);
            }
            else if(inLink) {
                sbLink.append(ch, start, length);
            }
            else if (inDescription) {
                sbDescription.append(ch, start, length);
            }
            else if (inPubDate) {
                sbPubDate.append(ch, start, length);
            }
        }

        @Override
        public void warning(SAXParseException e) throws SAXException {
            super.warning(e);
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            super.error(e);
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            super.fatalError(e);
        }
    } */

}
