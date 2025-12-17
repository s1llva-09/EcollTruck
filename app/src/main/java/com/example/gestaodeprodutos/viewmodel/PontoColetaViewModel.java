package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.PontoColeta;

import java.util.ArrayList;
import java.util.List;

public class PontoColetaViewModel extends ViewModel {

    private final MutableLiveData<List<PontoColeta>> pontosColeta = new MutableLiveData<>();

    public PontoColetaViewModel() {
        carregarPontosColeta();
    }

    public LiveData<List<PontoColeta>> getPontosColeta() {
        return pontosColeta;
    }

    private void carregarPontosColeta() {
        // Dados de exemplo, como na imagem
        List<PontoColeta> lista = new ArrayList<>();
        lista.add(new PontoColeta("Ponto A - Rua das Acácias, 100", "3 lixeiras • ~450kg estimado", "08:00", 3, 450, PontoColeta.Status.CONCLUIDO));
        lista.add(new PontoColeta("Ponto B - Av. Central, 250", "5 lixeiras • ~680kg estimado", "08:30", 5, 680, PontoColeta.Status.CONCLUIDO));
        lista.add(new PontoColeta("Ponto C - Rua das Flores, 123", "4 lixeiras • ~520kg estimado", "09:00", 4, 520, PontoColeta.Status.ATIVO));
        lista.add(new PontoColeta("Ponto D - Bairro Industrial, 500", "6 lixeiras • ~750kg estimado", "09:30", 6, 750, PontoColeta.Status.PENDENTE));
        lista.add(new PontoColeta("Ponto E - Centro, Praça Principal", "8 lixeiras • ~920kg estimado", "10:00", 8, 920, PontoColeta.Status.PENDENTE));

        pontosColeta.setValue(lista);
    }

    public void marcarComoConcluido(PontoColeta pontoParaConcluir) {
        List<PontoColeta> listaAtual = pontosColeta.getValue();
        if (listaAtual != null) {
            boolean proximoAtivado = false;
            for (PontoColeta ponto : listaAtual) {
                if (ponto == pontoParaConcluir) {
                    ponto.setStatus(PontoColeta.Status.CONCLUIDO);
                } else if (ponto.getStatus() == PontoColeta.Status.PENDENTE && !proximoAtivado) {
                    ponto.setStatus(PontoColeta.Status.ATIVO);
                    proximoAtivado = true;
                }
            }
            pontosColeta.setValue(new ArrayList<>(listaAtual)); // Cria nova lista para notificar o observer
        }
    }
}
