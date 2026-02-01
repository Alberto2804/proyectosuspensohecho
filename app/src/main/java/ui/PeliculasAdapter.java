package ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.proyectoenero.R;
import java.util.ArrayList;
import java.util.List;
import data.Pelicula;
import room.MediaEntity;
import room.LocalViewModel;
import viewmodel.TmdbViewModel;

public class PeliculasAdapter extends RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder> {

    private List<Pelicula> peliculasList;
    private TmdbViewModel tmdbViewModel;
    private LocalViewModel localViewModel;
    private final LayoutInflater inflater;
    private final Context context;

    public PeliculasAdapter(Context context, TmdbViewModel tmdbViewModel, LocalViewModel localViewModel) {
        this.context = context;
        this.tmdbViewModel = tmdbViewModel;
        this.localViewModel = localViewModel;
        this.inflater = LayoutInflater.from(context);
        this.peliculasList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.viewholder_pelicula, parent, false);
        return new PeliculaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder holder, int position) {
        Pelicula pelicula = peliculasList.get(position);

        holder.tvTitulo.setText(pelicula.getTitle());
        holder.tvSubtitulo.setText("Película • Popular");
        holder.tvDescripcion.setText(pelicula.getOverview());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + pelicula.getPosterPath())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(v -> {
            tmdbViewModel.seleccionarPelicula(pelicula.getId());
            Bundle bundle = new Bundle();
            bundle.putInt("id", pelicula.getId());
            bundle.putBoolean("esPelicula", true);
            Navigation.findNavController(v).navigate(R.id.detalleFragment, bundle);
        });

        holder.btnAgregarPendiente.setOnClickListener(v -> {
            MediaEntity media = new MediaEntity();
            media.setTmdbId(pelicula.getId());
            media.setTitulo(pelicula.getTitle());
            media.setDescripcion(pelicula.getOverview());
            media.setPosterPath(pelicula.getPosterPath());
            media.setTipo("PELICULA");
            media.setEsPendiente(true);

            media.setEsPendiente(true);
            media.setEsSeguimiento(false);

            localViewModel.insertarMedia(media);
            Toast.makeText(context, "Añadido a pendientes", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() { return peliculasList != null ? peliculasList.size() : 0; }

    public void setPeliculasList(List<Pelicula> list) {
        this.peliculasList = list;
        notifyDataSetChanged();
    }

    public void addPeliculasList(List<Pelicula> list) {
        int start = peliculasList.size();
        this.peliculasList.addAll(list);
        notifyItemRangeInserted(start, list.size());
    }

    class PeliculaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, btnAgregarPendiente;
        TextView tvTitulo, tvSubtitulo, tvDescripcion;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            btnAgregarPendiente = itemView.findViewById(R.id.btnAgregarPendiente);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvSubtitulo = itemView.findViewById(R.id.tvSubtitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}