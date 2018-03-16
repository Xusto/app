package dam.xusto;

import android.content.Context;
import android.support.annotation.NonNull;
import org.jetbrains.annotations.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by aroig on 3/16/18.
 */

public class ArrayAdapterComentario extends ArrayAdapter<Comentario> {
    private final int resource;

    public ArrayAdapterComentario(@NonNull Context context, @NonNull Comentario[] comentarios) {
        super(context, R.layout.item_comentarios, comentarios);
        resource = R.layout.item_comentarios;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View fila = inflater.inflate(resource, null);

        Comentario c = getItem(position);

        TextView tvNomeUsuario = (TextView) fila.findViewById(R.id.tvNomeUsuarioItemComentario);
        TextView tvComentario = (TextView) fila.findViewById(R.id.tvComentarioItemComentario);
        System.out.println(c.getUsuario());

        tvNomeUsuario.setText(c.getUsuario());
        tvComentario.setText(c.getTexto());

        return fila;
    }
}
