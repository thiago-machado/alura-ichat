package br.com.caelum.ichat.module;

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
}
