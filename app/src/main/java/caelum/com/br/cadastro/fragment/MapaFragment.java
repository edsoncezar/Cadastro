package caelum.com.br.cadastro.fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import caelum.com.br.cadastro.atualizador.AtualizadorDeLocalizacao;
import caelum.com.br.cadastro.dao.AlunoDAO;
import caelum.com.br.cadastro.local.Localizador;
import caelum.com.br.cadastro.vo.Aluno;

/**
 * Created by android5717 on 29/01/16.
 */
public class MapaFragment extends SupportMapFragment {


    @Override
    public void onResume() {
        super.onResume();


        Localizador loc = new Localizador(getActivity());

        LatLng local = loc.getCoordenada("Rua Vergueiro 3185 Vila Mariana");

        centraliza(local);

        AlunoDAO dao = new AlunoDAO(getActivity());
        List<Aluno> alunos = dao.getLista();
        dao.close();


        for(Aluno aluno: alunos){

            LatLng coord = loc.getCoordenada(aluno.getEndereco());

            if(coord != null){
                MarkerOptions marc = new MarkerOptions().position(coord).title(aluno.getNome()).snippet(aluno.getEndereco());
                getMap().addMarker(marc);
            }
        }

        AtualizadorDeLocalizacao atual = new AtualizadorDeLocalizacao(getActivity(),this);
    }


    public void centraliza(LatLng local){
        GoogleMap map = getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11));
    }
}
