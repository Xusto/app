package dam.xusto;

import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * Created by aroig on 3/14/18.
 */

public class Produto {
    private long id;
    private String nome;
    private String descricion;
    private double prezo;

    public Produto(long id, String nome, String descricion, double prezo) {
        this.setId(id);
        this.setNome(nome);
        this.setDescricion(descricion);
        this.setPrezo(prezo);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public double getPrezo() {
        return prezo;
    }

    public void setPrezo(double prezo) {
        this.prezo = prezo;
    }

    public static void cargarProdutos(Document resultado, ListView lvProdutos) {
        Element raiz = resultado.getDocumentElement();
        Produto[] produtos;

        NodeList nl = raiz.getElementsByTagName("producto");
        produtos = new Produto[nl.getLength()];

        for (int i = 0; i < nl.getLength(); i++) {
            NamedNodeMap atributos = nl.item(i).getAttributes();

            long idProducto = Integer.parseInt(atributos.getNamedItem("id_producto").getNodeValue());
            String nome = atributos.getNamedItem("nome").getNodeValue();
            String descricion = atributos.getNamedItem("descricion").getNodeValue();
            double prezo = Double.parseDouble(atributos.getNamedItem("prezo").getNodeValue());

            produtos[i] = new Produto(idProducto, nome, descricion, prezo);
        }

        lvProdutos.setAdapter(new ArrayAdapterProduto(lvProdutos.getContext(),produtos));
    }
}
