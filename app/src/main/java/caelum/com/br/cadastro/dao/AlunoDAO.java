package caelum.com.br.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import caelum.com.br.cadastro.vo.Aluno;

/**
 * Created by android5717 on 26/01/16.
 */
public class AlunoDAO extends SQLiteOpenHelper{

    private static final int VERSAO = 3;
    private static final String TABELA = "Alunos";
    private static final String DATABASE = "CadastroCaelum";

    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String ddl = "CREATE TABLE " + TABELA
                    +" ( id INTEGER PRIMARY KEY, "
                    +" nome TEXT NOT NULL, "
                    +" telefone TEXT, "
                    +" endereco TEXT, "
                    +" email TEXT, "
                    +" caminhoFoto TEXT, "
                    +" nota REAL ); ";

        db.execSQL(ddl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//       String sql = "DROP TABLE IF EXISTS " + TABELA;
        String sql = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT; ";
        db.execSQL(sql);
//        onCreate(db);

    }

    public void insere(Aluno aluno){

        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("email", aluno.getEmail());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());

        getWritableDatabase().insert(TABELA, null, values);

    }


    public void altera(Aluno aluno){

        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("email", aluno.getEmail());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());

        getWritableDatabase().update(TABELA, values, "id=?", new String[] {aluno.getId().toString()});

    }

    public List<Aluno> getLista(){

        List<Aluno> alunos = new ArrayList<Aluno>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + ";", null);

        try {

            while (c.moveToNext()) {

                Aluno aluno = new Aluno();

                aluno.setId(c.getLong(c.getColumnIndex("id")));
                aluno.setNome(c.getString(c.getColumnIndex("nome")));
                aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
                aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
                aluno.setEmail(c.getString(c.getColumnIndex("email")));
                aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
                aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

                alunos.add(aluno);

            }

        }catch (Throwable e){

            e.printStackTrace();

        } finally {

            c.close();
        }


        return alunos;

    }

    public void deletar(Aluno aluno){
        String[] args = {aluno.getId().toString()};

        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    public boolean isAluno(String telefone){

        String[] parametros = {telefone};

        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT telefone FROM " + TABELA + " WHERE telefone = ? ", parametros);

        int total = rawQuery.getCount();
        rawQuery.close();

        return total > 0;
    }

}
