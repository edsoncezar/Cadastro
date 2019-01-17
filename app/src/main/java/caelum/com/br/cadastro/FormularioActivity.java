package caelum.com.br.cadastro;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

import caelum.com.br.cadastro.dao.AlunoDAO;
import caelum.com.br.cadastro.helper.FormularioHelper;
import caelum.com.br.cadastro.vo.Aluno;


public class FormularioActivity extends ActionBarActivity {

    FormularioHelper helper;
    private Aluno aluno;

    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Button btn = (Button) findViewById(R.id.btn_formulario);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(FormularioActivity.this, "Você clicou no botão", Toast.LENGTH_SHORT).show();
//
//                finish();
//
//            }
//        });

        if(getIntent().hasExtra("alunoEdicao")){
            Aluno aluno = (Aluno) getIntent().getSerializableExtra("alunoEdicao");
            helper.carregaNoFormulario(aluno);
        }

        Button foto = helper.getFotoButton();

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                //vamos chamar a camera do android

                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Uri localFoto = Uri.fromFile(new File(localArquivoFoto));

                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);



                startActivityForResult(irParaCamera, 123);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == TIRA_FOTO){
            if (resultCode == Activity.RESULT_OK) {
                helper.carregaImagem(this.localArquivoFoto);
            } else {
                this.localArquivoFoto = null;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Aluno aluno = helper.pegaAlunoDoFormulario();
        AlunoDAO dao = new AlunoDAO(this);

        if(aluno.getId() == null) {

            if (helper.temNome()) {

                dao.insere(aluno);
                dao.close();
                finish();

            } else {
                helper.mostraErro();
            }
        } else {
            dao.altera(aluno);
            dao.close();
            finish();
        }

//        switch (id){
//
//            case R.id.menu_formulario_ok:
//
//                Toast.makeText(this, helper.pegaAlunoDoFormulario().getNome() + ": " +helper.pegaAlunoDoFormulario().getNota(), Toast.LENGTH_SHORT).show();
//
//                //finish();
//
//                return false;
//
//            default:
//
//                return super.onOptionsItemSelected(item);
//
//        }


        return false;
    }
}
