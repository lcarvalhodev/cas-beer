package br.com.almeida.casbeer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.almeida.casbeer.R;
import br.com.almeida.casbeer.model.Beer;

public class AdapterBeer extends RecyclerView.Adapter<AdapterBeer.MyViewHolder> {

    private List<Beer> beers = new ArrayList<>();
    private Context context;

    public AdapterBeer(List<Beer> beers, Context context) {
        this.beers = beers;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_beer, parent, false);
        return new AdapterBeer.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Beer beer = beers.get(position);
        holder.name.setText(beer.getName());
        holder.tag_line.setText(beer.getTagline());

        String url_image = beer.getImage_url();
        Picasso.get().load(url_image).resize(200, 200).centerInside().into(holder.image_url);

    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView tag_line;
        ImageView image_url;
        Button image_fav_beer;

        public MyViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.textNameBeer);
            tag_line = itemView.findViewById(R.id.textTaglineBeer);
            image_url = itemView.findViewById(R.id.imageBeer);
            image_fav_beer = itemView.findViewById(R.id.btnFavoriteBeer);
        }


    }

}
