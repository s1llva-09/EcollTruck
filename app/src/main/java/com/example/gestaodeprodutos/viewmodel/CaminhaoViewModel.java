package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.Caminhao;
import com.example.gestaodeprodutos.repository.CaminhaoRepository;

import java.util.List;

public class CaminhaoViewModel extends ViewModel {

    private final CaminhaoRepository repository;
    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> saveError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> deleteError = new MutableLiveData<>();
    private final MutableLiveData<List<Caminhao>> caminhoes = new MutableLiveData<>();
    private final MutableLiveData<String> genericError = new MutableLiveData<>();


    public CaminhaoViewModel() {
        this.repository = new CaminhaoRepository();
    }

    public LiveData<Boolean> getSaveSuccess() {
        return saveSuccess;
    }

    public LiveData<String> getSaveError() {
        return saveError;
    }

    public LiveData<Boolean> getDeleteSuccess() {
        return deleteSuccess;
    }

    public LiveData<String> getDeleteError() {
        return deleteError;
    }

    public LiveData<List<Caminhao>> getCaminhoes() {
        return caminhoes;
    }

    public LiveData<String> getGenericError() {
        return genericError;
    }

    public void saveCaminhao(String token, Caminhao caminhao) {
        repository.saveCaminhao(token, caminhao, saveSuccess, saveError);
    }

    public void deleteCaminhao(String token, int id) {
        repository.deleteCaminhao(token, id, deleteSuccess, deleteError);
    }

    public void fetchCaminhoes(String token) {
        repository.fetchCaminhoes(token, caminhoes, genericError);
    }
}
