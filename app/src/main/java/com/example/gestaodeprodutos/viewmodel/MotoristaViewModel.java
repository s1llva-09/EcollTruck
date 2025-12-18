package com.example.gestaodeprodutos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gestaodeprodutos.model.Motorista;
import com.example.gestaodeprodutos.repository.MotoristaRepository;

import java.util.List;

public class MotoristaViewModel extends ViewModel {

    private final MotoristaRepository repository;
    private final MutableLiveData<List<Motorista>> motoristas = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> saveSuccess = new MutableLiveData<>();

    public MotoristaViewModel() {
        this.repository = new MotoristaRepository();
    }

    public LiveData<List<Motorista>> getMotoristas() {
        return motoristas;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getSaveSuccess() {
        return saveSuccess;
    }

    public void fetchMotoristas(String token) {
        repository.fetchMotoristas(token, motoristas, error);
    }

    public void searchMotoristas(String token, String query) {
        repository.searchMotoristas(token, query, motoristas, error);
    }

    public void saveMotorista(String token, Motorista motorista) {
        repository.saveMotorista(token, motorista, saveSuccess, error);
    }
}
