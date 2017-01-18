package com.fanshuchao.example.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by fanshuchao on 2017-1-17.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }

}
