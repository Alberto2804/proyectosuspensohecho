package com.example.proyectoenero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.proyectoenero.R;
import com.example.proyectoenero.databinding.FragmentSeguimientoBinding;
import room.LocalViewModel;
import room.MediaLocalAdapter;
import room.MediaEntity;


public class SeguimientoFragment extends Fragment {
    private FragmentSeguimientoBinding binding;
    private LocalViewModel viewModel;
    private MediaLocalAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater i, ViewGroup c, Bundle s) {
        binding = FragmentSeguimientoBinding.inflate(i, c, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(LocalViewModel.class);

        adapter = new MediaLocalAdapter(new MediaLocalAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(MediaEntity item) {
                viewModel.eliminarMedia(item);
            }

            @Override
            public void onItemClick(MediaEntity item) {
                Bundle bundle = new Bundle();
                bundle.putInt("idLocal", item.getId());
                Navigation.findNavController(requireView())
                        .navigate(R.id.detalleSeguimientoFragment, bundle);
            }
        });

        binding.recyclerSeguimiento.setAdapter(adapter);
        binding.recyclerSeguimiento.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.obtenerSeguimientos().observe(getViewLifecycleOwner(), lista -> {
            adapter.setLista(lista);

            if (lista == null || lista.isEmpty()) {
                binding.tvEmpty.setVisibility(View.VISIBLE);
            } else {
                binding.tvEmpty.setVisibility(View.GONE);
            }
        });

        binding.fabAdd.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_global_anadirSeguimiento)
        );
    }
}