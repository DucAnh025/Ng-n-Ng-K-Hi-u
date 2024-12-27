package com.example.nasaclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nasaclient.databinding.ActivityNewsBinding;
import com.example.nasaclient.databinding.ActivityWithNavBinding;
import com.example.nasaclient.databinding.ItemNewsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class NewsActivity extends FragmentActivity {
    ActivityWithNavBinding binding;
    private ActivityNewsBinding newsBinding;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWithNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsBinding = ActivityNewsBinding.inflate(getLayoutInflater(), binding.container, true);

        recyclerView = newsBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsAdapter = new NewsAdapter();
        recyclerView.setAdapter(newsAdapter);

        fetchNewsData();

    }

    private void fetchNewsData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://boxgateway.kozow.com/api/news")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("NewsActivity", "API request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        List<NewsItem> newsItems = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String date = jsonObject.getString("date");
                            String imageUrl = jsonObject.getString("image_url");
                            String source = jsonObject.getString("source");
                            String link = jsonObject.getString("link");
                            newsItems.add(new NewsItem(title, date, imageUrl, source, link));
                        }
                        runOnUiThread(() -> newsAdapter.setNewsItems(newsItems));
                    } catch (JSONException e) {
                        Log.e("NewsActivity", "JSON parsing error: " + e.getMessage());
                    }
                } else {
                    Log.e("NewsActivity", "API request unsuccessful: " + response.code());
                }
            }
        });
    }

    private static class NewsItem {
        String title;
        String date;
        String imageUrl;
        String source;
        String link;

        public NewsItem(String title, String date, String imageUrl, String source, String link) {
            this.title = title;
            this.date = date;
            this.imageUrl = imageUrl;
            this.source = source;
            this.link = link;
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
        private List<NewsItem> newsItems = new ArrayList<>();

        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemNewsBinding itemBinding = ItemNewsBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false
            );
            return new NewsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {
            NewsItem newsItem = newsItems.get(position);
            holder.bind(newsItem);
        }

        @Override
        public int getItemCount() {
            return newsItems.size();
        }

        public void setNewsItems(List<NewsItem> newsItems) {
            this.newsItems = newsItems;
            notifyDataSetChanged();
        }
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemNewsBinding itemBinding;
        private String newsUrl;

        public NewsViewHolder(ItemNewsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(NewsItem newsItem) {
            itemBinding.titleTextView.setText(newsItem.title);
            itemBinding.dateTextView.setText(newsItem.date); // Display the date
            Glide.with(NewsActivity.this).load(newsItem.imageUrl).into(itemBinding.imageView);
            this.newsUrl = newsItem.link;
        }

        @Override
        public void onClick(View v) {
            if (newsUrl != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl));
                startActivity(intent);
            }
        }
    }
}
