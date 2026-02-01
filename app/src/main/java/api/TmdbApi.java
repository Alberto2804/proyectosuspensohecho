package api;

import data.Pelicula;
import data.PeliculaResponse;
import data.Serie;
import data.SerieResponse;
import data.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    @GET("movie/popular?language=es-ES")
    Call<PeliculaResponse> getPeliculasPopulares(@Query("page") int page);

    @GET("movie/{id}?language=es-ES")
    Call<Pelicula> getDetallePelicula(@Path("id") int id);

    @GET("movie/{id}/videos")
    Call<VideoResponse> getVideosPelicula(@Path("id") int id);


    @GET("tv/popular?language=es-ES")
    Call<SerieResponse> getSeriesPopulares(@Query("page") int page);

    @GET("tv/{id}?language=es-ES")
    Call<Serie> getDetalleSerie(@Path("id") int id);

    @GET("tv/{id}/videos")
    Call<VideoResponse> getVideosSerie(@Path("id") int id);

    @GET("search/movie?language=es-ES")
    Call<PeliculaResponse> searchPeliculas(@Query("query") String query);

    @GET("search/tv?language=es-ES")
    Call<SerieResponse> searchSeries(@Query("query") String query);
}