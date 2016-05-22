package will.tesler.perfectday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.asymmetricadapter.adapter.UniversalAdapter;
import will.tesler.perfectday.placetype.PlaceType;
import will.tesler.perfectday.placetype.PlaceTypeIds;

public class PlacesActivity extends AppCompatActivity {

    @Bind(R.id.recyclerview_places) RecyclerView mRecyclerView;

    private UniversalAdapter mAdapter = new UniversalAdapter();
    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

        layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.register(PlaceType.class, PlaceTypeTransformer.class);

        int[] placeTypeIds = PlaceTypeIds.getSupportedPlaceTypeIds();
        String[] placeTypeNames = getResources().getStringArray(R.array.placetypes);
        for (int i = 0; i < placeTypeIds.length; i++) {
            mAdapter.add(new PlaceType(placeTypeIds[i], placeTypeNames[i]));
        }
    }


    public static class PlaceTypeTransformer extends UniversalAdapter.Transformer<PlaceType> {

        @Bind(R.id.text) TextView mText;

        public PlaceTypeTransformer(ViewGroup parent) {
            super(R.layout.item_place, parent);
            ButterKnife.bind(this, getView());
        }

        @Override
        protected void transform(PlaceType model) {
            mText.setText(model.getName());
        }
    }

}
