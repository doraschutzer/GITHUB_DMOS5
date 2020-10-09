package br.edu.dmos5.github_dmos5.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.github_dmos5.R;
import br.edu.dmos5.github_dmos5.model.Repository;

public class ListaRepositoriosAdapter extends RecyclerView.Adapter<ListaRepositoriosAdapter.RepositoryViewHolder> {

    private List<Repository> repositorios;
    private final Context context;

    public ListaRepositoriosAdapter(Context context, List<Repository> repositorios) {
        this.context = context;
        this.repositorios = repositorios;
    }

    @Override
    public ListaRepositoriosAdapter.RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_repositorio, parent, false);
        return new RepositoryViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ListaRepositoriosAdapter.RepositoryViewHolder holder, int position) {
        Repository repository = repositorios.get(position);

        holder.textNomeRepositorio.setText(repository.getName());
    }

    @Override
    public int getItemCount() {
        if (repositorios != null){
            return repositorios.size();
        }
        return 0;
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView textNomeRepositorio;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            textNomeRepositorio = itemView.findViewById(R.id.text_nome_repositorio);
        }
    }

    public void atualizar(List<Repository> repositorios) {
        this.repositorios = repositorios;
        notifyDataSetChanged();
    }

}
