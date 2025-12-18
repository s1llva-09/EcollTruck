package com.example.gestaodeprodutos.model;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    
    @SerializedName("access_token")
    private String accessToken;
    
    @SerializedName("token_type")
    private String tokenType;
    
    @SerializedName("refresh_token")
    private String refreshToken;
    
    @SerializedName("user")
    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public User getUser() {
        return user;
    }

    public static class User {
        @SerializedName("id")
        private String id;
        
        @SerializedName("email")
        private String email;
        
        // O Supabase pode retornar 'app_metadata' ou 'user_metadata'
        // Se 'perfil' estiver dentro de user_metadata, precisaremos ajustar.
        // Por enquanto mantendo como estava, mas encapsulado.
        private String perfil;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getPerfil() {
            return perfil;
        }
    }
}
