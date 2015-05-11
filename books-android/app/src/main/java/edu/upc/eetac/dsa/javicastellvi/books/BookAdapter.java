package edu.upc.eetac.dsa.javicastellvi.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.javicastellvi.books.api.Book;


/**
 * Created by Javi on 02/05/2015.
 */
public class BookAdapter extends BaseAdapter {
    private  ArrayList<Book> data;
    private LayoutInflater inflater;

    public BookAdapter(Context context, ArrayList<Book> data) {
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
        return 0;
    }

    private static class ViewHolder {
        TextView tvTitulo;
        TextView tvAutor;
        TextView tvIdLibro;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_book, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitulo = (TextView) convertView
                    .findViewById(R.id.tvTitulo);
            viewHolder.tvAutor = (TextView) convertView
                    .findViewById(R.id.tvAutor);
            viewHolder.tvIdLibro = (TextView) convertView
                    .findViewById(R.id.tvIdLibro);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String titulo = data.get(position).getTitulo();
        String autor = data.get(position).getAutor();
        String idlibro = data.get(position).getIdlibro(); //mejor asi
        viewHolder.tvTitulo.setText(titulo);
        viewHolder.tvAutor.setText(autor);
        viewHolder.tvIdLibro.setText(idlibro);
        return convertView;
    }
}
