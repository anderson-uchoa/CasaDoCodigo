package br.com.caelum.casadocodigo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.activity.LivroActivity;
import br.com.caelum.casadocodigo.modelo.Livro;

/**
 * Created by matheus on 22/07/15.
 */
public class LivrosAdapter extends BaseAdapter {

    private final String LIVRO = "livro";
    private List<Livro> livros;
    private Activity activity;

    public LivrosAdapter(List<Livro> livros, Activity activity) {

        this.livros = livros;
        this.activity = activity;

    }


    private class ViewHolder{

        final TextView nomeLivro;
        final TextView descricaoLivro;
        final Button adicionarCarrinho;
        final ImageView imagemLivro;


        public ViewHolder(View view) {

            nomeLivro = (TextView) view.findViewById(R.id.nome_livro);
            descricaoLivro = (TextView) view.findViewById(R.id.desc_livro);
            adicionarCarrinho = (Button) view.findViewById(R.id.botao_comprar);
            imagemLivro = (ImageView) view.findViewById(R.id.imagem_livro);

        }


    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return livros.get(position).getId();
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            if (position % 2 == 0) {
                view = View.inflate(activity, R.layout.livro_item, null);
            } else {
                view = View.inflate(activity, R.layout.livro_item_impar, null);
            }
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        final Livro livro = (Livro) getItem(position);

        ImageView imagemLivro = holder.imagemLivro;
        TextView nomeLivro =  holder.nomeLivro;
        TextView descricaoLivro = holder.descricaoLivro;
        Button adicionarCarrinho = holder.adicionarCarrinho;

        if (livro.getImagemUrl() != null) {

            Picasso.with(activity).
                    load(livro.getImagemUrl()).
                    fit().
                    into(imagemLivro);
        }

        nomeLivro.setText(livro.getNomeLivro());
        descricaoLivro.setText(livro.getDescricaoLivro());

        adicionarCarrinho.setText(view.getResources().getString(R.string.comprar));

        adicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View alertView = View.inflate(activity, R.layout.opcao_compra, null);

                RadioButton virtual = (RadioButton) alertView.findViewById(R.id.valor_virtual);
                virtual.setText("Virtual : R$ " + livro.getValorVirtual());

                RadioButton fisico = (RadioButton) alertView.findViewById(R.id.valor_fisico);
                fisico.setText("Fisico : R$ " + livro.getValorFisico());

                RadioButton juntos = (RadioButton) alertView.findViewById(R.id.valor_juntos);
                juntos.setText("Juntos : R$ " + livro.getValorDoisJuntos());



                new AlertDialog.Builder(activity).setView(alertView).setTitle(livro.getNomeLivro()).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostraLivro = new Intent(activity, LivroActivity.class);
                mostraLivro.putExtra(LIVRO, livro);
                activity.startActivity(mostraLivro);
            }
        });

        return view;
    }
}
