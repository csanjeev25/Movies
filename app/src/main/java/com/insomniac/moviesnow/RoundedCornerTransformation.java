package com.insomniac.moviesnow;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by Sanjeev on 1/31/2018.
 */

public class RoundedCornerTransformation implements Transformation{

    @Override
    public Bitmap transform(Bitmap source) {
        final int radius= 5;
        final int margin = 10;
        final Paint paint = new Paint();
        paint.setShader(new BitmapShader(source,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(),source.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(margin,margin,source.getWidth() - margin,source.getHeight() - margin),radius,radius,paint);

        if(source != output)
            source.recycle();

        return output;
    }

    @Override
    public String key() {
        return "circle";
    }
}

