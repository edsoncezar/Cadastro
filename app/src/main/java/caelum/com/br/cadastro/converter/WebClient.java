package caelum.com.br.cadastro.converter;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by android5717 on 28/01/16.
 */
public class WebClient {

    private static final String URLcaelum = "http://www.caelum.com.br/mobile";


    public String post(String json){

        String retorno = "";

        try{

            URL urlC = new URL(URLcaelum);
            HttpURLConnection connection = (HttpURLConnection) urlC.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            PrintStream saida = new PrintStream(connection.getOutputStream());

            saida.println(json);

            connection.getDoInput();
            connection.getDoOutput();

            retorno = new Scanner(connection.getInputStream()).next();

            connection.connect();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;

    }
}
