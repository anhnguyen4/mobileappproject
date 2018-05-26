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


public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private Context context ;
    private List<String> data = new ArrayList<>();

    public RecyclerViewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.job_entry, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.tv_jobInfos.setText(data.get(position));
        holder.btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent save = new Intent(context, DBService.class);
                save.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                save.setAction("SAVE");
                save.putExtra("data", data.get(position));
                context.startService(save);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_jobInfos;
        Button btn_save;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_jobInfos =  itemView.findViewById(R.id.tv_jobInfos);
            btn_save = itemView.findViewById(R.id.btn_save);
        }
    }

}

