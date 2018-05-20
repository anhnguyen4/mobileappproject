package com.example.sipln.mobile_dict_app.Presenters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sipln.mobile_dict_app.R;

import java.util.List;

import com.example.sipln.mobile_dict_app.DB.Word;

public class RVWordEntryAdapter extends RecyclerView.Adapter<RVWordEntryAdapter.WordEntryViewHolder>{

    private List<Word> wordList;

    public RVWordEntryAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    @NonNull
    @Override
    public WordEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_entry, parent, false );
        WordEntryViewHolder wordEntryViewHolder = new WordEntryViewHolder(view);
        return wordEntryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordEntryViewHolder holder, int position) {
        holder.wordText.setText(wordList.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class WordEntryViewHolder extends RecyclerView.ViewHolder {
        private CardView wordEntry;
        private TextView wordText;
        public WordEntryViewHolder(View itemView) {
            super(itemView);
            wordEntry =  itemView.findViewById(R.id.cv_word_entry);
            wordText  =  itemView.findViewById(R.id.tv_word_text);
        }
    }
}
