package dam.xusto;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by qexame13 on 01/03/2018.
 */

class Servizo {

    private static String urlBase = "http://10.0.2.2/xusto/";

    public static URL urlDescargaTendas() {
        String cadeaURL = urlBase + "buscartendas.php";
        return construirURL(cadeaURL);
    }

    public static URL urlDescargaProdutos(long idTenda) {
        String cadeaURL = urlBase + "buscarproductos.php?idtenda=" + idTenda;
        return construirURL(cadeaURL);
    }


    public static URL urlInserirProduto(long idtenda, String nome, String descricion, double prezo) {
        String cadeaURL = urlBase + "inserir_produto.php?idtenda=" + idtenda +
                "&nome=" + nome + "&descript=" + encode(descricion) +
                "&prezo=" + encode(prezo+"");
       //  System.out.println(cadeaURL);
        return construirURL(cadeaURL);
    }

    public static URL urlDescargaComentariosP(long idProduto) {
        String cadeaURL = urlBase + "buscarcomentarios.php?idproducto=" + idProduto;
       // System.out.println(cadeaURL);
        return construirURL(cadeaURL);
    }

    private static URL construirURL(String cadeaUrl) {
        URL url = null;

        try {
            url = new URL(cadeaUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    private static String encode(String cadeaEntrada) {
        try {
            return URLEncoder.encode(cadeaEntrada, "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
