package caelum.com.br.cadastro.local;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by android5717 on 29/01/16.
 */
public class Localizador {

    private Geocoder geo;

    public Localizador(Context ctx) {
        geo = new Geocoder(ctx);
    }

    public LatLng getCoordenada(String end){

        try{

            List<Address> res;
            res = geo.getFromLocationName(end, 1);

            if(!res.isEmpty()){

                Address result = res.get(0);
                return new LatLng(result.getLatitude(), result.getLongitude());

            } else {
                return null;
            }




        } catch (IOException e){
            return null;
        }

    }
}
