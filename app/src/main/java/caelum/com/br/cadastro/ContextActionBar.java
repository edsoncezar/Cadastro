package caelum.com.br.cadastro;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import caelum.com.br.cadastro.dao.AlunoDAO;
import caelum.com.br.cadastro.vo.Aluno;

/**
 * Created by android5717 on 27/01/16.
 */
public class ContextActionBar implements ActionMode.Callback {


    private AlunoActivity activity;
    private Aluno alunoSelecionado;

    public ContextActionBar(AlunoActivity activity, Aluno alunoSelecionado) {
        this.activity = activity;
        this.alunoSelecionado = alunoSelecionado;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                new AlertDialog.Builder(activity).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar").setMessage("Deseja mesmo deletar ?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlunoDAO dao = new AlunoDAO(activity);
                        dao.deletar(alunoSelecionado);
                        dao.close();
                        activity.carregaLista();

                    }
                }).setNegativeButton("Não", null).show();


                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
