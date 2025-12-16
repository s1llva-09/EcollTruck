package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.repository.CaminhaoRepository;

import java.util.List;

public class CaminhaoViewModel extends ViewModel {

    private final CaminhaoRepository repository;
    private final MutableLiveData<List<Caminhao>> caminhoes = new MutableLiveData<>();

    public CaminhaoViewModel() {
        repository = new CaminhaoRepository();
    }

    public LiveData<List<Caminhao>> getCaminhoes() {
        return caminhoes;
    }

    public void carregarCaminhoes(String token) {
        repository.listarCaminhoes(caminhoes, token);
    }

    public LiveData<Boolean> inserirCaminhao(Caminhao caminhao, String token) {
        return repository.inserirCaminhao(caminhao, token);
    }

    public LiveData<Boolean> atualizarCaminhao(Caminhao caminhao, String token) {
        return repository.atualizarCaminhao(caminhao, token);
    }

    public LiveData<Boolean> deletarCaminhao(int id, String token) {
        return repository.deletarCaminhao(id, token);
    }
}
