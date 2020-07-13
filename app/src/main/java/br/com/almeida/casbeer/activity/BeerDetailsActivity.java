package br.com.almeida.casbeer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.almeida.casbeer.R;
import br.com.almeida.casbeer.model.Beer;

public class BeerDetailsActivity extends AppCompatActivity {

    private ImageView imageBeerDetails;
    private TextView textViewNameDetails;
    private TextView textViewTaglineDetails;
    private TextView textViewDescriptionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_details);
        getSupportActionBar().setTitle(R.string.tituloActionBarDetalhes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recovery object beers
        Bundle data = getIntent().getExtras();

        if (data != null) {
            Beer beer = (Beer) data.getSerializable("beer");

            Log.d("tryyy", "recoveryBeers: " + beer.getName() + "s");
            //init components
            imageBeerDetails = findViewById(R.id.imageBeerDetails);
            textViewNameDetails = findViewById(R.id.textNameBeerDetails);
            textViewTaglineDetails = findViewById(R.id.textTaglineBeerDetails);
            textViewDescriptionDetails = findViewById(R.id.textViewDescriptionDetails);

            String url_image = beer.getImage_url();
            Picasso.get().load(url_image).error(R.drawable.ic_baseline_broken_image_24).resize(280, 280).centerInside().into(imageBeerDetails);

            textViewNameDetails.setText(beer.getName());
            textViewTaglineDetails.setText(beer.getTagline());
            textViewDescriptionDetails.setText(beer.getDescription());
        }

        else{
            Toast.makeText(this, "Connection problem", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}