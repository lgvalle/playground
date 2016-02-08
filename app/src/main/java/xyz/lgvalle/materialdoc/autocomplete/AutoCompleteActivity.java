package xyz.lgvalle.materialdoc.autocomplete;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import xyz.lgvalle.materialdoc.R;

public class AutoCompleteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        AutoCompleteTextView autocomplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_dogs);
        List<Dog> dogs = getListItems(10);
        AutoCompleteDogsAdapter autoCompleteAdapter = new AutoCompleteDogsAdapter(this, dogs);
        autocomplete.setAdapter(autoCompleteAdapter);




        /*
        AutoCompleteTextView autocomplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_dogs);
        List<String> dogs = Arrays.asList(getResources().getStringArray(R.array.dogs_list));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dogs);
        autocomplete.setAdapter(adapter);
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_autocomplete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Dog> getListItems(int size) {

        List<Dog> dogs = new ArrayList<>();

        dogs.add(new Dog("Bichon Frise", R.drawable.bichon_frise_110x110));
        dogs.add(new Dog("Border Collie", R.drawable.border_collie_110x110));
        dogs.add(new Dog("Border Terrier", R.drawable.border_terrier_110x110));
        dogs.add(new Dog("Boxer", R.drawable.boxer_110x110));
        dogs.add(new Dog("Chihuahua", R.drawable.chihuahua_110x110));
        dogs.add(new Dog("German Shepherd", R.drawable.german_shepherd_110x110));
        dogs.add(new Dog("Golden Retriever", R.drawable.golden_retriever_110x110));
        dogs.add(new Dog("Greyhound", R.drawable.greyhound_110x110));


        return dogs;

    }

}
