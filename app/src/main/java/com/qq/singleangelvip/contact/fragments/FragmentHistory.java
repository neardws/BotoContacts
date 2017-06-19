package com.qq.singleangelvip.contact.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.calllog.LogObject;
import com.qq.singleangelvip.contact.calllog.LogsAdapter;
import com.qq.singleangelvip.contact.calllog.LogsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by singl on 2017/4/1.
 */
public  class FragmentHistory extends Fragment {

    public FragmentHistory(){}

    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    private ListView logList;
    private Runnable logsRunnable;


    public static FragmentHistory newInstance(int sectionNumber) {
        FragmentHistory fragment = new FragmentHistory();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        logList = (ListView) rootView.findViewById(R.id.LogsList);
        logsRunnable = new Runnable() {
            @Override
            public void run() {
                loadLogs();
            }
        };
        logsRunnable.run();
        return rootView;
    }
    private void loadLogs() {
        LogsManager logsManager = new LogsManager(getContext());
        List<LogObject> callLogs = logsManager.getLogs(LogsManager.ALL_CALLS);
        List<LogObject> logObjects = new ArrayList<>();
        for (int i = callLogs.size()-1; i >= 0; i--){
            logObjects.add(callLogs.get(i));
        }
        LogsAdapter logsAdapter = new LogsAdapter(getContext(), R.layout.log_layout, logObjects);
        logList.setAdapter(logsAdapter);
    }

}