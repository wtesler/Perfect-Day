package will.tesler.perfectday;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.asymmetricadapter.adapter.UniversalAdapter;
import will.tesler.perfectday.placetype.PlaceType;
import will.tesler.perfectday.placetype.PlaceTypeIds;
import will.tesler.perfectday.transformers.PlaceTypeTransformer;
import will.tesler.perfectday.ui.UiUtils;

import static android.R.attr.translationX;

public class PlacesActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    @Bind(R.id.imageview_sun) ImageView mImageViewSun;
    @Bind(R.id.recyclerview_places) RecyclerView mRecyclerView;
    @Bind(R.id.toolbar) ViewGroup mToolbar;

    private UniversalAdapter mAdapter = new UniversalAdapter();
    private GridLayoutManager layoutManager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable private Integer mToolbarHeightPixels;
    @Nullable private Integer mToolbarWidthPixels;

    @Nullable private Integer mSunHeightPixels;
    @Nullable private Integer mSunWidthPixels;

    @Nullable private ObjectAnimator mSunAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);

        //setSupportActionBar(mToolbar);

        setupRecyclerView();

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.register(PlaceType.class, PlaceTypeTransformer.class);

        int[] placeTypeIds = PlaceTypeIds.getSupportedPlaceTypeIds();
        String[] placeTypeNames = getResources().getStringArray(R.array.placetypes);
        for (int i = 0; i < placeTypeIds.length; i++) {
            mAdapter.add(new PlaceType(placeTypeIds[i], placeTypeNames[i]));
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        mSunAnimator = ObjectAnimator.ofFloat(mImageViewSun, "translationY", 0);
        mSunAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mSunAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mSunAnimator.setDuration(1000);

        mToolbar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mToolbar.removeOnLayoutChangeListener(this);

                mToolbarWidthPixels = mToolbar.getWidth();
                mToolbarHeightPixels = mToolbar.getHeight();

                mSunWidthPixels = mImageViewSun.getWidth();
                mSunHeightPixels = mImageViewSun.getHeight();

                final float offSetY = (mToolbarHeightPixels / 2f) - (mSunHeightPixels / 2f);
                final float translationY= (mToolbarHeightPixels / 2f) + (mSunHeightPixels / 2f);

                // Vertical
                Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float progress, Transformation t) {
                        float reverseProgress = 1 - progress;
                        mImageViewSun.setTranslationY((translationY * reverseProgress) + offSetY);
                    }
                };
                animation.setRepeatCount(ValueAnimator.INFINITE);
                animation.setRepeatMode(ValueAnimator.REVERSE);
                animation.setDuration(5000);
                animation.setInterpolator(new AccelerateInterpolator());

                // Horizontal
                final float offSetX = 0;
                final float translationX= mToolbarWidthPixels - mSunWidthPixels;

                Animation animation2 = new Animation() {
                    @Override
                    protected void applyTransformation(float progress, Transformation t) {
                        mImageViewSun.setTranslationX((translationX * progress) + offSetX);
                    }
                };
                animation2.setRepeatCount(ValueAnimator.INFINITE);
                animation2.setDuration(10000);
                animation2.setInterpolator(new LinearInterpolator());

                // Animation Set
                AnimationSet set = new AnimationSet(true);
                set.addAnimation(animation);
                set.addAnimation(animation2);

                // Start Movement animations
                mImageViewSun.startAnimation(set);

                // Sky Color Animation.
                int colorFrom = ContextCompat.getColor(PlacesActivity.this, R.color.sunset_sky);
                int colorTo = ContextCompat.getColor(PlacesActivity.this, R.color.colorPrimary);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
                colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
                colorAnimation.setDuration(5000);
                colorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                colorAnimation.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        mToolbar.setBackgroundColor((int) animator.getAnimatedValue());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.setStatusBarColor((int) animator.getAnimatedValue());
                        }
                    }

                });
                colorAnimation.start();

                // Sun Color Animation.
                int sunColorFrom = ContextCompat.getColor(PlacesActivity.this, R.color.sunset);
                int sunColorTo = ContextCompat.getColor(PlacesActivity.this, R.color.sun);
                ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), sunColorFrom, sunColorTo);
                colorAnimation2.setRepeatCount(ValueAnimator.INFINITE);
                colorAnimation2.setRepeatMode(ValueAnimator.REVERSE);
                colorAnimation2.setDuration(5000);
                colorAnimation2.setInterpolator(new AccelerateDecelerateInterpolator());
                colorAnimation2.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        UiUtils.tintDrawable(mImageViewSun.getDrawable(), (int) animator.getAnimatedValue());
                    }

                });
                colorAnimation2.start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        mAuth.removeAuthStateListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                mAuth.signOut();
                break;
//            case R.id.action_sunshine:
//                break;
        }
        return true;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void setSunTopMargin(int margin) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mImageViewSun.getLayoutParams();
        params.topMargin = margin;
        mImageViewSun.setLayoutParams(params);
    }

    private void setupRecyclerView() {
        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }
}
