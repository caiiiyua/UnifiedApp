package org.caiiiyua.unifiedapp.ui.view;

import org.caiiiyua.unifiedapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentViewFragment extends Fragment {

    private TextView mText;
    private String mContentText;

    public static Fragment newInstance(String content) {
        return new ContentViewFragment(content);
    }

    public ContentViewFragment(String content) {
        mContentText = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hello_world, null);
        mText = (TextView)rootView.findViewById(R.id.text);
        mText.setText(mContentText);
        return rootView;
    }

}
