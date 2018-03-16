package dam.xusto;

// TODO: Engadir imaxes (logos)
// TODO: Implementación da barra de búsqueda
// TODO: Implementar insercións: tendas, e comentarios (xa feito en produtos)

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Document;

import java.io.Serializable;

public class MainActivity extends Activity implements TarefaDescargaXML.Cliente, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private static final int DESCARGA_TENDAS = 10;
    private static final int DESCARGA_PRODUTOS = 20;
    private static final int INSERIR_PRODUTO = 30;
    private static SQLiteDatabase db;
    private TabHost tabs;
    private ListView lvTendas;
    private Spinner spProductosTenda;
    private SearchView svTendas;
    private SimpleCursorAdapter sca;
    private ListView lvProdutos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creación da base de datos
        if (db == null) {
            db = new XustoDBOpenHelper(this).getWritableDatabase();
        }

        tabs = (TabHost)findViewById(R.id.tabHost);
        tabs.setup();

        lvTendas = (ListView) findViewById(R.id.lvTendas);
        svTendas = (SearchView) findViewById(R.id.svTendas);
        spProductosTenda = (Spinner) findViewById(R.id.spProductosTenda);
        lvProdutos = (ListView) findViewById(R.id.lvProdutos);


        TabHost.TabSpec spec = tabs.newTabSpec("tabProdutos");
        spec.setContent(R.id.tabProdutos);
        spec.setIndicator(getResources().getString(R.string.tabSpecProduto));
        tabs.addTab(spec);


        spec = tabs.newTabSpec("tabTendas");
        spec.setContent(R.id.tabTendas);
        spec.setIndicator(getResources().getString(R.string.tabSpecTenda));
        tabs.addTab(spec);



        encherLvTendasSpProductosTenda(Tenda.getAllCursor());
        lvTendas.setTextFilterEnabled(true);    // para a búsqueda con searchview
        lvTendas.setOnItemClickListener(this);
        lvProdutos.setOnItemClickListener(this);
        svTendas.setOnQueryTextListener(this);
        spProductosTenda.setOnItemSelectedListener(this);
    }

    // Invocamos ao menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.miEngadir == item.getItemId()) {
            // lóxica para engadir unha tenda ou produto
            if (tabs.getCurrentTabTag().equals("tabTendas")) {
                // AlertDialog para a inserción dunha tenda
                Toast.makeText(this, "Inda sen implementar", Toast.LENGTH_SHORT).show();

            } else {
                // AlertDialog para a inserción dun produto
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle(getResources().getString(R.string.adEngadirProdutoTitulo));

                adb.setView(getLayoutInflater().inflate(R.layout.alertdialog_engadir_produto,null));



                adb.setNegativeButton(R.string.adBtnCancelar,null);
                adb.setPositiveButton(R.string.adBtnAceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        AlertDialog ad = (AlertDialog)dialog;

                        TextView tvNome = (TextView) ad.findViewById(R.id.adEngadirProdutoNome);
                        TextView tvDescricion = (TextView) ad.findViewById(R.id.adEngadirProdutoDescricion);
                        TextView tvPrezo = (TextView) ad.findViewById(R.id.adEngadirProdutoPrezo);

                        new TarefaDescargaXML(MainActivity.this, INSERIR_PRODUTO)
                                .execute(Servizo.urlInserirProduto(
                                        spProductosTenda.getSelectedItemId(),
                                        tvNome.getText().toString(),
                                        tvDescricion.getText().toString(),
                                        Double.parseDouble(tvPrezo.getText().toString()))
                                );
                    }
                });
                adb.show();
            }
        }

        return true;
    }

    // MÉTODOS PARA A IMPLEMENTACIÓN DE SEARCHVIEW
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void encherLvTendasSpProductosTenda(Cursor c) {
        if (c.getCount() == 0) {
            // lanzar descarga
            new TarefaDescargaXML(this, DESCARGA_TENDAS).execute(Servizo.urlDescargaTendas());

        } else {
            String[] from = new String[]{"nome","enderezo"};
            int[] to = new int[]{android.R.id.text1, android.R.id.text2};

            sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,c,from,to,0);
            lvTendas.setAdapter(sca);

            // Cambiamos o sca para que, no spinner de produtos, non se vexan as direccións
            SimpleCursorAdapter sca2 = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,c,from,to,0);
            spProductosTenda.setAdapter(sca2);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        // lanzar descarga
        new TarefaDescargaXML(this, DESCARGA_PRODUTOS).execute(Servizo.urlDescargaProdutos(id));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void recibirDocumento(Document resultado, int tipoDescarga) {
        if(resultado == null) {
            Toast.makeText(this, "Problemas de conexión" , Toast.LENGTH_LONG).show();
            return;
        }

        if (tipoDescarga == DESCARGA_TENDAS) {
            Tenda.crearDendeXML(resultado);
            encherLvTendasSpProductosTenda(Tenda.getAllCursor());
        }

        if (tipoDescarga == DESCARGA_PRODUTOS) {
            // Polo pouco peso que supón esta descarga, farase directamente dende o servidor
            Produto.cargarProdutos(resultado, lvProdutos);
        }

        if (tipoDescarga == INSERIR_PRODUTO) {
            if (resultado.getDocumentElement().getTagName().equals("exito")) {
                Toast.makeText(this, "Producto almacenado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro na inserción", Toast.LENGTH_SHORT).show();
            }

            // Volvemos a lanzar a descarga para recargar os productos co novo inserido:
            new TarefaDescargaXML(MainActivity.this,DESCARGA_PRODUTOS)
                    .execute(Servizo.urlDescargaProdutos(spProductosTenda.getSelectedItemId()));
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // Se o click procede da lista de tendas, iniciamos TendaActivity
        if (adapterView.getId() == R.id.lvTendas) {
            Intent intent = new Intent(this, TendaActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);

        } else if (adapterView.getId() == R.id.lvProdutos) {
            // Senón, iniciamos ProdutoActivity
            TextView tvIDProd = view.findViewById(R.id.tvIDProd);
            TextView tvNome = view.findViewById(R.id.tvItemProdutoNome);
            TextView tvDescricion = view.findViewById(R.id.tvItemProdutoDescricion);
            TextView tvPrezo = view.findViewById(R.id.tvItemProdutoPrezo);

            Intent intent = new Intent(this, ProdutoActivity.class);
            intent.putExtra("idProduto",Long.parseLong(tvIDProd.getText().toString()));
            intent.putExtra("nome",tvNome.getText().toString());
            intent.putExtra("descricion",tvDescricion.getText().toString());
            intent.putExtra("prezo",Double.parseDouble(tvPrezo.getText().toString()));

            startActivity(intent);
        }

    }

}
