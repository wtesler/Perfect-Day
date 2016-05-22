package will.tesler.perfectday.placetype;

import com.google.android.gms.location.places.Place;

public class PlaceTypeIds {

    private static int[] sSupportedPlaceTypeIds = new int[] {
            Place.TYPE_AMUSEMENT_PARK,
            Place.TYPE_AQUARIUM,
            Place.TYPE_ART_GALLERY,
            Place.TYPE_BAKERY,
            Place.TYPE_BAR,
            Place.TYPE_BEAUTY_SALON,
            Place.TYPE_BOWLING_ALLEY,
            Place.TYPE_CAFE,
            Place.TYPE_CAR_WASH,
            Place.TYPE_CASINO,
            Place.TYPE_CLOTHING_STORE,
            Place.TYPE_CONVENIENCE_STORE,
            Place.TYPE_DEPARTMENT_STORE,
            Place.TYPE_GAS_STATION,
            Place.TYPE_GROCERY_OR_SUPERMARKET,
            Place.TYPE_GYM,
            Place.TYPE_HAIR_CARE,
            Place.TYPE_HOME_GOODS_STORE,
            Place.TYPE_LAUNDRY,
            Place.TYPE_LIBRARY,
            Place.TYPE_LIQUOR_STORE,
            Place.TYPE_MEAL_TAKEAWAY,
            Place.TYPE_MOVIE_RENTAL,
            Place.TYPE_MOVIE_THEATER,
            Place.TYPE_MUSEUM,
            Place.TYPE_NIGHT_CLUB,
            Place.TYPE_PARK,
            Place.TYPE_PARKING,
            Place.TYPE_RESTAURANT,
            Place.TYPE_SHOE_STORE,
            Place.TYPE_SHOPPING_MALL,
            Place.TYPE_SPA,
            Place.TYPE_STADIUM,
            Place.TYPE_STORE,
            Place.TYPE_ZOO
    };

    public static int[] getSupportedPlaceTypeIds() {
        return sSupportedPlaceTypeIds;
    }
}
