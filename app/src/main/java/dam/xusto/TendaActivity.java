package dam.xusto;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TendaActivity extends Activity {
    private Tenda t;
    private TextView tvTendaNome;
    private TextView tvTendaEnderezo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenda);

        tvTendaNome = (TextView) findViewById(R.id.tvTendaNome);
        tvTendaEnderezo = (TextView) findViewById(R.id.tvTendaEnderezo);

        t = Tenda.buscarTendaPorID(getIntent().getExtras().getLong("id"));

        tvTendaNome.setText(t.getNome());
        tvTendaEnderezo.setText(t.getEnderezo());

    }
}
