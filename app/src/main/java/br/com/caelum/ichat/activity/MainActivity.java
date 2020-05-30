package br.com.caelum.ichat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import br.com.caelum.ichat.app.ChatApplication;
import br.com.caelum.ichat.component.ChatComponent;
import br.com.caelum.ichat.adapter.MensagemAdapter;
import br.com.caelum.ichat.callback.EnviarMensagemCallback;
import br.com.caelum.ichat.callback.OuvirMensagensCallback;
import br.com.caelum.ichat.modelo.Mensagem;
import br.com.caelum.ichat.service.ChatService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import caelum.com.br.ichat_alura.R;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;
    private List<Mensagem> mensagens = new ArrayList<>();

    /**
     * As Views estão sendo injetadas via ButterKnife.
     * Os atributos não podem ser private.
     *
     * Observar que até mesmo o clique do botão pode ser
     * realizado via ButterKnife.
     */
    @BindView(R.id.activity_botao_enviar)
    Button botaoEnviar;

    @BindView(R.id.activity_mensagem_enviar)
    EditText mensagemParaEnviar;

    @BindView(R.id.mensagem)
    ListView listaDeMensagens;

    @BindView(R.id.iv_avatar_usuario)
    ImageView avatar;

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

    @Inject
    Picasso picasso;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * ButterKnife é uma biblioteca que permite termos acesso as Views de um layout
         * através da injeção de dependências. Basta observarmos os atributos que estão
         * nessa classe.
         *
         * IMPORTANTE: ButterKnife é uma bibloteca DEPRECIADA.
         * NO site oficial, estão pedindo para utilizar o View Binding:
         * https://developer.android.com/topic/libraries/view-binding
         */
        ButterKnife.bind(this);

        /**
         * Utilizando o Picasso para criar uma Avatar e inserir no ImageView
         */
        picasso.with(this).load("https://api.adorable.io/avatars/285/" + idDoCliente + ".png").into(avatar);

        ChatApplication chatApplication = (ChatApplication) getApplication();
        chatComponent = chatApplication.getComponent();
        chatComponent.inject(this); // Acionando o Dagger que permitirá a Injeção de dependência de ChatService

        mensagens = new ArrayList<>();

        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(adapter);

        ouvirMensagem();

        /*
        Criando um LocalBroadcastManager, que receberá as requisições enviadas de um
        LocalBroadcastManager (ver classe OuvirMensagensCallback).

        LocalBroadcastManager exige a criação de um BroadcastReceiver.
         */
        criarBroadcastReceiver();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(broadcastReceiver, new IntentFilter(OuvirMensagensCallback.CHAVE_NOVA_MENSAGEM));
    }

    /**
     *
     */
    private void criarBroadcastReceiver() {

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Mensagem mensagem = (Mensagem) intent.getSerializableExtra(OuvirMensagensCallback.TAG_OBJ_MENSAGEM);
                colocaNaLista(mensagem);
            }
        };
    }

    /**
     * Clique do botão sendo injetado via ButterKnife.
     *
     */
    @OnClick(R.id.activity_botao_enviar)
    public void enviarMensagem() {
        Call<Void> postCall = chatService.enviar(new Mensagem(idDoCliente, mensagemParaEnviar.getText().toString()));
        postCall.enqueue(new EnviarMensagemCallback());
        mensagemParaEnviar.setText(null);
    }

    /**
     *
     * @param mensagem
     */
    public void colocaNaLista(Mensagem mensagem) {
        mensagens.add(mensagem);
        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(adapter);
        ouvirMensagem();
    }

    /**
     *
     */
    public void ouvirMensagem() {
        Call<Mensagem> getCall = chatService.ouvirMensagens();
        getCall.enqueue(new OuvirMensagensCallback(this));
    }

    /**
     * Caso a Activity seja recriada (ao virar a tela, por exemplo), precisamos evitar que
     * o LocalBroadcastManager tenha mais de uma insância. Para isso, vamos remover o registro
     * do LocalBroadcastManager criado anteriormente, para que um novo seja criado ao passar por
     * onCreate.
     *
     */
    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
