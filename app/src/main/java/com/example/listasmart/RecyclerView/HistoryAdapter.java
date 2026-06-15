package com.example.listasmart.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listasmart.R;
import com.example.listasmart.database.model.AnalysisHistory;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<AnalysisHistory> historyList;

    public HistoryAdapter(List<AnalysisHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_analysis_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        AnalysisHistory item = historyList.get(position);

        holder.txtNomeLista.setText(item.getNomeLista());
        holder.txtData.setText(item.getData());
        holder.txtMercado.setText(item.getMercado());
        holder.txtEconomia.setText(item.getEconomia());
    }

    @Override
    public int getItemCount() {
        return historyList != null ? historyList.size() : 0;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtNomeLista;
        TextView txtData;
        TextView txtMercado;
        TextView txtEconomia;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNomeLista = itemView.findViewById(R.id.txtNomeLista);
            txtData = itemView.findViewById(R.id.txtData);
            txtMercado = itemView.findViewById(R.id.txtMercado);
            txtEconomia = itemView.findViewById(R.id.txtEconomia);
        }
    }
    public void updateList(List<AnalysisHistory> newList) {
        this.historyList = newList;
        notifyDataSetChanged();
    }
}