package com.algaworks.brewer.service.event.cerveja;

import com.algaworks.brewer.storage.FotosStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CervejaListener {
    @Autowired
    private FotosStorage storage;

    @EventListener(condition = "#evento.temFoto()")
    public void cervejaSalva(CervejaSalvaEvent evento) {
        System.out.println("****  Cerveja salva, e tem foto sim " + evento.getCerveja().getFoto());
        this.storage.salvarMesmo(evento.getCerveja().getFoto());
    }
}
