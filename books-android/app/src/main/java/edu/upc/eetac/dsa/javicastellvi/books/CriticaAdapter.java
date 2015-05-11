package edu.upc.eetac.dsa.javicastellvi.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.upc.eetac.dsa.javicastellvi.books.api.Critica;


;

/**
 * Created by Javi on 2/05/15.
 */
public class CriticaAdapter extends BaseAdapter{




        ArrayList<Critica> data;
        LayoutInflater inflater;

        public CriticaAdapter(Context context, ArrayList<Critica> data) {
            super();
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return ((Critica) getItem(position)).getIdLibro();
        }

        private static class ViewHolder {
            TextView tvUsername;
            TextView tvCriticasTex;
            TextView tvLastModified;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_row_critica, null);
                viewHolder = new ViewHolder();
                viewHolder.tvUsername = (TextView) convertView
                        .findViewById(R.id.tvUsername);
                viewHolder.tvCriticasTex = (TextView) convertView
                        .findViewById(R.id.tvCriticasText);
                viewHolder.tvLastModified = (TextView) convertView
                        .findViewById(R.id.tvLastModified);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String username = data.get(position).getUsername();
            String texto = data.get(position).getContenido();
            String ultima_fecha_hora = SimpleDateFormat.getInstance().format(
                    data.get(position).getLastModified());
            viewHolder.tvUsername.setText(username);
            viewHolder.tvCriticasTex.setText(texto);
            viewHolder.tvLastModified.setText(ultima_fecha_hora);
            return convertView;
        }



 }


