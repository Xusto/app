package dam.xusto;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by aroig on 3/14/18.
 */

public class Tenda {

    private static final int DESCARGA_TENDAS = 10;
    // o id é autoincremental
    private long id;
    private String nome;
    private String enderezo;
    private int codPostal;
    private String latitude;
    private String lonxitude;
    private static final String taboa = "tenda";
    private static final String[] columnas = {"_id", "nome", "enderezo", "codpostal", "latitude", "lonxitude"};

    public Tenda() {}


    public Tenda(long id, String nome, String enderezo, int codPostal, String latitude, String lonxitude) {
        this.id = id;
        this.nome = nome;
        this.enderezo = enderezo;
        this.codPostal = codPostal;
        this.latitude = latitude;
        this.lonxitude = lonxitude;
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

    public String getEnderezo() {
        return enderezo;
    }

    public void setEnderezo(String enderezo) {
        this.enderezo = enderezo;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLonxitude() {
        return lonxitude;
    }

    public void setLonxitude(String lonxitude) {
        this.lonxitude = lonxitude;
    }


    public static Cursor getAllCursor(){
        return MainActivity.getDb().query(taboa,columnas,null,null,null,null,null);
    }

    public static void crearDendeXML(Document resultado) {
        Element raiz = resultado.getDocumentElement();

        NodeList nl = raiz.getElementsByTagName("tenda");
        Tenda t;

        for (int i = 0; i < nl.getLength(); i++) {
            NamedNodeMap atributos = nl.item(i).getAttributes();

            long id = Long.parseLong(atributos.getNamedItem("id_tenda").getNodeValue());
            String nome = atributos.getNamedItem("nome").getNodeValue();
            String enderezo = atributos.getNamedItem("enderezo").getNodeValue();
            int codPostal = Integer.parseInt(atributos.getNamedItem("codpostal").getNodeValue());
            String latitude = atributos.getNamedItem("latitude").getNodeValue();
            String lonxitude = atributos.getNamedItem("lonxitude").getNodeValue();

            t = new Tenda(id, nome, enderezo, codPostal, latitude, lonxitude);

            t.gardar();
        }
    }

    public static Tenda buscarTendaPorID(long id) {
        Tenda t = null;

        Cursor c = MainActivity.getDb().query(taboa, columnas, "_id = ?", new String[]{id + ""},null, null, null);

        if (c.moveToFirst()) {
            String nome = c.getString(1);
            String enderezo = c.getString(2);
            int codPostal = c.getInt(3);
            String latitude = c.getString(4);
            String lonxitude = c.getString(5);

            t = new Tenda(id, nome, enderezo, codPostal, latitude, lonxitude);
        }

        return t;
    }

    private void gardar() {
        ContentValues cv = new ContentValues();
        cv.put(columnas[0],this.id);
        cv.put(columnas[1],this.nome);
        cv.put(columnas[2],this.enderezo);
        cv.put(columnas[3],this.codPostal);
        cv.put(columnas[4],this.latitude);
        cv.put(columnas[5],this.lonxitude);

        MainActivity.getDb().insert(taboa, null, cv);
    }


}

