package com.example.dam_uc3_fernandez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    private Context context;
    private List<SpinnerItem> items;

    public SpinnerAdapter(Context context, List<SpinnerItem> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Usamos el layout para la vista seleccionada (cuando se ve el Spinner)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        }

        SpinnerItem item = getItem(position);

        TextView textView = convertView.findViewById(R.id.spinner_text);
        ImageView imageView = convertView.findViewById(R.id.spinner_icon);

        textView.setText(item.getText());
        imageView.setImageResource(item.getImageResource());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Usamos el layout para las opciones desplegadas del Spinner
        return getView(position, convertView, parent);
    }
}
