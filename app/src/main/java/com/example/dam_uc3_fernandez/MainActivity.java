package com.example.dam_uc3_fernandez;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText origen, destino, dataIda, dataVolta;
    private Spinner claseServicio;
    private Calendar calendar; // Para comparar las fechas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el calendario
        calendar = Calendar.getInstance();

        // Referencias a los elementos
        origen = findViewById(R.id.origen);
        destino = findViewById(R.id.destino);
        dataIda = findViewById(R.id.data_ida);
        dataVolta = findViewById(R.id.data_volta);
        claseServicio = findViewById(R.id.clase_servicio);
        Button btnBuscar = findViewById(R.id.btn_buscar);

        // Acción del botón Buscar
        btnBuscar.setOnClickListener(v -> {
            String msg = "Buscando vuelos de " + origen.getText().toString() +
                    " a " + destino.getText().toString();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        // Agregar listeners para mostrar el DatePicker
        dataIda.setOnClickListener(v -> showDatePickerDialog(dataIda, null));
        dataVolta.setOnClickListener(v -> showDatePickerDialog(dataVolta, dataIda));

        // Configurar el Spinner con adaptador personalizado
        setupSpinner();
    }

    // Método para configurar el Spinner
    private void setupSpinner() {
        // Crear la lista de opciones para el Spinner
        List<SpinnerItem> items = new ArrayList<>();
        items.add(new SpinnerItem("Económico", R.drawable.icon_economico));
        items.add(new SpinnerItem("Económico Premium", R.drawable.icon_economico_premium));
        items.add(new SpinnerItem("Business Premium", R.drawable.icon_business_premium));

        // Crear y asignar el adaptador al Spinner
        SpinnerAdapter adapter = new SpinnerAdapter(this, items);
        claseServicio.setAdapter(adapter);
    }

    // Método para mostrar el DatePickerDialog
    private void showDatePickerDialog(EditText editText, EditText editTextIda) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                (view, year1, month1, dayOfMonth1) -> {
                    // Verificar si es la fecha de vuelta y que no sea antes de la fecha de ida
                    if (editTextIda != null && !isDateValidForReturn(year1, month1, dayOfMonth1, editTextIda)) {
                        Toast.makeText(MainActivity.this, "La fecha de vuelta no puede ser antes de la ida", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Formatear la fecha seleccionada
                    String date = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                    editText.setText(date); // Establecer la fecha en el campo correspondiente
                }, year, month, dayOfMonth);

        // Evitar seleccionar fechas anteriores a la actual
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Mostrar el dialogo
        datePickerDialog.show();
    }

    // Método para validar que la fecha de vuelta no sea anterior a la fecha de ida
    private boolean isDateValidForReturn(int year, int month, int day, EditText dataIda) {
        try {
            String[] parts = dataIda.getText().toString().split("/");

            int idaDay = Integer.parseInt(parts[0]);
            int idaMonth = Integer.parseInt(parts[1]) - 1; // Los meses comienzan desde 0
            int idaYear = Integer.parseInt(parts[2]);

            Calendar idaDate = Calendar.getInstance();
            idaDate.set(idaYear, idaMonth, idaDay);

            Calendar returnDate = Calendar.getInstance();
            returnDate.set(year, month, day);

            return !returnDate.before(idaDate); // La fecha de vuelta no puede ser antes que la de ida

        } catch (Exception e) {
            return true; // Si no hay fecha de ida válida, se permite cualquier fecha de vuelta
        }
    }
}
