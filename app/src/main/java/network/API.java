package network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface API {

    @GET("/login/{username}/{password}")
    Call<LoginResponse> login(
            @Path("username") String Username,
            @Path("password") String Password
    );

    @GET("/create/{username}/{password}/{email}")
    Call<GenericStatusResponse> createAccount(
            @Path("username") String Username,
            @Path("password") String Password,
            @Path("email") String Email
    );

    @GET("/todolist/create/{name}")
    Call<GenericStatusResponse> createTodoList(@Path("name") String name, @HeaderMap Map<String, String> headers);

    @GET("/todolist/list")
    Call<ListTodoListResponse> getTodoList(@HeaderMap Map<String, String> headers);

    @GET("/todolist/{id}")
    Call<TodoListDetailResponse> getTodoListDetails (
            @Path("id") int id,
            @HeaderMap Map<String, String> headers
    );

    @GET("/task/create/{todolistid}/{text}")
    Call<GenericStatusResponse> createTask(@Path("todolistid") int todolistid, @Path("text") String text, @HeaderMap Map<String, String> headers);

    @GET("/task/delete/{id}")
    Call<GenericStatusResponse> deleteTask(@Path("id") int id, @HeaderMap Map<String, String> headers);

    @GET("/task/toggle/{id}")
    Call<GenericStatusResponse> toggleTask(@Path("id") int id, @HeaderMap Map<String, String> headers);

}
