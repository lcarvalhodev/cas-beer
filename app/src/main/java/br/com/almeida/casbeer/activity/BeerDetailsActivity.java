package br.com.almeida.casbeer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        getSupportActionBar().setTitle("Beer Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recovery object beers
        Bundle data = getIntent().getExtras();

        if (data != null) {
            Beer beer = (Beer) data.getSerializable("beer");

            //init components
            imageBeerDetails = findViewById(R.id.imageBeerDetails);
            textViewNameDetails = findViewById(R.id.textNameBeerDetails);
            textViewTaglineDetails = findViewById(R.id.textTaglineBeerDetails);
            textViewDescriptionDetails = findViewById(R.id.textViewDescriptionDetails);

            String url_image = beer.getImage_url();
            Picasso.get().load(url_image).resize(200, 200).centerInside().into(imageBeerDetails);

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