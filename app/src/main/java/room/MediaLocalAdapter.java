package room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoenero.R;

import java.util.ArrayList;
import java.util.List;

import room.ImageUtils;
import room.MediaEntity;

public class MediaLocalAdapter extends RecyclerView.Adapter<MediaLocalAdapter.ViewHolder> {

    private List<MediaEntity> lista = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(MediaEntity media);
        void onItemClick(MediaEntity media);
    }

    public MediaLocalAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setLista(List<MediaEntity> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pelicula, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MediaEntity item = lista.get(position);

        holder.tvTitulo.setText(item.getTitulo());


        holder.tvDescripcion.setText(item.getDescripcion());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });

        if (item.getFotoRecuerdo() != null) {
            holder.imgPoster.setImageBitmap(ImageUtils.blobToBitmap(item.getFotoRecuerdo()));
            holder.imgPoster.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else if (item.getPosterPath() != null) {
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + item.getPosterPath())
                    .into(holder.imgPoster);
        }


        if (item.isEsPendiente()) {
            holder.tvSubtitulo.setText("Pendiente • " + item.getTipo());
        } else {

            holder.tvSubtitulo.setText("Visto el " + item.getFechaVisualizacion() + " • ★ " + item.getPuntuacion());
        }

        holder.btnAccion.setImageResource(android.R.drawable.ic_menu_delete);
        holder.btnAccion.setOnClickListener(v -> listener.onDeleteClick(item));

        if ("SERIE".equals(item.getTipo())) {
            holder.icMedia.setImageResource(android.R.drawable.ic_menu_agenda);
        } else {
            holder.icMedia.setImageResource(android.R.drawable.ic_menu_slideshow);
        }
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, btnAccion, icMedia;
        TextView tvTitulo, tvSubtitulo, tvDescripcion;

        ViewHolder(View v) {
            super(v);
            imgPoster = v.findViewById(R.id.imgPoster);
            btnAccion = v.findViewById(R.id.btnAgregarPendiente);
            icMedia = v.findViewById(R.id.icMedia);

            tvTitulo = v.findViewById(R.id.tvTitulo);
            tvSubtitulo = v.findViewById(R.id.tvSubtitulo);
            tvDescripcion = v.findViewById(R.id.tvDescripcion);
        }
    }
}