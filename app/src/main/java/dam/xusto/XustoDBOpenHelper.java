package dam.xusto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aroig on 3/14/18.
 */

public class XustoDBOpenHelper extends SQLiteOpenHelper{

    public XustoDBOpenHelper(Context context) {
        super(context, "xusto.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (" +
                "  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "  nick VARCHAR(45) NOT NULL,\n" +
                "  contrasinal TEXT NOT NULL,\n" +
                "  nome TEXT NOT NULL,\n" +
                "  apelidos TEXT NOT NULL) "
        );

        db.execSQL("CREATE TABLE tenda (" +
                "  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "  nome TEXT NOT NULL," +
                "  enderezo TEXT," +
                "  codpostal INT," +
                "  latitude TEXT," +
                "  lonxitude TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
