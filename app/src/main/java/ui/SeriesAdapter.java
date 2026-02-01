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

import data.Serie;
import room.MediaEntity;
import room.MediaEntity;
import room.LocalViewModel;
import viewmodel.TmdbViewModel;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SerieViewHolder> {

    private List<Serie> seriesList;
    private TmdbViewModel tmdbViewModel;
    private LocalViewModel localViewModel;
    private final LayoutInflater inflater;
    private final Context context;

    public SeriesAdapter(Context context, TmdbViewModel tmdbViewModel, LocalViewModel localViewModel) {
        this.context = context;
        this.tmdbViewModel = tmdbViewModel;
        this.localViewModel = localViewModel;
        this.inflater = LayoutInflater.from(context);
        this.seriesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.viewholder_pelicula, parent, false);
        return new SerieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        Serie serie = seriesList.get(position);

        holder.tvTitulo.setText(serie.getName());
        holder.tvDescripcion.setText(serie.getOverview());
        holder.tvSubtitulo.setText("Serie de TV");
        holder.icMedia.setImageResource(android.R.drawable.ic_menu_agenda);

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + serie.getPosterPath())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(v -> {
            tmdbViewModel.seleccionarSerie(serie.getId());

            Bundle bundle = new Bundle();
            bundle.putInt("id", serie.getId());
            bundle.putBoolean("esPelicula", false);

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.detalleFragment, bundle);
        });

        holder.btnAgregarPendiente.setOnClickListener(v -> {
            MediaEntity media = new MediaEntity();
            media.setTmdbId(serie.getId());
            media.setTitulo(serie.getName());
            media.setDescripcion(serie.getOverview());
            media.setPosterPath(serie.getPosterPath());
            media.setTipo("SERIE");
            media.setEsPendiente(true);

            localViewModel.insertarMedia(media);

            Toast.makeText(context, "Serie añadida a pendientes", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return seriesList != null ? seriesList.size() : 0;
    }

    public void setSeriesList(List<Serie> list) {
        this.seriesList = list;
        notifyDataSetChanged();
    }

    public void addSeriesList(List<Serie> list) {
        int start = seriesList.size();
        this.seriesList.addAll(list);
        notifyItemRangeInserted(start, list.size());
    }

    public class SerieViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgPoster, icMedia, btnAgregarPendiente;
        final TextView tvTitulo, tvSubtitulo, tvDescripcion;

        public SerieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            icMedia = itemView.findViewById(R.id.icMedia);
            btnAgregarPendiente = itemView.findViewById(R.id.btnAgregarPendiente);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvSubtitulo = itemView.findViewById(R.id.tvSubtitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}