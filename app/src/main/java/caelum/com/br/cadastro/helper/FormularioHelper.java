package caelum.com.br.cadastro.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import caelum.com.br.cadastro.FormularioActivity;
import caelum.com.br.cadastro.R;
import caelum.com.br.cadastro.vo.Aluno;

/**
 * Created by android5717 on 26/01/16.
 */
public class FormularioHelper {

    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText email;
    private RatingBar nota;
    private Aluno aluno;

    private ImageView foto;
    private Button fotoButton;


    public FormularioHelper(FormularioActivity activity) {

        nome = (EditText) activity.findViewById(R.id.nome_formulario);
        telefone = (EditText) activity.findViewById(R.id.telefone_formulario);
        endereco = (EditText) activity.findViewById(R.id.endereco_formulario);
        email = (EditText) activity.findViewById(R.id.email_formulario);
        nota = (RatingBar) activity.findViewById(R.id.nota_formulario);

        foto = (ImageView) activity.findViewById(R.id.formulario_foto);
        fotoButton = (Button) activity.findViewById(R.id.formulario_foto_button);

        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario(){

        aluno.setEmail(email.getText().toString());
        aluno.setNome(nome.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setNota(Double.valueOf(nota.getRating())*2);
        aluno.setCaminhoFoto((String) foto.getTag());

        return aluno;

    }

    public void carregaNoFormulario(Aluno aluno){

        email.setText(aluno.getEmail());
        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        endereco.setText(aluno.getEndereco());
        nota.setProgress(aluno.getNota().intValue());

        this.aluno = aluno;

        if(aluno.getCaminhoFoto() != null){
            this.carregaImagem(aluno.getCaminhoFoto());
        }

    }

    public void carregaImagem(String localArquivoFoto){

        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, imagemFoto.getWidth(),300, true);

        foto.setImageBitmap(imagemFotoReduzida);
        foto.setTag(localArquivoFoto);
        foto.setScaleType(ImageView.ScaleType.FIT_XY);


    }

    public boolean temNome(){
        return !nome.getText().toString().isEmpty();
    }

    public void mostraErro(){
        nome.setError("Campo nome não pode ser vazio !");
    }

    public Button getFotoButton() {
        return fotoButton;
    }

}
