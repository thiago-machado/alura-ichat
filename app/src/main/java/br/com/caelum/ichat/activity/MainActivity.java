package br.com.caelum.ichat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import br.com.caelum.ichat.app.ChatApplication;
import br.com.caelum.ichat.ChatComponent;
import br.com.caelum.ichat.adapter.MensagemAdapter;
import br.com.caelum.ichat.callback.EnviarMensagemCallback;
import br.com.caelum.ichat.callback.OuvirMensagensCallback;
import br.com.caelum.ichat.modelo.Mensagem;
import br.com.caelum.ichat.service.ChatService;
import caelum.com.br.ichat_alura.R;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;
    private List<Mensagem> mensagens = new ArrayList<>();
    private ListView listaDeMensagens;
    private ChatComponent chatComponent;

    /**
     * Pedindo ao Dagger para injetar um ChatService.
     *
     * Acessar ChatModule para verificar como está sendo criado essa
     * instância de ChatService.
     *
     * Obs: não podemos deixar esse atributo como private!
     *
     *
     * SOBRE O DAGGER
     *
     * O Dagger é um framework de injeção de dependências para o Android,
     * que é open source e mantido pela Google. Ele veio com o objetivo de
     * substituir frameworks antigos que dependiam de Reflection em suas soluções,
     * sendo o Dagger bem mais rápido por ser completamente estático e fazer a
     * injeção de dependência em tempo de compilação.
     */
    @Inject
    ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ChatApplication chatApplication = (ChatApplication) getApplication();
        chatComponent = chatApplication.getComponent();
        chatComponent.inject(this); // Acionando o Dagger que permitirá a Injeção de dependência de ChatService


        listaDeMensagens = findViewById(R.id.mensagem);

        mensagens = new ArrayList<>();

        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(adapter);

        Button botaoEnviar = findViewById(R.id.activity_botao_enviar);
        EditText mensagemParaEnviar = findViewById(R.id.activity_mensagem_enviar);

        ouvirMensagem();

        botaoEnviar.setOnClickListener((view) -> {
            Call<Void> postCall = chatService.enviar(new Mensagem(idDoCliente, mensagemParaEnviar.getText().toString()));
            postCall.enqueue(new EnviarMensagemCallback());
        });
    }

    public void colocaNaLista(Mensagem mensagem) {
        mensagens.add(mensagem);
        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(adapter);
        ouvirMensagem();
    }

    public void ouvirMensagem() {
        Call<Mensagem> getCall = chatService.ouvirMensagens();
        getCall.enqueue(new OuvirMensagensCallback(this));
    }
}
