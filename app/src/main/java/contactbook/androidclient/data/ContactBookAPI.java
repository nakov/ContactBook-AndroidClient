package TaskBoard.androidclient.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TaskBoardAPI {
    @GET("contacts")
    Call<List<Contact>> getContacts();

    @GET("contacts/{id}")
    Call<Contact> findContactById(@Path("id") int id);

    @GET("contacts/search/{keyword}")
    Call<List<Contact>> findContactsByKeyword(@Path("keyword") String keyword);
}
