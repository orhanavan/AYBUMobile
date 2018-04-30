package com.aybumobile.Tabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aybumobile.R;

import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FoodListFragment extends Fragment {

    private TextView date, food1, food2, food3, food4;
    private ProgressBar progressBar;
    private LinearLayout foodListView;

    public FoodListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        date = view.findViewById(R.id.date);
        food1 = view.findViewById(R.id.food1);
        food2 = view.findViewById(R.id.food2);
        food3 = view.findViewById(R.id.food3);
        food4 = view.findViewById(R.id.food4);
        progressBar = view.findViewById(R.id.progressBar);
        foodListView = view.findViewById(R.id.food_listview);
        foodListView.setVisibility(View.INVISIBLE);

        new connect().execute();

        return view;
    }



    public class connect extends AsyncTask<Void, Void, Void> {

        List<String> info = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup.connect("http://ybu.edu.tr/sks/").get();
                Elements elements = document.select("font");
                info.add(elements.get(0).text());
                info.add(elements.get(1).text());
                info.add(elements.get(2).text());
                info.add(elements.get(3).text());
                info.add(elements.get(4).text());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            date.setText(info.get(0));
            food1.setText(WordUtils.capitalize(info.get(1).toLowerCase()));
            food2.setText(WordUtils.capitalize(info.get(2).toLowerCase()));
            food3.setText(WordUtils.capitalize(info.get(3).toLowerCase()));
            food4.setText(WordUtils.capitalize(info.get(4).toLowerCase()));
            progressBar.setVisibility(View.INVISIBLE);
            foodListView.setVisibility(View.VISIBLE);
        }

    }

}