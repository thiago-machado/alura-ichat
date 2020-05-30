package br.com.caelum.ichat.callback;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.greenrobot.eventbus.EventBus;

import br.com.caelum.ichat.event.FailureEvent;
import br.com.caelum.ichat.event.MensagemEvent;
import br.com.caelum.ichat.modelo.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OuvirMensagensCallback implements Callback<Mensagem> {

    public static final String CHAVE_NOVA_MENSAGEM = "nova_mensagem";
    public static final String TAG_OBJ_MENSAGEM = "mensagem";
    private final EventBus eventBus;
    private Context context;

    public OuvirMensagensCallback(EventBus eventBus, Context activity) {
        this.context = activity;
        this.eventBus = eventBus;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {

        if(response.isSuccessful()) {
            Mensagem mensagem = response.body();

            /**
             * Dessa forma, estamos trabalhando orientado a eventos.
             * Quem quiser, vai poder pegar essa requisição, para isso:
             * 1) basta a ter a anotação @Subscribe no método;
             * 2) receber como parâmetro um MensagemEvent.
             */
            eventBus.post(new MensagemEvent(mensagem));
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
        eventBus.post(new FailureEvent(t));
    }
}
