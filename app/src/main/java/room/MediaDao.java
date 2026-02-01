package room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MediaDao {

    @Insert
    void insertar(MediaEntity media);

    @Update
    void actualizar(MediaEntity media);

    @Delete
    void eliminar(MediaEntity media);

    @Query("SELECT * FROM media_local WHERE esPendiente = 1")
    LiveData<List<MediaEntity>> obtenerPendientes();

    @Query("SELECT * FROM media_local WHERE esSeguimiento = 1")
    LiveData<List<MediaEntity>> obtenerSeguimientos();


    @Query("SELECT * FROM media_local WHERE tmdbId = :tmdbId AND esSeguimiento = 1 LIMIT 1")
    MediaEntity comprobarSeguimientoExistente(int tmdbId);

    @Query("SELECT * FROM media_local WHERE id = :id")
    LiveData<MediaEntity> obtenerPorId(int id);

    @Query("SELECT COUNT(*) FROM media_local WHERE tmdbId = :tmdbId AND esSeguimiento = 1")
    int contarSeguimientos(int tmdbId);
}