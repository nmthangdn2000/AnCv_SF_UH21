package thang.com.wref.MainScreen.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import thang.com.wref.CameraPredictionScreen.CameraPredictFragment;
import thang.com.wref.Commerce.CommerceFragment;
import thang.com.wref.MainScreen.HomeFragment;
import thang.com.wref.QRCodeScreen.QRCodeFragment;
import thang.com.wref.StoriesScreen.SocialNetworkFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private MeowBottomNavigation meowBottomNavigation;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, MeowBottomNavigation meowBottomNavigation) {
        super(fragmentActivity);
        this.meowBottomNavigation = meowBottomNavigation;
    }
    // create fragment in mainActivity
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
//                return new MapFragment(meowBottomNavigation);
                return new HomeFragment();
            case 1:
                SocialNetworkFragment socialNetworkFragment = new SocialNetworkFragment();
                return socialNetworkFragment.newInstance(meowBottomNavigation);
            case 2:
                return new CommerceFragment();
            case 3:
                return new QRCodeFragment();
            case 4:
                return new CameraPredictFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
