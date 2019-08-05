package candreyskakunenko.myapplication.Helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import candreyskakunenko.myapplication.BuildConfig;
import candreyskakunenko.myapplication.Fragments.ErrorFragment;
import candreyskakunenko.myapplication.Fragments.PictureFragment;
import candreyskakunenko.myapplication.Fragments.TextFragment;
import candreyskakunenko.myapplication.Fragments.WebFragment;
import candreyskakunenko.myapplication.Interface.NavigationManager;
import candreyskakunenko.myapplication.NavActivity;
import candreyskakunenko.myapplication.R;

public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private NavActivity mActivity;

    public static FragmentNavigationManager getmInstance(NavActivity navActivity){
        if (mInstance==null) {
            mInstance = new FragmentNavigationManager();
            mInstance.configure(navActivity);
        }
        return mInstance;

    }

    private void configure(NavActivity navActivity) {
        mActivity =navActivity;
        mFragmentManager = navActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String function, String param) {
        switch (function){
            case "text":{
                showFragment(TextFragment.newInstance(param),false);
                break;
            }
            case "image": {
                showFragment(PictureFragment.newInstance(param), false);
                break;
            }
            case "url":{
                showFragment(WebFragment.newInstance(param),false);
                break;
            }
            case "error":{
                showFragment(ErrorFragment.newInstance(), false);
                break;
            }
            default: {
                showFragment(ErrorFragment.newInstance(), false);
                break;
            }
        }

    }

    private void showFragment(Fragment fragmentContent, boolean b) {

        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container,fragmentContent);
        ft.addToBackStack(null);
        if(b||!BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();
    }

}
