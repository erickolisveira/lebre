package com.example.lebre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Login extends AppCompatActivity {

    ArrayList<Usuario> usuariosList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.login_button);

        Button esqueceuSenha = findViewById(R.id.esquecerSenha);
        esqueceuSenha.setPaintFlags(esqueceuSenha.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        esqueceuSenha.getPaint().setUnderlineText(true);

        Button cadastro = findViewById(R.id.newUsuario_button);
        cadastro.setPaintFlags(cadastro.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        cadastro.getPaint().setUnderlineText(true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout email = findViewById(R.id.email_input);
                TextInputLayout senha = findViewById(R.id.senha_input);

                String getEmail = email.getEditText().getText().toString();
                String getSenha = senha.getEditText().getText().toString();

                try {
                Gerenciamento_Arquivo.LerUsuarioNoSD(Login.this, usuariosList);
                Usuario usuarioLogin;

                usuarioLogin = Gerenciamento_Arquivo.VerificaUsuarioArrayList(usuariosList, getEmail, getSenha);

                    if (usuarioLogin == null) {
                        Alerta(getApplicationContext(), " Não encontrado, tente novamente ou cadastre-se.");
                    } else {
                        Alerta(getApplicationContext(), "Seja Bem-Vindo!");
                        Bundle args = new Bundle();
                        args.putSerializable("BundleUsuario", usuarioLogin);

                        Intent intent = new Intent(Login.this, CadastrarEmergencia.class);
                        intent.putExtra("Usuario", args);
                        try {
                            startActivity(intent);
                        }catch(Exception e){
                            Alerta(getApplicationContext(), e.getMessage());
                            //finish();
                        }

                    }
                }catch(Exception e){
                    Alerta(getApplicationContext(), e.getMessage());
                }
            }
        });

        try {cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
            }
        });} catch(Exception e){
            Alerta(Login.this, e.getLocalizedMessage());
        }
    }

    public void Alerta(Context context, String mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }
}
