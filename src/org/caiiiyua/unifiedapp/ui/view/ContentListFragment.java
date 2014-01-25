package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.AbstractActivityController;
import org.caiiiyua.unifiedapp.ui.ControllableActivity;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ContentListFragment extends ListFragment {

    public static String texts[] = {
        "Beijing",
        "Shanghai",
        "Nanjing",
        "Tianjing",
        "Chengdu",
        "LanZhou",
        "Kunming",
        "Shenyang",
        "Hefei",
        "Hangzhou",
        "Baiying",
        "Lasa",
        "Guilin"
    };
//    private ContentListView mContentListView;
    private ListView mContentListView;
    private ControllableActivity mActivity;
    private AbstractActivityController mController;
    private TracksAdapter mTracksAdapter;

    public ContentListFragment(AbstractActivityController controller) {
        mController = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.unified_list, null);
        mContentListView = (ListView)rootView.findViewById(R.id.list_container);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (ControllableActivity) getActivity();
        mTracksAdapter = new TracksAdapter(getActivity(),
                mContentListView, getTracksCursor());
//        mController.initContentLoader();
    }

    private Cursor getTracksCursor() {
        // TODO Auto-generated method stub
        return mController.getTracksCursor();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String toastString = "You have clicked " + position + " item";
        Toast.makeText(getActivity(), toastString, Toast.LENGTH_SHORT).show();
        mController.launchFragment(this, position);
    }
}
