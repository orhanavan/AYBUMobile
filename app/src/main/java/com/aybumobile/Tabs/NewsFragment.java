package com.aybumobile.Tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aybumobile.R;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    private ListView listView;
    private WebView web;
    final private ArrayList<String> newsList = new ArrayList<>();
    final private ArrayList<String> linkList = new ArrayList<>();
    private ArrayAdapter adapter;
    private Button loadButton;
    private ProgressBar progressBar;
    private int page = 1;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        listView = view.findViewById(R.id.news_list);
        web = view.findViewById(R.id.news_web);
        loadButton = view.findViewById(R.id.news_load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String script = "document.getElementById('ctl00$ContentPlaceHolder1$Content_List1$Sayfalama')";
                web.evaluateJavascript(script, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        if (s.equals("null"))
                            Toast.makeText(getContext(), "New content not found.", Toast.LENGTH_SHORT).show();
                        else{

                        }
                    }
                });
//          if there are new content, paste this code above if's else
//
//                progressBar.setVisibility(View.VISIBLE);
//                String script = "javascript:__doPostBack('ctl00$ContentPlaceHolder1$Content_List1$Sayfalama','"+Integer.toString(++page)+"')";
//                web.evaluateJavascript(script, new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String s) {
//                    }
//                });
            }
        });

        progressBar = view.findViewById(R.id.news_progressBar);
        adapter = new ArrayAdapter<>(getContext(),R.layout.list_item, R.id.list_title, newsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(linkList.get(i)));
                getActivity().startActivity(browser);
            }
        });

        listView.setAdapter(adapter);

        new connect_news().execute();

        return view;
    }

    public class connect_news extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            web.post(new Runnable() {
                @Override
                public void run() {
                    WebSettings faller = web.getSettings();
                    final String url = "http://ybu.edu.tr/muhendislik/bilgisayar/content_list-314-haberler.html";
                    faller.setJavaScriptEnabled(true);
                    web.loadUrl(url);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            web.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    view.evaluateJavascript("document.getElementsByTagName('h1')[1].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            stringControl(s);
                        }
                    });
                    view.evaluateJavascript("document.getElementsByTagName('h1')[2].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            stringControl(s);
                        }
                    });
                    view.evaluateJavascript("document.getElementsByTagName('h1')[3].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            stringControl(s);
                        }
                    });
                    view.evaluateJavascript("document.getElementsByTagName('h1')[4].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            stringControl(s);

                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                    // links
                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[0].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkControl(s);
                        }
                    });
                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[1].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkControl(s);
                        }
                    });
                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[2].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkControl(s);
                        }
                    });
                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[3].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkControl(s);
                        }
                    });
                }

            });
            super.onPostExecute(aVoid);
        }

        public void stringControl(String s){
            if(!s.equals("null"))
                newsList.add(s.substring(77, s.length()-1));
        }
        public void linkControl(String s){
            if(!s.equals("null"))
                linkList.add(s.substring(1, s.length()-1));
        }
    }

}