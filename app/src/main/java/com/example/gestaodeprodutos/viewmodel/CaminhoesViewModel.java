package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.repository.CaminhaoRepository;

import java.util.List;

public class CaminhoesViewModel extends ViewModel {

    private final CaminhaoRepository repository;
    private final MutableLiveData<List<Caminhao>> caminhoes = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public CaminhoesViewModel() {
        this.repository = new CaminhaoRepository();
    }

    public LiveData<List<Caminhao>> getCaminhoes() {
        return caminhoes;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchCaminhoes(String token) {
        repository.fetchCaminhoes(token, caminhoes, error);
    }
}
