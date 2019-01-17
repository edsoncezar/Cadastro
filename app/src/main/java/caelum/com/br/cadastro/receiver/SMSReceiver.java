package caelum.com.br.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import caelum.com.br.cadastro.R;
import caelum.com.br.cadastro.dao.AlunoDAO;

/**
 * Created by android5717 on 28/01/16.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        Object[] mensagens = (Object[]) bundle.get("pdus");
        byte[] mensagem = (byte[]) mensagens[0];

        SmsMessage sms = SmsMessage.createFromPdu(mensagem);
        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);

        if(dao.isAluno(telefone)){

            Toast.makeText(context, "SMS recebido do aluno de telefone: "+telefone, Toast.LENGTH_SHORT).show();

            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

        dao.close();

    }
}
