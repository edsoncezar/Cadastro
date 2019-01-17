package caelum.com.br.cadastro.atualizador;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import caelum.com.br.cadastro.fragment.MapaFragment;

/**
 * Created by android5717 on 29/01/16.
 */
public class AtualizadorDeLocalizacao implements LocationListener {

    private MapaFragment mapa;
    private GoogleApiClient client;

    public AtualizadorDeLocalizacao(Context context, MapaFragment mapa) {
        this.mapa = mapa;

        Configurador config = new Configurador(this);


        this.client = new GoogleApiClient.Builder(context).addApi(LocationServices.API).addConnectionCallbacks(config).build();
        this.client.connect();
    }

    public void onLocationChanged(Location local){
        double lat = local.getLatitude();
        double lon = local.getLongitude();

        LatLng loc = new LatLng(lat, lon);
        this.mapa.centraliza(loc);

    }

    public void inicia(LocationRequest req){
        LocationServices.FusedLocationApi.requestLocationUpdates(client, req, this);
        this.client.disconnect();
    }

    public void cancela(){
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        this.client.disconnect();
    }

}
