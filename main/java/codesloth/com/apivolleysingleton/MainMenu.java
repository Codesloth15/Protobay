package codesloth.com.apivolleysingleton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import codesloth.com.apivolleysingleton.ImagePackage.ImageList;

public class MainMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SearchView searchView;
    Spinner category;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        String[] topic = {"", "foods", "technology", "nature", "animals"};
        category = findViewById(R.id.category);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainMenu.this, android.R.layout.simple_list_item_1, topic);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(arrayAdapter);
        category.setOnItemSelectedListener(this);
        searchView = findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchData = searchView.getQuery().toString();
                Intent intent = new Intent(MainMenu.this, ImageList.class);
                intent.putExtra("search", searchData);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        searchResult(adapterView, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    public void searchResult(AdapterView<?> adapterView, int position) {

        String value = adapterView.getItemAtPosition(position).toString();
        if (!value.equalsIgnoreCase("")) {
            if (!value.equals("")) {
                Intent intent = new Intent(MainMenu.this, ImageList.class);
                intent.putExtra("search", value);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}