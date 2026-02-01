package room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MediaEntity.class}, version = 1)
public abstract class MediaDatabase extends RoomDatabase {

    public abstract MediaDao mediaDao();

    private static volatile MediaDatabase instance;

    public static MediaDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (MediaDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    MediaDatabase.class,
                                    "media_database.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}