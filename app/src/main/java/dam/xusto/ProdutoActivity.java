package dam.xusto;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;

public class ProdutoActivity extends Activity implements TarefaDescargaXML.Cliente {

    private static final int DESCARGA_COMENTARIOS = 10;
    private TextView tvProdutoPrezo;
    private TextView tvProdutoDescricion;
    private TextView tvProdutoNome;
    private ListView lvProdutoComentario;
    private Produto p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        tvProdutoNome = (TextView) findViewById(R.id.tvProdutoNome);
        tvProdutoDescricion = (TextView) findViewById(R.id.tvProdutoDescricion);
        tvProdutoPrezo = (TextView) findViewById(R.id.tvProdutoPrezo);
        lvProdutoComentario = (ListView) findViewById(R.id.lvProdutoComentarios);

        p = new Produto(getIntent().getExtras().getLong("idProduto"),
                getIntent().getExtras().getString("nome"),
                getIntent().getExtras().getString("descricion"),
                getIntent().getExtras().getDouble("prezo"));

        tvProdutoNome.setText(p.getNome());
        tvProdutoDescricion.setText(p.getDescricion());
        tvProdutoPrezo.setText(p.getPrezo()+"");

        encherLvComentarios();
    }

    private void encherLvComentarios() {
        new TarefaDescargaXML(this, DESCARGA_COMENTARIOS).execute(Servizo.urlDescargaComentariosP(p.getId()));
    }


    @Override
    public void recibirDocumento(Document resultado, int tipoDescarga) {
        if (tipoDescarga == DESCARGA_COMENTARIOS) {
            Comentario.cargarProdutos(resultado, lvProdutoComentario);
        }
    }
}
