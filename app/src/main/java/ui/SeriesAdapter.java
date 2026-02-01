package ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.proyectoenero.R;

import java.util.ArrayList;
import java.util.List;

import data.Serie;
import viewmodel.TmdbViewModel;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SerieViewHolder> {

    List<Serie> seriesList;
    TmdbViewModel viewModel;

    private final LayoutInflater inflater;

    public SeriesAdapter(Context context, ViewModel viewModel) {
        this.viewModel = (TmdbViewModel) viewModel;
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

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + serie.getPosterPath())
                .transform(new CenterCrop())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(v -> {
            viewModel.seleccionarSerie(serie.getId());

            Bundle bundle = new Bundle();
            bundle.putInt("id", serie.getId());
            bundle.putBoolean("esPelicula", false); // Es serie

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.detalleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return seriesList != null ? seriesList.size() : 0;
    }

    public void setSeriesList(List<Serie> seriesList) {
        this.seriesList = seriesList;
        notifyDataSetChanged();
    }

    public void addSeriesList(List<Serie> nuevas) {
        int inicio = seriesList.size();
        this.seriesList.addAll(nuevas);
        notifyItemRangeInserted(inicio, nuevas.size());
    }

    public class SerieViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgPoster, icMedia;
        final TextView tvTitulo, tvSubtitulo, tvDescripcion;

        public SerieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            icMedia = itemView.findViewById(R.id.icMedia);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvSubtitulo = itemView.findViewById(R.id.tvSubtitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}