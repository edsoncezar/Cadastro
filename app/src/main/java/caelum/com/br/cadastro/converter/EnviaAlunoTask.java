package caelum.com.br.cadastro.converter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import caelum.com.br.cadastro.dao.AlunoDAO;
import caelum.com.br.cadastro.vo.Aluno;

/**
 * Created by android5717 on 28/01/16.
 */
public class EnviaAlunoTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progress;

    public EnviaAlunoTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> lista = dao.getLista();

        String listaJSON = new AlunoConverter().toJSON(lista);

        String jsonResposta = new WebClient().post(listaJSON);

        return jsonResposta;

    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context,"Aguarde...","Envio de dados para Web", true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        progress.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT);
    }
}
