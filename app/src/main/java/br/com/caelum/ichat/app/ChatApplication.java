package br.com.caelum.ichat.app;

import android.app.Application;

import br.com.caelum.ichat.ChatComponent;
import br.com.caelum.ichat.DaggerChatComponent;

/**
 * Nós usamos uma instância dessa classe em MainActivity.
 *
 * Dessa maneita, temos acesso ao ChatComponent e consequentemente ao ChatService via
 * @Inject (injeção de dependência).
 *
 * Obs: não se esquecer de declarar essa classe no Manifest.xml
 *
 */
public class ChatApplication extends Application {

    private ChatComponent chatComponent;

    /**
     * Como essa classe é um Singleton (já que extende de Application),
     * vamos criar uma instÇancia de ChatComponent através da implementação
     * no método abaixo.
     *
     * Obs: DaggerChatComponent somente é criado após fazermos um build no projeto
     * após a criação do @Module e @Component.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        chatComponent = DaggerChatComponent.builder().build();
    }

    /**
     * Retornando a instância de ChatComponent quando necessário.
     * @return
     */
    public ChatComponent getComponent() {
        return chatComponent;
    }
}
