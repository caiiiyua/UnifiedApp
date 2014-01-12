package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.AbstractActivityController;
import org.caiiiyua.unifiedapp.ui.ControllableActivity;
import org.caiiiyua.unifiedapp.ui.VolumeListCallbacks;
import org.caiiiyua.unifiedapp.utils.LogUtils;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class VolumeListFragment extends Fragment implements VolumesUpdateListener,
            OnItemClickListener {

    public static String texts[] = {
        "Vol01",
        "Vol02",
        "Vol03",
        "Vol04",    
        "Vol05",
        "Vol06",
        "Vol07",
        "Vol08",
        "Vol09",
        "Vol10",
        "Vol11",
        "Vol12",
        "Vol13"
    };
//    private ContentListView mContentListView;
    private ControllableActivity mActivity;
    private AbstractActivityController mController;
    private ListView mVolumeList;
    private VolumeListAdapter mListAdpater;
    private VolumeListCallbacks mCallbacks;

    public static VolumeListFragment newInstance(AbstractActivityController controller) {
        VolumeListFragment volumeListFragment = new VolumeListFragment(controller);
        return volumeListFragment;
    }

    public VolumeListFragment(AbstractActivityController controller) {
        mController = controller;
    }

    private Cursor getVolumeListCursor() {
        return mCallbacks != null ? mCallbacks.getVolumeListCursor() : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.unified_list, null);
        mVolumeList = (ListView) rootView.findViewById(R.id.list_container);
        LogUtils.d(LogUtils.TAG, "VolumeListFragment onCreateView");
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(LogUtils.TAG, "VolumeListFragment onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtils.d(LogUtils.TAG, "VolumeListFragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        mCallbacks = mController;
        mController.registerVolumesUpdateListener(this);
        Activity activity = getActivity();
        mActivity = (ControllableActivity) activity;
        mListAdpater = new VolumeListAdapter(getActivity(), activity,
                mVolumeList, getVolumeListCursor());
        mVolumeList.setAdapter(mListAdpater);
        mVolumeList.setOnItemClickListener(this);
    }

    @Override
    public void onVolumesUpdated(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        LogUtils.d(LogUtils.TAG, "VolumeListFragment onVolumesUpdated");
        mListAdpater.swapCursor(cursor);
        mListAdpater.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        mController.removeVolumesUpdateListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        String toastString = "You have clicked " + position + " item with id: " + id;
        Toast.makeText(getActivity(), toastString, Toast.LENGTH_SHORT).show();
        mController.launchFragment(this, position);
    }
}
