package br.com.caelum.ichat.callback;

import br.com.caelum.ichat.activity.MainActivity;
import br.com.caelum.ichat.modelo.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OuvirMensagensCallback implements Callback<Mensagem> {

    private MainActivity activity;

    public OuvirMensagensCallback(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
        if(response.isSuccessful()) {
            activity.colocaNaLista(response.body());
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
        activity.ouvirMensagem();
    }
}
