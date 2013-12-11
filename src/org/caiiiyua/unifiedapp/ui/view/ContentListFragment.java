package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.R;
import org.caiiiyua.unifiedapp.ui.MainActivity;
import org.caiiiyua.unifiedapp.ui.RestrictedActivity;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private RestrictedActivity mActivity;
    private ContentPagerController mContentPagerController;
    public ContentListFragment(ContentPagerController controller) {
        mContentPagerController = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_list, null);
//        mContentListView = (ListView)rootView.findViewById(R.id.content_list);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity()
                , android.R.layout.simple_list_item_1, texts));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String toastString = "You have clicked " + position + " item";
        Toast.makeText(getActivity(), toastString, Toast.LENGTH_SHORT).show();
        mContentPagerController.show(position);
    }
}
