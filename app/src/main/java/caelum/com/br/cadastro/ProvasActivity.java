package caelum.com.br.cadastro;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import caelum.com.br.cadastro.fragment.DetalhesProvaFragment;
import caelum.com.br.cadastro.fragment.ListaProvasFragment;
import caelum.com.br.cadastro.vo.Prova;


/**
 * Created by android5717 on 29/01/16.
 */
public class ProvasActivity extends ActionBarActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(isTablet()){
            transaction.replace(R.id.provas_view, new ListaProvasFragment())
                    .replace(R.id.provas_detalhes, new DetalhesProvaFragment());
        } else {
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }



        transaction.commit();

    }

    public boolean isTablet(){
        return  getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionaProvas(Prova prova){

        FragmentManager manager = getSupportFragmentManager();

        if(isTablet()){
            DetalhesProvaFragment detalhesProva = (DetalhesProvaFragment) manager.findFragmentById(R.id.provas_detalhes);
            detalhesProva.populaCamposComDados(prova);
        } else {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova", prova);
            DetalhesProvaFragment detalhesProva = new DetalhesProvaFragment();
            detalhesProva.setArguments(argumentos);

            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.provas_view, detalhesProva);

            transaction.addToBackStack(null);
            transaction.commit();
        }


    }
}
