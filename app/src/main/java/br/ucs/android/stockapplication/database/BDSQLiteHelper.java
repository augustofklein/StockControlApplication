package br.ucs.android.stockapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

import br.ucs.android.stockapplication.model.Item;
import br.ucs.android.stockapplication.model.Leitura;

public class BDSQLiteHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StockControlDB";


    private static final String TABELA_ITEM = "item";

    private static final String TABELA_LEITURA = "leitura";


    private static final String ID = "id";

    private static final String ITEM_CODIGO = "codigo";
    private static final String ITEM_DESCRICAO = "descricao";
    private static final String ITEM_UNIDADE = "unidade";
    private static final String ITEM_QUANTIDADE = "quantidade";

    private static final String[] COLUNAS_ITEM = {ID, ITEM_CODIGO, ITEM_DESCRICAO, ITEM_UNIDADE, ITEM_QUANTIDADE};


    private static final String LEITURA_ITEM = "idItem";
    private static final String LEITURA_QUANTIDADE = "quantidade";
    private static final String LEITURA_DATA = "data";
    private static final String LEITURA_LATITUDE = "latitude";
    private static final String LEITURA_LONGITUDE = "longitude";

    private static final String[] COLUNAS_LEITURA = {ID, LEITURA_ITEM, LEITURA_QUANTIDADE, LEITURA_DATA, LEITURA_LATITUDE, LEITURA_LONGITUDE};




    public BDSQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABELA_ITEM + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_CODIGO + " TEXT, " +
                ITEM_DESCRICAO + " TEXT, " +
                ITEM_UNIDADE + " TEXT, " +
                ITEM_QUANTIDADE + " REAL " + ") ";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE = "CREATE TABLE " + TABELA_LEITURA + " ( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LEITURA_ITEM + " INTEGER, " +
                LEITURA_DATA + " TEXT, " +
                LEITURA_QUANTIDADE + " REAL, " +
                LEITURA_LATITUDE + " REAL, " +
                LEITURA_LONGITUDE + " REAL " + ") ";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_LEITURA);
        this.onCreate(db);

    }


    public long addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_CODIGO, item.getCodigo());
        values.put(ITEM_DESCRICAO, item.getDescricao());
        values.put(ITEM_UNIDADE, item.getUnidade());
        values.put(ITEM_QUANTIDADE, item.getQuantidade());
        long id = db.insert(TABELA_ITEM, null, values);
        db.close();
        return id;
    }

    private Item cursorToItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getInt(0));
        item.setCodigo(cursor.getString(1));
        item.setDescricao(cursor.getString(2));
        item.setUnidade(cursor.getString(3));
        item.setQuantidade(cursor.getDouble(4));
        return item;
    }

    public Item getItem(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABELA_ITEM
                + " WHERE " + ITEM_CODIGO + " = '" + codigo + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Item item = cursorToItem(cursor);
            return item;
        }
        return null;

    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABELA_ITEM
                + " WHERE " + ID + " = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Item item = cursorToItem(cursor);
            return item;
        }
        return null;

    }

    public ArrayList<Item> getAllItens() {
        ArrayList<Item> listaItens = new ArrayList<Item>();
        String query = "SELECT * FROM " + TABELA_ITEM
                + " ORDER BY " + ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = cursorToItem(cursor);
                listaItens.add(item);
            } while (cursor.moveToNext());
        }
        return listaItens;
    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_CODIGO, item.getCodigo());
        values.put(ITEM_DESCRICAO, item.getDescricao());
        values.put(ITEM_UNIDADE, item.getUnidade());
        values.put(ITEM_QUANTIDADE, item.getQuantidade());
        int i = db.update(TABELA_ITEM, //tabela
                values, // valores
                ID + " = ?", // colunas para comparar
                new String[] { String.valueOf(item.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_ITEM, //tabela
                ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
        return i; // número de linhas excluídas
    }



    public long addLeitura(Leitura leitura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEITURA_ITEM, leitura.getItem().getId());
        values.put(LEITURA_DATA, leitura.getData().getTime());
        values.put(LEITURA_QUANTIDADE, leitura.getQuantidade());
        values.put(LEITURA_LATITUDE, leitura.getLatitude());
        values.put(LEITURA_LONGITUDE, leitura.getLongitude());
        long id = db.insert(TABELA_LEITURA, null, values);
        db.close();
        return id;
    }

    private Leitura cursorToLeitura(Cursor cursor) {
        Leitura leitura = new Leitura();
        leitura.setId(cursor.getInt(0));
        leitura.setItem(getItem(cursor.getInt(1)));
        leitura.setData(new Date(Long.parseLong(cursor.getString(2))));
        leitura.setQuantidade(cursor.getDouble(3));
        leitura.setLatitude(cursor.getDouble(4));
        leitura.setLongitude(cursor.getDouble(5));
        return leitura;
    }

    public Leitura getLeitura(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUNAS_LEITURA + " FROM " + TABELA_LEITURA
                + " WHERE " + ID + " = '" + id + "'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Leitura leitura = cursorToLeitura(cursor);
            return leitura;
        }
        return null;

    }

    public ArrayList<Leitura> getAllLeituras(int idItem) {
        ArrayList<Leitura> listaItens = new ArrayList<Leitura>();
        String query = "SELECT " + COLUNAS_LEITURA + " FROM " + TABELA_LEITURA
                + " WHERE " + LEITURA_ITEM + " = '" + idItem + "'"
                + " ORDER BY " + ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Leitura leitura = cursorToLeitura(cursor);
                listaItens.add(leitura);
            } while (cursor.moveToNext());
        }
        return listaItens;
    }

    public ArrayList<Leitura> getAllLeituras() {
        ArrayList<Leitura> listaItens = new ArrayList<Leitura>();
        String query = "SELECT " + COLUNAS_LEITURA + " FROM " + TABELA_LEITURA
                + " ORDER BY " + ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Leitura leitura = cursorToLeitura(cursor);
                listaItens.add(leitura);
            } while (cursor.moveToNext());
        }
        return listaItens;
    }

    public int updateLeitura(Leitura leitura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEITURA_DATA, leitura.getData().getTime());
        values.put(LEITURA_QUANTIDADE, leitura.getQuantidade());
        values.put(LEITURA_LATITUDE, leitura.getLatitude());
        values.put(LEITURA_LONGITUDE, leitura.getLongitude());
        int i = db.update(TABELA_LEITURA, //tabela
                values, // valores
                ID + " = ?", // colunas para comparar
                new String[] { String.valueOf(leitura.getId()) }); //parâmetros
        db.close();
        return i; // número de linhas modificadas
    }

    public int deleteLeitura(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_LEITURA, //tabela
                ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
        return i; // número de linhas excluídas
    }

    public int deleteLeiturasItem(int idItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_LEITURA, //tabela
                LEITURA_ITEM + " = ?", new String[] { String.valueOf(idItem) });
        db.close();
        return i; // número de linhas excluídas
    }

}
