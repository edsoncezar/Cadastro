package caelum.com.br.cadastro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import caelum.com.br.cadastro.adapter.ListaAlunosAdapter;
import caelum.com.br.cadastro.converter.AlunoConverter;
import caelum.com.br.cadastro.converter.EnviaAlunoTask;
import caelum.com.br.cadastro.converter.WebClient;
import caelum.com.br.cadastro.dao.AlunoDAO;
import caelum.com.br.cadastro.vo.Aluno;


public class AlunoActivity extends ActionBarActivity implements Serializable{

    private ListView listaAlunos;
    private List<Aluno> alunos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listaAlunos = (ListView) findViewById(R.id.lv);

//        String alunos1[] = {"Edson", "Carlos", "Felipe"};

//        AlunoDAO dao = new AlunoDAO(this);
//        alunos = dao.getLista();
//        dao.close();

//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
//                , alunos1);

//        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1
//                , alunos);
//
//        listaAlunos.setAdapter(adapter);

//        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Toast.makeText(AlunoActivity.this, "Posição Selecionada : " + position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
//                Toast.makeText(AlunoActivity.this, "Clique Longo : " + aluno.getNome(), Toast.LENGTH_SHORT).show();
//
//                return false;
//
//            }
//        });

        registerForContextMenu(listaAlunos);

        Button novo = (Button) findViewById(R.id.novo);

        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AlunoActivity.this, FormularioActivity.class);

                startActivity(intent);

            }
        });

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent edicao = new Intent(AlunoActivity.this, FormularioActivity.class);

                edicao.putExtra("alunoEdicao", (Aluno) listaAlunos.getItemAtPosition(position));

                startActivity(edicao);

            }

        });



    }

    public void onResume(){
        super.onResume();
        this.carregaLista();
    }


    public void carregaLista(){
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();

//        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1
//                , alunos);

        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        MenuItem deletar = menu.add("Deletar");


        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                new AlertDialog.Builder(AlunoActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar").setMessage("Deseja mesmo deletar ?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlunoDAO dao = new AlunoDAO(AlunoActivity.this);
                        dao.deletar(alunoSelecionado);
                        dao.close();
                        carregaLista();

                    }
                }).setNegativeButton("Não", null).show();


                return true;
            }
        });

        MenuItem ligar = menu.add("Ligar");

        Intent intentLigar = new Intent(Intent.ACTION_CALL);

        intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));

        ligar.setIntent(intentLigar);


        MenuItem mapa = menu.add("Mapa");

        Intent intentMapa = new Intent(Intent.ACTION_VIEW);

        intentMapa.setData(Uri.parse("geo:0,0?z=14&q="+alunoSelecionado.getEndereco()));

        mapa.setIntent(intentMapa);


        MenuItem site = menu.add("Site");

        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String siteAluno = alunoSelecionado.getEmail();

        if(!siteAluno.startsWith("http://")){
            siteAluno = "http://" +siteAluno;
        }

        intentSite.setData(Uri.parse(siteAluno));

        site.setIntent(intentSite);

        MenuItem sms = menu.add("SMS");

        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.putExtra("sms_body", "Teste de mensagem !");

        intentSMS.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));

        sms.setIntent(intentSMS);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
//                AlunoDAO dao = new AlunoDAO(this);
//                List<Aluno> alunos = dao.getLista();
//                dao.close();
//
//                String json = new AlunoConverter().toJSON(alunos);
//
//                WebClient client = new WebClient();
//
//                String resposta = client.post(json);
//
//                Toast.makeText(this, resposta, Toast.LENGTH_LONG).show();

                new EnviaAlunoTask(this).execute();
                return true;

            case R.id.menu_receber_provas:
                Intent provas = new Intent(this, ProvasActivity.class);
                startActivity(provas);
                return true;

            case R.id.menu_mapa:
                Intent mapa = new Intent(this, MostraAlunosActivity.class);
                startActivity(mapa);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
