package com.example.gestaodeprodutos.view;

import com.google.gson.annotations.SerializedName;

public class EmpresaRegistro {

    // A tabela tb_cliente não usa os mesmos campos do auth.users (email e password).
    // Esses campos (email, password) vão para a autenticação do Supabase.
    // Os outros campos devem ser inseridos na tabela `tb_cliente`.
    // No entanto, o endpoint /auth/v1/signup do Supabase usa 'email', 'password' e 'data' (metadata).
    // Para inserir na tabela `tb_cliente`, ou usamos uma trigger no banco (o que é comum no Supabase)
    // ou fazemos duas requisições (primeiro cria o user, depois insere na tabela).
    //
    // Vou assumir que você está usando a estrutura padrão de metadata do Supabase para que uma trigger
    // no banco de dados popule a `tb_cliente`.
    //
    // Baseado na imagem que você enviou da tabela `tb_cliente`, os campos são:
    // id_cliente (int8), data_cadastro, email, telefone, endereco, cpf, senha, nome_completo, cnpj.
    
    private String email;
    private String password;

    @SerializedName("data")
    private Data data;

    private static class Data {
        // Mapeando para as colunas da tabela tb_cliente mostradas na imagem
        @SerializedName("cnpj")
        private String cnpj;
        
        @SerializedName("nome_completo")
        private String nomeCompleto; // Antes era nomeResponsavel
        
        @SerializedName("telefone")
        private String telefone;
        
        @SerializedName("endereco")
        private String endereco; // Juntando cidade e estado, ou mapeando para cidade se preferir
        
        // A tabela tem CPF, mas aqui é cadastro de empresa (CNPJ). 
        // Talvez o campo CPF fique vazio ou seja usado para o responsável.
        // Vou manter vazio por enquanto ou null.
        
        // Campos extras para controle interno se necessário, mas focando na tabela:
        @SerializedName("tipo_usuario")
        private String tipoUsuario; 

        public Data(String cnpj, String nomeCompleto, String telefone, String endereco) {
            this.cnpj = cnpj;
            this.nomeCompleto = nomeCompleto;
            this.telefone = telefone;
            this.endereco = endereco;
            this.tipoUsuario = "empresa";
        }
    }

    public EmpresaRegistro(String cnpj, String nomeResponsavel, String email, String phone, String cidade, String estado, String password) {
        this.email = email;
        this.password = password;
        // Concatenando Cidade e Estado para o campo 'endereco' já que a tabela tem apenas 'endereco'
        String enderecoCompleto = cidade + " - " + estado;
        this.data = new Data(cnpj, nomeResponsavel, phone, enderecoCompleto);
    }
}
