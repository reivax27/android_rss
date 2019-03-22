package com.example.assignment7_rss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class NBAFeed extends AppCompatActivity {

    private ListView listView;
//    private ArrayList<String> titleList;
//    private ArrayList<String> linkList;
//    private ArrayList<String> descriptionList;
//    private ArrayList<String> pubDateList;
    private ArrayList<ListItem> listItems;
    //private ArrayAdapter<String> adapter;
    private NbaAdapter nbaAdapter;
    private TextView tvTopText, tvBottomText;
    private String feed, titleColor, dateColor;
    private ImageView imageView;
    private RSSTask2 rssTask;
    private int selectedItem, textSize;
    private SharedPreferences sharedPref;
    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nbafeed);
        sharedPref = getSharedPreferences("RSS_Shared_Pref", MODE_PRIVATE);

        //if(sharedPref.contains("textSize") && sharedPref.contains("titleColor") && sharedPref.contains("dateColor") && sharedPref.contains("selectedItem")) {
            textSize = sharedPref.getInt("textSize", 7);
            titleColor = sharedPref.getString("titleColor", "#000000");
            dateColor = sharedPref.getString("dateColor", "#000000");
            selectedItem = sharedPref.getInt("selectedItem", 10);
        //}
        //else {
            //default values

        //}

//        titleList = getIntent().getExtras().getStringArrayList("title");
//        linkList = getIntent().getExtras().getStringArrayList("link");
//        descriptionList = getIntent().getExtras().getStringArrayList("description");
//        pubDateList = getIntent().getExtras().getStringArrayList("pubDate");

        if (getIntent().getExtras() != null) {
            feed = getIntent().getStringExtra("feed");
        }
        else {
            feed = "knicks";
        }
        //textSize = 12;
        //feed = "knicks";
        //imageView = findViewById(R.id.imgLogo);
        rssTask = new RSSTask2();
        rssTask.execute();


        //populates the ListItem object
        //populateList(titleList, linkList, descriptionList, pubDateList);
        //listItems.size();
        //String someTitle = listItems.get(0).getTitle();
        //Log.d("xavier", someTitle);
//        nbaAdapter = new NbaAdapter(this, R.layout.list_item, listItems);
//        listView = findViewById(R.id.lvNBAFeed);
//        listView.setAdapter(nbaAdapter);

        /*
        //the item in the ListView will respond to the clicked item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem list = listItems.get(position);
                Intent intent = new Intent(NBAFeed.this, SelectedItemActivity.class);
                //intent.putExtra("selectedItem", position);
//                intent.putExtra("title", titleList.get(position));
//                intent.putExtra("link", linkList.get(position));
//                intent.putExtra("description", descriptionList.get(position));
//                intent.putExtra("pubDate", pubDateList.get(position));

                intent.putExtra("title", list.getTitle());
                intent.putExtra("link", list.getLink());
                intent.putExtra("description", list.getDescription());
                intent.putExtra("pubDate", list.getPubDate());
                startActivity(intent);

            }
        }); */




//        Log.d("xavier", "TITLES!!!!");
//        for(int i = 0; i < titleList.size(); i++) {
//            Log.d("xavier", titleList.get(i));
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rss_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = getIntent();
        switch (item.getItemId()) {
            case R.id.knicks_feed:
                intent.putExtra("feed", "knicks");
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();;
                this.overridePendingTransition(0,0);
                startActivity(intent);
                this.overridePendingTransition(0,0);
                return true;
            case R.id.raptors_feed:
                intent.putExtra("feed", "raptors");
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();;
                this.overridePendingTransition(0,0);
                startActivity(intent);
                this.overridePendingTransition(0,0);
                return true;
            case R.id.refresh:
                finish();
                this.overridePendingTransition(0,0);
//                SharedPreferences.Editor spEdit = sharedPref.edit();
//                spEdit.clear();
//                spEdit.commit();
                startActivity(intent);
                Log.d("refresh", "clicked refresh");
                this.overridePendingTransition(0,0);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(this, MainActivity.class);
                startActivityForResult(settingsIntent, REQUEST_CODE);
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }

    }

    /*
    @Override
    protected void onResume() {
        //SharedPreferences sharedPref = getSharedPreferences("RSS_Shared_Pref", MODE_PRIVATE);
        textSize = sharedPref.getInt("textSize", 0);
        textColor = sharedPref.getString("textColor", "Black");
        super.onResume();
    } */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           Log.d("onback", "backtoactivity");
        if(resultCode == RESULT_OK) {
            Log.d("onback", "backtoactivityresultok");
            textSize = data.getIntExtra("textSize", 7);
            titleColor = data.getStringExtra("titleColor");
            dateColor = data.getStringExtra("dateColor");
            selectedItem = data.getIntExtra("selectedItem", 10);
            rssTask = new RSSTask2();
            rssTask.execute();
        }

    }

    //    private class NbaAdapter extends ArrayAdapter<NbaNews> {
//
//        private ArrayList<NbaNews> items;
//
//        public NbaAdapter(Context context, int textViewResourceId, ArrayList<NbaNews> items) {
//            super(context, textViewResourceId, items);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = convertView;
//
//            if(view == null) {
//                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = vi.inflate(R.layout.list_item, null);
//            }
//
//            NbaNews n = items.get(position);
//            if (n != null) {
//                tvTopText = view.findViewById(R.id.tvTopText);
//                tvBottomText = view.findViewById(R.id.tvBottomText);
//
//                if (tvTopText != null) {
//                    tvTopText.setText("Title:" + n.getTitle());
//                }
//
//            }
//            return view;
//            //return super.getView(position, convertView, parent);
//        }
//    }

    class RSSTask2 extends AsyncTask<Void, Void, Void> {
        //private final String KNICKS_URL = "https://www.nba.com/knicks/rss.xml";
        private String rssURL;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = null;

            if(feed.equals("knicks")) {
                rssURL = "https://www.nba.com/knicks/rss.xml";
            }
            else if(feed.equals("raptors")) {
                rssURL = "https://www.nba.com/raptors/rss.xml";
            }

            try {
                saxParser = saxParserFactory.newSAXParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            URL url = null;
            try {
                url = new URL(rssURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            InputStream inputStream = null;
            try {
                inputStream = url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            KnicksHandler nbaHandler = new KnicksHandler();
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
            nbaAdapter = new NbaAdapter(NBAFeed.this, R.layout.list_item, listItems);
            listView = findViewById(R.id.lvNBAFeed);
            listView.setAdapter(nbaAdapter);



            //the item in the ListView will respond to the clicked item
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListItem list = listItems.get(position);
                    Intent intent = new Intent(NBAFeed.this, SelectedItemActivity.class);
                    //intent.putExtra("selectedItem", position);
    //                intent.putExtra("title", titleList.get(position));
    //                intent.putExtra("link", linkList.get(position));
    //                intent.putExtra("description", descriptionList.get(position));
    //                intent.putExtra("pubDate", pubDateList.get(position));

                    intent.putExtra("title", list.getTitle());
                    intent.putExtra("link", list.getLink());
                    intent.putExtra("description", list.getDescription());
                    intent.putExtra("pubDate", list.getPubDate());
                    startActivity(intent);

                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    ListItem list = listItems.get(position);
                    Toast.makeText(NBAFeed.this, list.getTitle(), Toast.LENGTH_LONG).show();
                    return true;
                }
            });

            super.onPostExecute(aVoid);
        }


    }

    class KnicksHandler extends DefaultHandler {

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

            populateList(title, link, description, pubDate);
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
    }

    private class NbaAdapter extends ArrayAdapter<ListItem> {

        //private ArrayList<String> listItem;

        //public NbaAdapter(Context context, int textViewResourceId, ArrayList<String> titleList) {
        public NbaAdapter(Context context, int textViewResourceId, ArrayList<ListItem> listItems) {
            super(context, textViewResourceId, listItems);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.list_item, null);
            }

            final ListItem li = listItems.get(position);
            if (li != null) {
                tvTopText = view.findViewById(R.id.tvTopText);
                tvBottomText = view.findViewById(R.id.tvBottomText);
                imageView = view.findViewById(R.id.imgLogo);

                if (tvTopText != null) {
                    tvTopText.setText("Title: " + li.getTitle());
                    tvTopText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                    tvTopText.setTextColor(Color.parseColor(titleColor));
                }

                if (tvBottomText != null) {
                    tvBottomText.setText("Date: " + li.getPubDate());
                    tvBottomText.setTextColor(Color.parseColor(dateColor));
                }

                if(feed.equals("knicks")) {
                    imageView.setImageResource(R.drawable.knicks);
                }
                else if(feed.equals("raptors")) {
                    imageView.setImageResource(R.drawable.raptors);
                }

            }
            return view;

            //return super.getView(position, convertView, parent);
        }
}

    private void populateList(ArrayList<String> titleList, ArrayList<String> linkList, ArrayList<String> descriptionList, ArrayList<String> pubDateList) {
        listItems = new ArrayList<ListItem>();

        for(int i = 0; i < selectedItem; i++) {
            listItems.add(new ListItem(titleList.get(i), linkList.get(i), descriptionList.get(i),pubDateList.get(i)));
        }
    }

    class ListItem {
        private String title;
        private String link;
        private String description;
        private String pubDate;

        public ListItem(String title, String link, String description, String pubDate) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.pubDate = pubDate;
        }

        public String getTitle() { return title; }
        public String getLink() { return link; }
        public String getDescription() { return description ;}
        public String getPubDate() { return pubDate; }
    }
}
