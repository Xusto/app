package dam.xusto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by aroig on 3/16/18.
 */

public class ArrayAdapterProduto extends ArrayAdapter<Produto> {
    private final int resource;

    public ArrayAdapterProduto(@NonNull Context context, @NonNull Produto[] produtos) {
        super(context, R.layout.item_produto, produtos);
        resource = R.layout.item_produto;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View fila = inflater.inflate(resource, null);

        Produto p = getItem(position);

        TextView tvItemProdutoNome = (TextView) fila.findViewById(R.id.tvItemProdutoNome);
        TextView tvItemProdutoDescricion = (TextView) fila.findViewById(R.id.tvItemProdutoDescricion);
        TextView tvItemProdutoPrezo = (TextView) fila.findViewById(R.id.tvItemProdutoPrezo);
        TextView tvIDProd = (TextView) fila.findViewById(R.id.tvIDProd);   // Con este textview invisible coseguimos almacenar o id do producto para pasarllo á correpondente actividade

        tvItemProdutoNome.setText(p.getNome());
        tvItemProdutoDescricion.setText(p.getDescricion());
        tvItemProdutoPrezo.setText(p.getPrezo() + "");
        tvIDProd.setText(p.getId()+ "");

        return fila;
    }
}
