package br.com.caelum.ichat.event;

public class FailureEvent {

    public Throwable throwable;

    public FailureEvent(Throwable throwable) {
        this.throwable = throwable;
    }
}
