package ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.proyectoenero.R;
import com.example.proyectoenero.databinding.FragmentDetalleBinding;

import api.Resource;
import data.Pelicula;
import data.Serie;
import data.Video;
import viewmodel.TmdbViewModel;

public class DetalleFragment extends Fragment {

    private FragmentDetalleBinding binding;
    private TmdbViewModel viewModel;

    private String currentVideoKey = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TmdbViewModel.class);

        if (getArguments() != null) {
            int id = getArguments().getInt("id");
            boolean esPelicula = getArguments().getBoolean("esPelicula");

            if (esPelicula) {
                viewModel.seleccionarPelicula(id);
                observarPelicula();
            } else {
                viewModel.seleccionarSerie(id);
                observarSerie();
            }

            observarVideos();
        }

        binding.btnVerTrailer.setOnClickListener(v -> {
            if (currentVideoKey != null) {
                abrirYoutube(currentVideoKey);
            } else {
                Toast.makeText(getContext(), "Tráiler no disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observarPelicula() {
        viewModel.peliculaSeleccionada.observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    binding.progressLoading.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setVisibility(View.GONE);
                    break;

                case SUCCESS:
                    binding.progressLoading.setVisibility(View.GONE);
                    binding.tvErrorMessage.setVisibility(View.GONE);
                    mostrarPelicula(resource.data);
                    break;

                case ERROR:
                    binding.progressLoading.setVisibility(View.GONE);
                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(resource.message);
                    limpiarVista();
                    break;
            }
        });
    }

    private void observarSerie() {
        viewModel.serieSeleccionada.observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    binding.progressLoading.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setVisibility(View.GONE);
                    break;

                case SUCCESS:
                    binding.progressLoading.setVisibility(View.GONE);
                    binding.tvErrorMessage.setVisibility(View.GONE);
                    mostrarSerie(resource.data);
                    break;

                case ERROR:
                    binding.progressLoading.setVisibility(View.GONE);
                    binding.tvErrorMessage.setVisibility(View.VISIBLE);
                    binding.tvErrorMessage.setText(resource.message);
                    limpiarVista();
                    break;
            }
        });
    }

    private void observarVideos() {
        viewModel.videos.observe(getViewLifecycleOwner(), resource -> {
            if (resource.status == Resource.Status.SUCCESS && resource.data != null) {
                for (Video v : resource.data) {
                    if ("YouTube".equals(v.getSite()) && "Trailer".equals(v.getType())) {
                        currentVideoKey = v.getKey();
                        binding.btnVerTrailer.setEnabled(true);
                        binding.btnVerTrailer.setText("VER TRÁILER");
                        return;
                    }
                }
                binding.btnVerTrailer.setEnabled(false);
                binding.btnVerTrailer.setText("NO DISPONIBLE");
            }
        });
    }

    private void mostrarPelicula(Pelicula p) {
        if (p == null) return;

        binding.tvDetalleTitulo.setText(p.getTitle());
        binding.tvDetalleDescripcion.setText(p.getOverview());
        binding.tvDetalleSubtitulo.setText("Sinopsis");

        String imagenPath = p.getBackdropPath() != null ? p.getBackdropPath() : p.getPosterPath();
        if (imagenPath != null) {
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w780" + imagenPath)
                    .into(binding.imgDetalleFondo);
        }
    }

    private void mostrarSerie(Serie s) {
        if (s == null) return;

        binding.tvDetalleTitulo.setText(s.getName());
        binding.tvDetalleDescripcion.setText(s.getOverview());
        binding.tvDetalleSubtitulo.setText("Sinopsis");


        String imagenPath = s.getBackdropPath() != null ? s.getBackdropPath() : s.getPosterPath();
        if (imagenPath != null) {
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w780" + imagenPath)
                    .into(binding.imgDetalleFondo);
        }
    }

    private void limpiarVista() {
        binding.tvDetalleTitulo.setText("");
        binding.tvDetalleDescripcion.setText("");
        binding.tvDetalleSubtitulo.setText("");
        binding.imgDetalleFondo.setImageDrawable(null);
    }

    private void abrirYoutube(String key) {
        try {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + key));
            startActivity(webIntent);
        }
    }
}