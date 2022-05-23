package com.alexsandro.tcc02.letsbora.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.alexsandro.tcc02.letsbora.R;
import com.alexsandro.tcc02.letsbora.model.Viagem;
import java.util.List;

public class ViagemAdapter extends RecyclerView.Adapter<ViagemAdapter.MyViewHolder> {

    private List<Viagem> viagens;

    public ViagemAdapter(List<Viagem> listaViagens) {
        this.viagens = listaViagens;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viagem_detalhe, parent, false);

        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Viagem viagem = viagens.get(position);
        holder.horarioViagem.setText(viagem.getHorario());
        holder.localOrigem.setText(viagem.getLocalOrigen());
        holder.localDestino.setText(viagem.getLocalDestino());
        holder.imagemOrigem.setImageResource(viagem.getImagemOrigem());
        holder.imagemDestino.setImageResource(viagem.getImagemDestino());

    }

    @Override
    public int getItemCount() {

        return viagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView horarioViagem;
        private TextView localOrigem;
        private TextView localDestino;
        private ImageView imagemOrigem;
        private ImageView imagemDestino;
//        private Button iniciarViagem;

        public MyViewHolder(View itemView) {
            super(itemView);
            horarioViagem = itemView.findViewById(R.id.horarioViagem);
            localOrigem = itemView.findViewById(R.id.localOrigem);
            localDestino = itemView.findViewById(R.id.localDestino);
            imagemOrigem = itemView.findViewById(R.id.imagemOrigem);
            imagemDestino = itemView.findViewById(R.id.imagemDestino);
//            iniciarViagem = itemView.findViewById(R.id.iniciarViagem);
        }
    }

}
