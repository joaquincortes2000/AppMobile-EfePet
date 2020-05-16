package com.example.certamen1app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> nombres;
    private List<String> raza;
    private List<String> edad;

    public MyAdapter(Context context, int layout, List<String> nombres, List<String> raza, List<String> edad){
        this.context = context;
        this.layout = layout;
        this.nombres = nombres;
        this.raza = raza;
        this.edad = edad;
    }

    @Override
    public int getCount() {
        return this.nombres.size();
    }

    @Override
    public Object getItem(int position) {
        return this.nombres.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View v = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.list_item, null);

        String currentName = nombres.get(position);
        String currentRaza = raza.get(position);
        String currentEdad = edad.get(position);

        TextView textView = (TextView) v.findViewById(R.id.textView2);
        textView.setText(currentName);
        TextView textView2 = (TextView) v.findViewById(R.id.textView4);
        textView2.setText(currentRaza);
        TextView textView3 = (TextView) v.findViewById(R.id.textView5);
        textView3.setText(currentEdad);

        return v;
    }
}
