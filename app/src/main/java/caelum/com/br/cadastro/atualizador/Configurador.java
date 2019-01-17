package caelum.com.br.cadastro.atualizador;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by android5717 on 29/01/16.
 */
public class Configurador implements GoogleApiClient.ConnectionCallbacks {


    private AtualizadorDeLocalizacao atual;

    public Configurador(AtualizadorDeLocalizacao atual) {
        this.atual = atual;
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest req = LocationRequest.create();

        req.setInterval(2000);
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        req.setSmallestDisplacement(50);

        atual.inicia(req);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
