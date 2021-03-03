package TaskBoard.androidclient.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.util.List;

import TaskBoard.androidclient.R;
import TaskBoard.androidclient.data.Contact;
import TaskBoard.androidclient.data.TaskBoardAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivitySearchContacts extends AppCompatActivity {
    TextView textViewSearchResult;

    private String apiBaseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);

        this.apiBaseUrl = this.getIntent().getStringExtra("paramApiBaseUrl");
        if (!this.apiBaseUrl.endsWith("/"))
            this.apiBaseUrl += "/";

        EditText editTextKeyword = findViewById(R.id.editTextKeyword);
        editTextKeyword.requestFocus();
        Button buttonSearch = findViewById(R.id.buttonSearch);
        this.textViewSearchResult = findViewById(R.id.textViewSearchResult);

        buttonSearch.setOnClickListener(v -> {
            hideSoftKeyboard(this);
            String keyword = editTextKeyword.getText().toString();
            searchContactsByKeyword(keyword);
        });
    }

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void searchContactsByKeyword(String keyword) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TaskBoardAPI service = retrofit.create(TaskBoardAPI.class);

        Call<List<Contact>> request = service.findContactsByKeyword(keyword);
        Context context = this;
        request.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    showError("HTTP code: " + response.code());
                    return;
                }
                displayContacts(response.body());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    private void displayContacts(List<Contact> contacts) {
        textViewSearchResult.setText("Contacts found: " + contacts.size());

        // Lookup the recyclerview in activity layout
        RecyclerView recyclerViewContacts =
                (RecyclerView) findViewById(R.id.recyclerViewContacts);

        ContactsAdapter contactsAdapter = new ContactsAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        recyclerViewContacts.setAdapter(contactsAdapter);
        // Set layout manager to position the items
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showError(String errMsg) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errMsg)
                .show();
    }
}
