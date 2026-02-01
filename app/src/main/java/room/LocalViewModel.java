package room;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import room.MediaEntity;
import room.LocalRepository;

public class LocalViewModel extends AndroidViewModel {

    private LocalRepository repository;

    public LocalViewModel(@NonNull Application application) {
        super(application);
        repository = new LocalRepository(application);
    }

    public void insertarSeguimientoUnico(MediaEntity media, LocalRepository.InsertCallback callback) {
        repository.insertarSeguimientoSiNoExiste(media, callback);
    }

    public void eliminarMedia(MediaEntity media) {
        repository.eliminar(media);
    }

    public LiveData<List<MediaEntity>> obtenerPendientes() {
        return repository.obtenerPendientes();
    }

    public LiveData<List<MediaEntity>> obtenerSeguimientos() {
        return repository.obtenerSeguimientos();
    }

    public LiveData<MediaEntity> obtenerPorId(int id) {
        return repository.obtenerPorId(id);
    }


    public void insertarMedia(MediaEntity media) {
    }
}