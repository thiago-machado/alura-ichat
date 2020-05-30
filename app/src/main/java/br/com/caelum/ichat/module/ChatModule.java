package br.com.caelum.ichat.module;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.com.caelum.ichat.service.ChatService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classes especialistas em fornecer as instâncias a serem injetadas.
 *
 * Acessar a interface ChatComponent para consultar como o "vínculo" entre
 * o Provider e o Inject são realizados. No caso, como estamos vinculando
 * a instância a ser injetada com a classe que irá recebê-la.
 */
@Module
public class ChatModule {

    private final Context context;

    public ChatModule(Context context) {
        this.context = context;
    }

    /**
     * Provedor de dependências
     *
     * @return
     */
    @Provides
    public ChatService getChatService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ChatService.class);
    }

    @Provides
    public Picasso getPicasso() {
        return new Picasso.Builder(context).build();
    }

    @Provides
    public EventBus getEventBus() {
        return EventBus.builder().build();
    }
}
