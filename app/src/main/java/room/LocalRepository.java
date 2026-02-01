package room;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import room.MediaDao;
import room.MediaDatabase;
import room.MediaEntity;

public class LocalRepository {
    private MediaDao mediaDao;
    private Executor executor;

    public LocalRepository(Application application) {
        mediaDao = MediaDatabase.getInstance(application).mediaDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertar(MediaEntity media) {
        executor.execute(() -> mediaDao.insertar(media));
    }
    public void insertarSeguimientoSiNoExiste(MediaEntity media, InsertCallback callback) {
        executor.execute(() -> {
            int count = mediaDao.contarSeguimientos(media.getTmdbId());
            if (count > 0) {
                callback.onResult(false);
            } else {
                mediaDao.insertar(media);
                callback.onResult(true);
            }
        });
    }

    public void insertarPendienteSiNoExiste(MediaEntity media, InsertCallback callback) {
        executor.execute(() -> {

            int count = mediaDao.contarPendientes(media.getTmdbId());
            if (count > 0) {
                callback.onResult(false);
            } else {
                mediaDao.insertar(media);
                callback.onResult(true);
            }
        });
    }


    public void eliminar(MediaEntity media) {
        executor.execute(() -> mediaDao.eliminar(media));
    }

    public LiveData<List<MediaEntity>> obtenerPendientes() {
        return mediaDao.obtenerPendientes();
    }

    public LiveData<List<MediaEntity>> obtenerSeguimientos() {
        return mediaDao.obtenerSeguimientos();
    }
    public LiveData<MediaEntity> obtenerPorId(int id) {
        return mediaDao.obtenerPorId(id);
    }

    public interface InsertCallback {
        void onResult(boolean success);
    }
}