package com.aybumobile.Tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.aybumobile.R;
import java.util.ArrayList;


public class AnnouncementsFragment extends Fragment {

    private ListView listView;
    private WebView web;
    final private ArrayList<String> announcementList = new ArrayList<>();
    final private ArrayList<String> linkList = new ArrayList<>();
    private ArrayAdapter adapter;
    private Button loadButton;
    private ProgressBar progressBar;
    private int page = 1;

    public AnnouncementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcements, container, false);
        listView = view.findViewById(R.id.list);
        web = view.findViewById(R.id.web);
        loadButton = view.findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String script = "javascript:__doPostBack('ctl00$ContentPlaceHolder1$Content_List1$Sayfalama','"+Integer.toString(++page)+"')";
                web.evaluateJavascript(script, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                    }
                });
            }
        });

        progressBar = view.findViewById(R.id.progressBar);
        adapter = new ArrayAdapter<>(getContext(),R.layout.list_item, R.id.list_title, announcementList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(linkList.get(i)));
                getActivity().startActivity(browser);
            }
        });
        listView.setAdapter(adapter);

        new connect_ann().execute();

        return view;
    }

    public class connect_ann extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            web.post(new Runnable() {
                @Override
                public void run() {
                    WebSettings faller = web.getSettings();
                    final String url = "http://ybu.edu.tr/muhendislik/bilgisayar/content_list-257-duyurular.html";
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

                    // announcements
                    view.evaluateJavascript("document.getElementsByTagName('h1')[1].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            announcementList.add(s.substring(77, s.length()-1));
                        }
                    });
                    view.evaluateJavascript("document.getElementsByTagName('h1')[2].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            announcementList.add(s.substring(77, s.length()-1));
                        }
                    });
                    view.evaluateJavascript("document.getElementsByTagName('h1')[3].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            announcementList.add(s.substring(77, s.length()-1));
                        }
                    });
                    view.evaluateJavascript("document.getElementsByTagName('h1')[4].textContent", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            announcementList.add(s.substring(77, s.length()-1));

                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                    // links
                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[0].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkList.add(s.substring(1, s.length()-1));
                        }
                    });

                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[1].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkList.add(s.substring(1, s.length()-1));
                        }
                    });

                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[2].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkList.add(s.substring(1, s.length()-1));
                        }
                    });

                    view.evaluateJavascript("document.getElementsByClassName('detailbuton')[3].href", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            linkList.add(s.substring(1, s.length()-1));
                        }
                    });

                }
            });
        }


    }



}