package br.edu.dmos5.github_dmos5.api;

import java.util.List;

import br.edu.dmos5.github_dmos5.model.Repository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    String baseURL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<Repository>> listarRepositorios(@Path("user") String user);
}
