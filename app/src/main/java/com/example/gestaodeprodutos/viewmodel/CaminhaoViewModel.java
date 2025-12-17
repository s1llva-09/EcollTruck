package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.repository.CaminhaoRepository;

public class CaminhaoViewModel extends ViewModel {

    private final CaminhaoRepository repository;
    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> saveError = new MutableLiveData<>();

    public CaminhaoViewModel() {
        this.repository = new CaminhaoRepository();
    }

    public LiveData<Boolean> getSaveSuccess() {
        return saveSuccess;
    }

    public LiveData<String> getSaveError() {
        return saveError;
    }

    public void saveCaminhao(String token, Caminhao caminhao) {
        repository.saveCaminhao(token, caminhao, saveSuccess, saveError);
    }
}
