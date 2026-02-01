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

    @GET("movie/{id}/videos?language=es-ES")
    Call<VideoResponse> getVideosPelicula(@Path("id") int id);


    @GET("tv/popular?language=es-ES")
    Call<SerieResponse> getSeriesPopulares(@Query("page") int page);

    @GET("tv/{id}?language=es-ES")
    Call<Serie> getDetalleSerie(@Path("id") int id);

    @GET("tv/{id}/videos?language=es-ES")
    Call<VideoResponse> getVideosSerie(@Path("id") int id);
}