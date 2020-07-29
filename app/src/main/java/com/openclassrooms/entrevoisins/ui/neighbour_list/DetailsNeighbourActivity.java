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



        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String url = i.getStringExtra("url");
        String address = i.getStringExtra("address");
        String aboutme = i.getStringExtra("aboutme");
        String phonenumber = i.getStringExtra("phonenumber");
        Neighbour neighbour = (Neighbour) i.getSerializableExtra("neighbour");

        mTxtName.setText(name);
        mTxtfacebook.setText("http://www.facebook.com/" + name);
        mTxtName2.setText(name);
        mTxtadresse.setText(address);
        mTxtapropos.setText(aboutme);
        mTxtnumber.setText(phonenumber);

        Glide.with(this)
                .load(url)
                .into(mBackground);

        mImgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        if (mApiService.getNeighboursFavorite().contains(neighbour)) {
            mFloatfavoris.setColorFilter(Color.YELLOW);
            mFloatfavoris.setSelected(true);
        }
        else {
            mFloatfavoris.setColorFilter(Color.BLACK);
            mFloatfavoris.setSelected(false);
        }

        mFloatfavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFloatfavoris.isSelected()) {
                    mFloatfavoris.setColorFilter(Color.BLACK);
                    mFloatfavoris.setSelected(false);
                    mApiService.deleteNeighbourFavorite(neighbour);

                }
                else {
                    mFloatfavoris.setColorFilter(Color.YELLOW);
                    mFloatfavoris.setSelected(true);
                    mApiService.createNeighbourFavorite(neighbour);

                }
            }
        });



    }
}

