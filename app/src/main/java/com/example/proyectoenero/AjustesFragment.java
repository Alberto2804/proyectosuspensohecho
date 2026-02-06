package com.example.proyectoenero;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyectoenero.databinding.FragmentAjustesBinding;

import java.util.Arrays;
import java.util.List;

import sharedPreferences.MainViewModel;

public class AjustesFragment extends Fragment {

    private FragmentAjustesBinding binding;
    private MainViewModel viewModel;

    private final List<String> idiomasNombres = Arrays.asList("Español (España)", "Inglés (USA)", "Francés", "Alemán");
    private final List<String> idiomasCodigos = Arrays.asList("es-ES", "en-US", "fr-FR", "de-DE");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAjustesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupSpinner();
        cargarPreferencias();

        binding.btnGuardar.setOnClickListener(v -> guardarPreferencias());
        binding.btnReset.setOnClickListener(v -> {
            viewModel.resetear();
            cargarPreferencias(); // Recargamos la UI con los defaults
            aplicarTema("claro"); // Forzamos tema claro
            Toast.makeText(getContext(), "Preferencias reseteadas", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, idiomasNombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerIdioma.setAdapter(adapter);
    }

    private void cargarPreferencias() {
        // Nombre
        binding.etNombreUsuario.setText(viewModel.getNombre());

        // Idioma
        String idiomaActual = viewModel.getIdioma();
        int index = idiomasCodigos.indexOf(idiomaActual);
        if (index >= 0) binding.spinnerIdioma.setSelection(index);

        // Wifi
        binding.cbSoloWifi.setChecked(viewModel.isSoloWifi());

        // Tema
        String tema = viewModel.getTema();
        if ("oscuro".equals(tema)) {
            binding.rbTemaOscuro.setChecked(true);
        } else {
            binding.rbTemaClaro.setChecked(true);
        }
    }

    private void guardarPreferencias() {
        viewModel.guardarNombre(binding.etNombreUsuario.getText().toString());

        int selectedLangIndex = binding.spinnerIdioma.getSelectedItemPosition();
        viewModel.guardarIdioma(idiomasCodigos.get(selectedLangIndex));

        viewModel.guardarSoloWifi(binding.cbSoloWifi.isChecked());

        String temaSeleccionado = binding.rbTemaOscuro.isChecked() ? "oscuro" : "claro";
        viewModel.guardarTema(temaSeleccionado);

        aplicarTema(temaSeleccionado);

        Toast.makeText(getContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
    }

    private void aplicarTema(String tema) {
        if ("oscuro".equals(tema)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}