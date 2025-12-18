package com.example.gestaodeprodutos.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.gestaodeprodutos.model.Rota;
import com.example.gestaodeprodutos.repository.RotaRepository;

public class RotaViewModel extends AndroidViewModel {

    private RotaRepository rotaRepository;
    private LiveData<Boolean> success;
    private LiveData<String> error;

    public RotaViewModel(@NonNull Application application) {
        super(application);
        rotaRepository = new RotaRepository();
        success = rotaRepository.getSuccess();
        error = rotaRepository.getError();
    }

    public void criarRota(Rota rota, String token) {
        rotaRepository.criarRota(rota, token);
    }

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<String> getError() {
        return error;
    }
}
