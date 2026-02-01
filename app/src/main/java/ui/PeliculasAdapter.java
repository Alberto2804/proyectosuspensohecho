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

import data.Pelicula;
import viewmodel.TmdbViewModel;

public class PeliculasAdapter extends RecyclerView.Adapter<PeliculasAdapter.PeliculaViewHolder> {

    List<Pelicula> peliculasList;
    TmdbViewModel viewModel;

    private final LayoutInflater inflater;

    public PeliculasAdapter(Context context, ViewModel viewModel) {
        this.viewModel = (TmdbViewModel) viewModel;
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
        holder.tvDescripcion.setText(pelicula.getOverview());
        holder.tvSubtitulo.setText("Película • Popular");

        holder.icMedia.setImageResource(android.R.drawable.ic_menu_slideshow);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + pelicula.getPosterPath())
                .transform(new CenterCrop())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(v -> {
            viewModel.seleccionarPelicula(pelicula.getId());

            Bundle bundle = new Bundle();
            bundle.putInt("id", pelicula.getId());
            bundle.putBoolean("esPelicula", true);

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.detalleFragment, bundle);

        });
    }

    @Override
    public int getItemCount() {
        return peliculasList != null ? peliculasList.size() : 0;
    }

    public void setPeliculasList(List<Pelicula> peliculasList) {
        this.peliculasList = peliculasList;
        notifyDataSetChanged();
    }

    public void addPeliculasList(List<Pelicula> nuevas) {
        int inicio = peliculasList.size();
        this.peliculasList.addAll(nuevas);
        notifyItemRangeInserted(inicio, nuevas.size());
    }

    public class PeliculaViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgPoster, icMedia;
        final TextView tvTitulo, tvSubtitulo, tvDescripcion;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            icMedia = itemView.findViewById(R.id.icMedia);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvSubtitulo = itemView.findViewById(R.id.tvSubtitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}