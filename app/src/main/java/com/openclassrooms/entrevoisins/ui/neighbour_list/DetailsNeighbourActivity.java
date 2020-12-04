package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class DetailsNeighbourActivity extends AppCompatActivity {

    private ImageView mImgArrow;
    private RelativeLayout mRelaPicture;
    private TextView mTxtName;
    private CardView mCardcontact;
    private TextView mTxtName2;
    private TextView mTxtadresse;
    private TextView mTxtnumber;
    private TextView mTxtfacebook;
    private CardView mCardaboutme;
    private TextView mTxtapropos;
    private FloatingActionButton mFloatfavoris;
    private ImageView mBackground;
    private NeighbourApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        mBackground = findViewById(R.id.activity_details_neighbour_img_background);
        mImgArrow = findViewById(R.id.activity_details_neighbour_img_arrow);
        mRelaPicture = findViewById(R.id.activity_details_neighbour_rela_picture);
        mTxtName = findViewById(R.id.activity_details_neighbour_txt_name);
        mCardcontact = findViewById(R.id.activity_details_neighbour_card_contact);
        mTxtName2 = findViewById(R.id.activity_details_neighbour_txt_name2);
        mTxtadresse = findViewById(R.id.activity_details_neighbour_txt_adresse);
        mTxtnumber = findViewById(R.id.activity_details_neighbour_txt_number);
        mTxtfacebook = findViewById(R.id.activity_details_neighbour_txt_facebook);
        mCardaboutme = findViewById(R.id.activity_details_neighbour_card_aboutme);
        mTxtapropos = findViewById(R.id.activity_details_neighbour_txt_apropos);
        mFloatfavoris = findViewById(R.id.activity_details_neighbour_float_favoris);
        mApiService = DI.getNeighbourApiService();

        // --- récupération de l'intent
        Intent i = getIntent();
        // --- récupération de l'extra contenant l'objet neighbour
        Neighbour neighbour = (Neighbour) i.getSerializableExtra("neighbour");

        mTxtName.setText(neighbour.getName());
        mTxtfacebook.setText("http://www.facebook.com/" + neighbour.getName());
        mTxtName2.setText(neighbour.getName());
        mTxtadresse.setText(neighbour.getAddress());
        mTxtapropos.setText(neighbour.getAboutMe());
        mTxtnumber.setText(neighbour.getPhoneNumber());

        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .into(mBackground);

        mImgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        // --- l'étal initial dE l'étoile
        // --- si la liste favoris contient neighbour
        if (mApiService.getNeighboursFavorite().contains(neighbour)) {
            mFloatfavoris.setColorFilter(Color.YELLOW);
            mFloatfavoris.setSelected(true);
        }
        // --- sinon
        else {
            mFloatfavoris.setColorFilter(Color.BLACK);
            mFloatfavoris.setSelected(false);
        }

        mFloatfavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // --- si neighbour est en favoris
                if (mFloatfavoris.isSelected()) {
                    mFloatfavoris.setColorFilter(Color.BLACK);
                    mFloatfavoris.setSelected(false);
                    mApiService.deleteNeighbourFavorite(neighbour);

                }
                // --- sinon
                else {
                    mFloatfavoris.setColorFilter(Color.YELLOW);
                    mFloatfavoris.setSelected(true);
                    mApiService.createNeighbourFavorite(neighbour);

                }
            }
        });



    }
}

