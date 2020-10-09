package br.edu.dmos5.github_dmos5.view;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.github_dmos5.R;
import br.edu.dmos5.github_dmos5.api.GitHubService;
import br.edu.dmos5.github_dmos5.model.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 64;

    private EditText editNomeUsuario;
    private Button btnBuscar;
    private RecyclerView listaRepositorios;

    private ListaRepositoriosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNomeUsuario = findViewById(R.id.edit_nome_usuario);
        btnBuscar = findViewById(R.id.btn_buscar);
        listaRepositorios = findViewById(R.id.lista_repositorios);

        adapter = new ListaRepositoriosAdapter(this, null);
        listaRepositorios.setAdapter(adapter);

        btnBuscar.setOnClickListener(v -> {
            if (verificarPermissao()) {
                String usuario = editNomeUsuario.getText().toString();
                if (!usuario.isEmpty()) {
                    buscarRepositorios(usuario);
                } else {
                    Toast.makeText(this, "Informe um usuário", Toast.LENGTH_SHORT)
                            .show();
                }

            } else {
                solicitaPermissao();
            }
        });
    }


    private boolean verificarPermissao() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitaPermissao() {
        final Activity activity = this;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(getApplicationContext())
                    .setMessage(R.string.msg_permissao)
                    .setPositiveButton(R.string.permitir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{
                                            Manifest.permission.INTERNET
                                    },
                                    REQUEST_PERMISSION
                            );
                        }
                    })
                    .setNegativeButton(R.string.nao_permitir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{
                            Manifest.permission.INTERNET
                    },
                    REQUEST_PERMISSION
            );
        }
    }

    private void buscarRepositorios(String usuario) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        final Call<List<Repository>> repos = service.listarRepositorios(usuario);

        repos.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful()) {
                    List<Repository> repositorios = response.body();
                    adapter.atualizar(repositorios);
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Toast.makeText(
                        MainActivity.this,
                        R.string.erro_buscar_usuario,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

}