package xyz.lgvalle.materialdoc;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupToolbar();
        setupSearchView();
        setupAdapter();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, DemoActivity.class));
            }
        });

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            handleSearchQuery("oncreate", intent.getExtras());
        }
    }

    private void setupAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);
        DogsAdapter dogsAdapter = new DogsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(dogsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            handleSearchQuery("onNewIntent", extras);
        }
    }

    private void handleSearchQuery(String oncreate, Bundle extras) {
        String userQuery = String.valueOf(extras.get(SearchManager.USER_QUERY));
        String query = String.valueOf(extras.get(SearchManager.QUERY));

        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);

        Toast.makeText(this, "from: " + oncreate + "  query: " + query + " user_query: " + userQuery, Toast.LENGTH_SHORT).show();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupSearchView() {
        SearchView searchView = (SearchView) findViewById(R.id.search);
        // Sets searchable configuration defined in searchable.xml for this SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setQueryHint(getString(R.string.query_hint));
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_ACTION_SEARCH |
                EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFor(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filterSearchFor(query);
                return true;
            }


        });

    }

    private void filterSearchFor(String query) {
        Log.d(TAG, "Query change: " + query);
    }

    private void searchFor(String query) {
        Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();
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


    public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
        public final static String AUTHORITY = "xyz.lgvlle.materialdoc";
        public final static int MODE = DATABASE_MODE_QUERIES;

        public MySuggestionProvider() {
            setupSuggestions(AUTHORITY, MODE);
        }
    }
}
