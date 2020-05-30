package br.com.caelum.ichat.event;

import br.com.caelum.ichat.modelo.Mensagem;

public class MensagemEvent {

    public Mensagem mensagem;

    public MensagemEvent (Mensagem mensagem) {
        this.mensagem = mensagem;
    }
}
