package io.github.seibelsabrina.sheltermefb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShelterDetailViewActivity extends AppCompatActivity {

    TextView detailAddress;
    TextView detailCapacity;
    TextView detailLatitude;
    TextView detailLongitude;
    TextView detailNotes;
    TextView detailPhoneNumber;
    TextView detailRestrictions;
    TextView detailShelterName;
    Button buttonDetailBookRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail_view);

        Bundle bundle = getIntent().getExtras();
        final Shelter s = (Shelter) bundle.getSerializable("shelter");
        final Person person = (Person) bundle.getSerializable("person");

        detailAddress = (TextView) findViewById(R.id.textViewDetailAddressInfo);
        detailCapacity = (TextView) findViewById(R.id.textViewDetailCapacityInfo);
        detailLatitude = (TextView) findViewById(R.id.textViewDetailLatitudeInfo);
        detailLongitude = (TextView) findViewById(R.id.textViewDetailLongitudeInfo);
        detailNotes = (TextView) findViewById(R.id.textViewDetailNotesInfo);
        detailPhoneNumber = (TextView) findViewById(R.id.textViewDetailPhoneNumberInfo);
        detailRestrictions = (TextView) findViewById(R.id.textViewDetailRestrictionsInfo);
        detailShelterName = (TextView) findViewById(R.id.textViewDetailShelterName);
        buttonDetailBookRoom = (Button) findViewById(R.id.buttonDetailBookRoom);

        detailAddress.setText(s.getAddress());
        detailCapacity.setText(s.getCapacity());
        detailLatitude.setText((s.getLatitude()).toString());
        detailLongitude.setText((s.getLongitude()).toString());
        detailNotes.setText(s.getNotes());
        detailPhoneNumber.setText(s.getPhoneNumber());
        detailRestrictions.setText(s.getRestrictions());
        detailShelterName.setText(s.getShelterName());

        buttonDetailBookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShelterDetailViewActivity.this, BookRoomActivity.class);
                intent.putExtra("shelter", s);
                intent.putExtra("person", person);
                startActivity(intent);
            }
        });
    }
}
