package thang.com.wref.Components.Animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class OverScrollBounceBehavior extends CoordinatorLayout.Behavior<View> {
    private final static float OVER_SCROLL_AREA = 4;
    private int mOverScrollY;

    public OverScrollBounceBehavior() {
    }

    public OverScrollBounceBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        mOverScrollY = 0;
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        if(dyUnconsumed == 0)
            return;
        mOverScrollY -= dyUnconsumed/OVER_SCROLL_AREA;
        final ViewGroup group = (ViewGroup) target;
        final int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = group.getChildAt(i);
            view.setTranslationY(mOverScrollY);
        }
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        if (mOverScrollY == 0) {
            return false;
        }
        // Smooth animate to 0 when user fling view
        moveToDefPosition(target);
        return true;
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        moveToDefPosition(target);
    }

    private void moveToDefPosition(View target) {
        final ViewGroup viewGroup = (ViewGroup) target;
        final int count = viewGroup.getChildCount();
        for(int i = 0; i < count; i++){
            final View view = viewGroup.getChildAt(i);
            ViewCompat.animate(view).translationY(0)
                    .setInterpolator(new AccelerateDecelerateInterpolator()).start();
        }
    }
}
