package mensajero.mensajerogo.Constantes;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ShowAnim extends Animation {
    public static final String TAG = " showAnmimation";
    int targetHeight;
    int initialHeight;
    View view;
    public ShowAnim(View view, int targetHeight, int initialHeight) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.initialHeight = initialHeight;
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Log.i(TAG,"target height last" + view.getLayoutParams().height);
        view.getLayoutParams().height = (int)( (targetHeight - initialHeight) * interpolatedTime );
        view.requestLayout();
        Log.i(TAG,"target height " + (int)( (targetHeight - initialHeight) * interpolatedTime ));
    }
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }
    @Override
    public boolean willChangeBounds() { return true; } }
