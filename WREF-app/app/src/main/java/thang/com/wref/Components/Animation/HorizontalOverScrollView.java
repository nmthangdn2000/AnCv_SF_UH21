package thang.com.wref.Components.Animation;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;

public class HorizontalOverScrollView extends HorizontalScrollView {
    private static final int WIDTH_DEVIDER_OVERSCROLL_DISTANCE = 3;

    private TimeInterpolator mInterpolator;
    private int mMaxOverscrollDistance;
    private int mAnimTime;
    private long mStartTime;

    public HorizontalOverScrollView(final Context context) {
        super(context);
        init();
    }

    public HorizontalOverScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalOverScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        mMaxOverscrollDistance = widthPixels / WIDTH_DEVIDER_OVERSCROLL_DISTANCE;
        mAnimTime = getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime);
        mInterpolator = new DecelerateInterpolator();
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        int overScrollDistance = mMaxOverscrollDistance;
        if (isTouchEvent) {
            mStartTime = AnimationUtils.currentAnimationTimeMillis();
        } else {
            final long elapsedTime = AnimationUtils.currentAnimationTimeMillis() - mStartTime;
            float interpolation = mInterpolator.getInterpolation((float) elapsedTime / mAnimTime);
            interpolation = interpolation > 1 ? 1 : interpolation;
            overScrollDistance -= overScrollDistance * interpolation;
            overScrollDistance = overScrollDistance < 0 ? 0 : overScrollDistance;
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, overScrollDistance, maxOverScrollY, isTouchEvent);
    }
}
