package com.example.dam_uc3_fernandez;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText origen, destino, dataIda, dataVolta;
    private Spinner claseServicio;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        origen = findViewById(R.id.origen);
        destino = findViewById(R.id.destino);
        dataIda = findViewById(R.id.data_ida);
        dataVolta = findViewById(R.id.data_volta);
        claseServicio = findViewById(R.id.clase_servicio);

        // Configuración del ImageButton
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> {
            String msg = "Buscando vuelos de " + origen.getText().toString() + " a " + destino.getText().toString();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        // Configuración del TextView para el evento click
        TextView tvBuscarVuelo = findViewById(R.id.tv_buscar_vuelos);
        tvBuscarVuelo.setOnClickListener(v -> {
            String msg = "Buscando vuelos de " + origen.getText().toString() + " a " + destino.getText().toString();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        // Configuración del DatePicker
        dataIda.setOnClickListener(v -> showDatePickerDialog(dataIda, null));
        dataVolta.setOnClickListener(v -> showDatePickerDialog(dataVolta, dataIda));

        setupSpinner();
    }

    private void setupSpinner() {
        List<SpinnerItem> items = new ArrayList<>();
        items.add(new SpinnerItem("Económico", R.drawable.icon_economico));
        items.add(new SpinnerItem("Económico Premium", R.drawable.icon_economico_premium));
        items.add(new SpinnerItem("Business Premium", R.drawable.icon_business_premium));

        SpinnerAdapter adapter = new SpinnerAdapter(this, items);
        claseServicio.setAdapter(adapter);
    }

    private void showDatePickerDialog(EditText editText, EditText editTextIda) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                (view, year1, month1, dayOfMonth1) -> {

                    if (editTextIda != null && !isDateValidForReturn(year1, month1, dayOfMonth1, editTextIda)) {
                        Toast.makeText(MainActivity.this, "La fecha de vuelta no puede ser antes de la ida", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String date = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                    editText.setText(date);
                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    private boolean isDateValidForReturn(int year, int month, int day, EditText dataIda) {
        try {
            String[] parts = dataIda.getText().toString().split("/");

            int idaDay = Integer.parseInt(parts[0]);
            int idaMonth = Integer.parseInt(parts[1]) - 1;
            int idaYear = Integer.parseInt(parts[2]);

            Calendar idaDate = Calendar.getInstance();
            idaDate.set(idaYear, idaMonth, idaDay);

            Calendar returnDate = Calendar.getInstance();
            returnDate.set(year, month, day);

            return !returnDate.before(idaDate);

        } catch (Exception e) {
            return true;
        }
    }
}
