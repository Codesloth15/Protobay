package codesloth.com.apivolleysingleton.ImagePackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import codesloth.com.apivolleysingleton.R;
import codesloth.com.apivolleysingleton.Singleton;

public class ImageList extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    List<ImageListConstructor> itemListList;
    String search_query;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        initializingVariables();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
        onClick();
    }
    public  void onClick(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void initializingVariables(){
        requestQueue = Singleton.getInstance(this).getRequestQueue();
        recyclerView=findViewById(R.id.recyclerview);
        itemListList = new ArrayList<>();
        swipeRefreshLayout=findViewById(R.id.refresh);
        info=findViewById(R.id.info);

    }
    @SuppressLint("SetTextI18n")
    public  void fetchData(){
        Intent intent = getIntent();
        search_query=intent.getExtras().getString("search");
        info.setText("Select a " +search_query+ " image to download for free.High resolution picture downloads for your next project");
        String url = "https://pixabay.com/api/?key=30915098-84f02c72801cf740289cbb07e&q="+search_query+"&image_type=photo&pretty=true";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayRequest = response.getJSONArray("hits");
                    for(int i=0;i<jsonArrayRequest.length();i++){
                        JSONObject jsonObject = jsonArrayRequest.getJSONObject(i);
                        String image = jsonObject.getString("webformatURL");
                        String tags = jsonObject.getString("tags");
                        int likes =jsonObject.getInt("likes");
                        ImageListConstructor imageListConstructor = new ImageListConstructor(image,tags,likes);
                        itemListList.add(imageListConstructor);
                        ImageListAdapter imageListAdapter = new ImageListAdapter(ImageList.this,itemListList);
                        recyclerView.setAdapter(imageListAdapter);
                        imageListAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    Toast.makeText(ImageList.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ImageList.this,"network error",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}