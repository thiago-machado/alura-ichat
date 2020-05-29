package br.com.caelum.ichat.component;

import br.com.caelum.ichat.activity.MainActivity;
import br.com.caelum.ichat.adapter.MensagemAdapter;
import br.com.caelum.ichat.module.ChatModule;
import dagger.Component;

/**
 * Interface responsável por ligar o Provider (quem provê a instância desejada)
 * ao Inject (classe que receberá a instância).
 *
 * Precisamos realizar um "build" do projeto antes de finalizarmos as configurações.
 * Pois com o @Module e @Componente criados, precisamos criar uma classe chamada, por exemplo,
 * DaggerChatComponent. Essa classe somente é criada na compilação!!!
 *
 * Feito isso, precisamos criar uma classe que extenda de Application para finalizarmos as configurações.
 *
 * Acessar ChatApplication para consultar os últimos ajustes.
 */
@Component(modules= ChatModule.class)
public interface ChatComponent {

    /**
     * Estamos dizendo para injetar o módulo "ChatModule.class" na classe
     * MainActivity.
     *
     * Caso seja preciso injetar em outras classes, basta criar outros métodos
     * como esse, mas com outro nome. Na verdade, o nome do método "inject" é
     * uma sugestão da comunidade. Ou seja, pode ser qualquer nome.
     *
     * @param mainActivity
     */
    void inject(MainActivity mainActivity);
    void inject(MensagemAdapter adapter);
}
