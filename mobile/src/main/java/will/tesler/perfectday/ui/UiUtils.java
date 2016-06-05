package will.tesler.perfectday.ui;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Created by admin on 6/5/16.
 */

public class UiUtils {

    /**
     * Tint the given drawable with the specified color.
     *
     * @param drawable The drawable.
     * @param color The color.
     */
    public static void tintDrawable(Drawable drawable, int color) {
        if (drawable != null) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);

            float[] matrix = {
                    0, 0, 0, 0, red,
                    0, 0, 0, 0, green,
                    0, 0, 0, 0, blue,
                    0, 0, 0, 1, 0
            };

            ColorFilter filter = new ColorMatrixColorFilter(matrix);
            drawable.mutate();
            drawable.setColorFilter(filter);
        }
    }
}
