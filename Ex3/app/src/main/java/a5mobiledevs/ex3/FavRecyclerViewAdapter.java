package a5mobiledevs.ex3;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FavRecyclerViewAdapter  extends RecyclerView.Adapter<FavRecyclerViewAdapter.FavRecyclerViewHolder>{

    private Context context ;
    private List<String> data = new ArrayList<>();

    public FavRecyclerViewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public FavRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favjob_entry, parent, false);

        return new FavRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavRecyclerViewHolder holder, final int position) {
        holder.tv_favjobInfos.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FavRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_favjobInfos;
        public FavRecyclerViewHolder(View itemView) {
            super(itemView);
            tv_favjobInfos =  itemView.findViewById(R.id.tv_favjobInfos);

        }
    }

}

