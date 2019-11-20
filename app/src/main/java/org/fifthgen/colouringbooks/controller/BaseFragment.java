package org.fifthgen.colouringbooks.controller;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by GameGFX Studio on 2015/9/2.
 */
public class BaseFragment extends Fragment {
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatBaseActivity) {
            ((AppCompatBaseActivity) getActivity()).setmSwipeRefreshLayout(swipeRefreshLayout);
        }
    }
}
