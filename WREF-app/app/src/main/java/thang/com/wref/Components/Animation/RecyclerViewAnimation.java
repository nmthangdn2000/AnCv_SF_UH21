package thang.com.wref.Components.Animation;

import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import thang.com.wref.Commerce.ItemProductApdater;
import thang.com.wref.MainScreen.Adapters.AgriAdapter;
import thang.com.wref.MainScreen.Adapters.ItemThemeAgriAdapter;
import thang.com.wref.StoriesScreen.Adapters.StoriesAdapter;

public class RecyclerViewAnimation {
    /** The magnitude of translation distance while the list is over-scrolled. */
    private static final float OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f;
    /** The magnitude of translation distance when the list reaches the edge on fling. */
    private static final float FLING_TRANSLATION_MAGNITUDE = 0.5f;
    private NestedScrollView nestedScrollView;

    public RecyclerViewAnimation() {

    }

    // bouncing animtion in recyclerview
    public void setAnimationRecyclerviewVertical(RecyclerView rcvItemThemeAgri, String adaper){
        rcvItemThemeAgri.setEdgeEffectFactory(new RecyclerView.EdgeEffectFactory(){
            @NonNull
            @Override
            protected EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
                return new EdgeEffect(view.getContext()){
                    @Override
                    public void onPull(float deltaDistance) {
                        super.onPull(deltaDistance);
                        handlePull(deltaDistance);
                    }

                    @Override
                    public void onPull(float deltaDistance, float displacement) {
                        super.onPull(deltaDistance, displacement);
                        handlePull(deltaDistance);
                    }

                    private void handlePull(float deltaDistance) {
                        float sign = direction == DIRECTION_BOTTOM ? -1 : 1;
                        float translationYDelta =
                                sign * view.getWidth() * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE;
                        for (int childCount = view.getChildCount(), i = 0; i < childCount; ++i) {
                            if (adaper.equals("agriAdapter")) {
                                final AgriAdapter.ViewHodler holder = (AgriAdapter.ViewHodler) view.getChildViewHolder(view.getChildAt(i));
                                holder.translationY.cancel();
                                holder.itemView.setTranslationY(holder.itemView.getTranslationY() + translationYDelta);
                            }else if (adaper.equals("ItemThemeAgriActivity")) {
                                final ItemThemeAgriAdapter.ViewHodler holder = (ItemThemeAgriAdapter.ViewHodler) view.getChildViewHolder(view.getChildAt(i));
                                holder.translationY.cancel();
                                holder.itemView.setTranslationY(holder.itemView.getTranslationY() + translationYDelta);
                            }
                        }
                    }

                    @Override
                    public void onRelease() {
                        super.onRelease();
                        for (int childCount = view.getChildCount(), i = 0; i < childCount; ++i) {
                            if (adaper.equals("agriAdapter")){
                                final AgriAdapter.ViewHodler holder = (AgriAdapter.ViewHodler) view.getChildViewHolder(view.getChildAt(i));
                                holder.translationY.start();
                            }else if (adaper.equals("ItemThemeAgriActivity")){
                                final ItemThemeAgriAdapter.ViewHodler holder = (ItemThemeAgriAdapter.ViewHodler) view.getChildViewHolder(view.getChildAt(i));
                                holder.translationY.start();
                            }
                        }
                    }

                    @Override
                    public void onAbsorb(int velocity) {
                        super.onAbsorb(velocity);
                        float sign = direction == DIRECTION_BOTTOM ? -1 : 1;
                        float translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE;
                        for (int childCount = view.getChildCount(), i = 0; i < childCount; ++i) {

                            if (adaper.equals("agriAdapter")){
                                final AgriAdapter.ViewHodler holder = (AgriAdapter.ViewHodler) view.getChildViewHolder(view.getChildAt(i));
                                holder.translationY.setStartVelocity(translationVelocity)
                                        .start();
                            }else if (adaper.equals("ItemThemeAgriActivity")){
                                final ItemThemeAgriAdapter.ViewHodler holder = (ItemThemeAgriAdapter.ViewHodler) view.getChildViewHolder(view.getChildAt(i));
                                holder.translationY.setStartVelocity(translationVelocity)
                                        .start();
                            }
                        }
                    }
                };
            }

        });
    }

    public void setAnimationRecyclerviewHorizontal(RecyclerView rcv){
        rcv.setEdgeEffectFactory(new RecyclerView.EdgeEffectFactory(){
            @NonNull
            @Override
            protected EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
                return new EdgeEffect(view.getContext()){
                    @Override
                    public void onPull(float deltaDistance) {
                        super.onPull(deltaDistance);
                        handlePull(deltaDistance);
                    }

                    @Override
                    public void onPull(float deltaDistance, float displacement) {
                        super.onPull(deltaDistance, displacement);
                        handlePull(deltaDistance);
                    }
                    private void handlePull(float deltaDistance) {
                        float sign = direction == DIRECTION_BOTTOM ? -1 : 1;
                        float translationYDelta =
                                sign * view.getWidth() * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE;
                        for (int childCount = view.getChildCount(), i = 0; i < childCount; ++i) {
                            final ItemProductApdater.ViewHolder holder = (ItemProductApdater.ViewHolder) view.getChildViewHolder(view.getChildAt(i));
                            holder.translationX.cancel();
                            holder.itemView.setTranslationX(holder.itemView.getTranslationX() + translationYDelta);
                        }
                    }
                    @Override
                    public void onRelease() {
                        super.onRelease();
                        for (int childCount = view.getChildCount(), i = 0; i < childCount; ++i) {
                            final ItemProductApdater.ViewHolder holder = (ItemProductApdater.ViewHolder) view.getChildViewHolder(view.getChildAt(i));
                            holder.translationX.start();
                        }
                    }

                    @Override
                    public void onAbsorb(int velocity) {
                        super.onAbsorb(velocity);
                        float sign = direction == DIRECTION_BOTTOM ? -1 : 1;
                        float translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE;
                        for (int childCount = view.getChildCount(), i = 0; i < childCount; ++i) {
                            final ItemProductApdater.ViewHolder holder = (ItemProductApdater.ViewHolder) view.getChildViewHolder(view.getChildAt(i));
                            holder.translationX.setStartVelocity(translationVelocity)
                                    .start();
                        }
                    }
                };

            }
        });
    }
}
