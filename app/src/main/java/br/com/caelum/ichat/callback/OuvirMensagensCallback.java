package br.com.caelum.ichat.callback;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import br.com.caelum.ichat.modelo.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OuvirMensagensCallback implements Callback<Mensagem> {

    public static final String CHAVE_NOVA_MENSAGEM = "nova_mensagem";
    public static final String TAG_OBJ_MENSAGEM = "mensagem";
    private Context context;

    public OuvirMensagensCallback(Context activity) {
        this.context = activity;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {

        if(response.isSuccessful()) {
            Intent intent = new Intent(CHAVE_NOVA_MENSAGEM);
            intent.putExtra(TAG_OBJ_MENSAGEM, response.body());

            /*
            Trabalhando com eventos com LocalBroadcast

            Uma alternativa seria disparar um evento toda vez que recebemos uma
            mensagem e dessa forma, deixamos aberto pra qualquer componente do
            Android receber essa mensagem caso esteja interessado.

            Se você fez o treinamento de Android da Alura, sabe que ao receber
            um SMS ou quando você liga seu Android, ele dispara um evento
            para todo o BroadcastReceiver que esteja interessado possa tratar.

            No nosso caso, não queremos expor nossas mensagens para todo o sistema operacional,
            mas apenas para os receivers da nossa aplicação. Para isso, vamos usar o LocalBroadcastManager.

            Agora, nas Activities que vão receber esses dados, devemos implementar um
            BroadcastReceiver (ver classe MainActivity).
             */
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            localBroadcastManager.sendBroadcast(intent);
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
    }
}
