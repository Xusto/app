package dam.xusto;

import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * Created by aroig on 3/16/18.
 */

public class Comentario {
    private String usuario;
    private String texto;

    public Comentario(String usuario, String texto) {
        this.usuario = usuario;
        this.texto = texto;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public static void cargarProdutos(Document resultado, ListView lvComentarios) {
        Element raiz = resultado.getDocumentElement();
        Comentario[] comentarios;

        NodeList nl = raiz.getElementsByTagName("comentario");
        comentarios = new Comentario[nl.getLength()];
        for (int i = 0; i < nl.getLength(); i++) {
            NamedNodeMap atributos = nl.item(i).getAttributes();

            String usuario = atributos.getNamedItem("usuario").getNodeValue();
            String texto = atributos.getNamedItem("texto").getNodeValue();

            comentarios[i] = new Comentario(usuario, texto);
        }

        lvComentarios.setAdapter(new ArrayAdapterComentario(lvComentarios.getContext(),comentarios));
    }
}
