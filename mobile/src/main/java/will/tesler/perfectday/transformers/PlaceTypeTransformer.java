package will.tesler.perfectday.transformers;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.asymmetricadapter.adapter.UniversalAdapter;
import will.tesler.perfectday.R;
import will.tesler.perfectday.placetype.PlaceType;
import will.tesler.perfectday.ui.SquareFrameLayout;

public class PlaceTypeTransformer extends UniversalAdapter.Transformer<PlaceType> {

        @Bind(R.id.text) TextView mText;
        @Bind(R.id.image) ImageView mImage;
        @Bind(R.id.content_frame) SquareFrameLayout mFrame;

        public PlaceTypeTransformer(ViewGroup parent) {
            super(R.layout.item_place, parent);
            ButterKnife.bind(this, getView());
        }

        @Override
        protected void transform(PlaceType model) {
            mText.setText(model.getName());

//            if (model.getName().equals("Amusement Park")) {
//                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.amusement_park);
//                Bitmap blurredBitmap = Blur.fastblur(getContext(), bitmap, 25);
//                BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), blurredBitmap);
//                mImage.setImageDrawable(drawable);
//            } else {
//                mImage.setImageDrawable(null);
//            }


        }
    }