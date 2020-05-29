package br.com.caelum.ichat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.caelum.ichat.modelo.Mensagem;
import caelum.com.br.ichat_alura.R;

import java.util.List;

/**
 * Para baixar o server: https://s3.amazonaws.com/caelum-online-public/android-chat/files/ichat-api.jar
 */
public class MensagemAdapter extends BaseAdapter {

    private List<Mensagem> mensagens;
    private Context context;
    private int idDoCliente;

    public MensagemAdapter(int idDoCliente, List<Mensagem> mensagens, Context context) {
        this.mensagens = mensagens;
        this.context = context;
        this.idDoCliente = idDoCliente;
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Mensagem getItem(int i) {
        return mensagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if(view == null) {
            view = inflater.inflate(R.layout.mensagem, viewGroup, false);
        }

        TextView texto = view.findViewById(R.id.tv_texto);

        Mensagem mensagem = getItem(i);

        if (idDoCliente != mensagem.getId()) {
            view.setBackgroundColor(Color.CYAN);
        }

        texto.setText(mensagem.getText());

        return view;
    }
}