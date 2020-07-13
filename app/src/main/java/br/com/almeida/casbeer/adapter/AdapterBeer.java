package br.com.almeida.casbeer.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.almeida.casbeer.R;
import br.com.almeida.casbeer.activity.BeerDetailsActivity;
import br.com.almeida.casbeer.activity.MainActivity;
import br.com.almeida.casbeer.helper.BeerDAO;
import br.com.almeida.casbeer.helper.DBHelper;
import br.com.almeida.casbeer.model.Beer;

import static android.content.ContentValues.TAG;

public class AdapterBeer extends RecyclerView.Adapter<AdapterBeer.MyViewHolder> {

    private List<Beer> beers = new ArrayList<>();
    private List<Beer> favBeers = new ArrayList<>();
    private Context context;

    boolean alreadyFav = false;

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

        //recovery data from local database
        BeerDAO beerDAO = new BeerDAO(context);
        favBeers = beerDAO.list();

        Beer beer = beers.get(position);

        for (int i = 0; i < favBeers.size(); i++) {
            Beer beer1 = favBeers.get(i);
            if (beer1.getName().equals(beer.getName())) {
                holder.image_fav_beer.setBackgroundResource(R.drawable.ic_star_yellow_24);
                alreadyFav = true;
            }
        }

        holder.name.setText(beer.getName());
        holder.tag_line.setText(beer.getTagline());

        String url_image = beer.getImage_url();
        Picasso.get().load(url_image).error(R.drawable.ic_baseline_broken_image_24).resize(200, 200).centerInside().into(holder.image_url);

        holder.image_fav_beer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                favBeers = beerDAO.list();

                if (favBeers.size() == 0)
                    alreadyFav = false;
                for (int i = 0; i < favBeers.size(); i++) {
                    Beer beer1 = favBeers.get(i);
                    if (beer1.getName().equals(beer.getName())) {
                        alreadyFav = true;
                        Log.d("tryyy", "onClick: " + alreadyFav);
                    }
                    else{
                        Log.d("tryyy", "onClick: " + alreadyFav);
                        alreadyFav = false;
                    }

                }

                if (!alreadyFav) {
                    BeerDAO beerDAO = new BeerDAO(context);
                    beerDAO.insert(beer);
                    Log.d("tryyy", "onClick: " + "try to insert");
                    holder.image_fav_beer.setBackgroundResource(R.drawable.ic_star_yellow_24);
                } else {
                    //remove from favs
                    Log.d("tryyy", "onClick: " + "try to remove");
                    beerDAO.delete(beer);
                    holder.image_fav_beer.setBackgroundResource(R.drawable.ic_star_grey_24);
                }
            }
        });

        holder.image_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BeerDetailsActivity.class);
                intent.putExtra("beer", beer);

                context.startActivity(intent);
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BeerDetailsActivity.class);
                intent.putExtra("beer", beer);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView tag_line;
        ImageView image_url;
        Button image_fav_beer;
        LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textNameBeer);
            tag_line = itemView.findViewById(R.id.textTaglineBeer);
            image_url = itemView.findViewById(R.id.imageBeer);
            image_fav_beer = itemView.findViewById(R.id.btnFavoriteBeer);
            linearLayout = itemView.findViewById(R.id.linearLayoutVerticalClickId);

        }

    }

}
