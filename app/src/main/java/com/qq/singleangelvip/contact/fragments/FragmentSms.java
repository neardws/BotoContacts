package com.qq.singleangelvip.contact.fragments;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.activities.MessageBoxActivity;
import com.qq.singleangelvip.contact.contacts.Contact;
import com.qq.singleangelvip.contact.contacts.Contacts;
import com.qq.singleangelvip.contact.contacts.Query;
import com.qq.singleangelvip.contact.message.BaseIntentUtil;
import com.qq.singleangelvip.contact.message.RexseeSMS;
import com.qq.singleangelvip.contact.message.SMSAdpter;
import com.qq.singleangelvip.contact.message.SMSBean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by singl on 2017/4/1.
 */
public  class FragmentSms extends Fragment {

    public FragmentSms(){}
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView smsListView;
    private SMSAdpter smsAdpter;
    private RexseeSMS rsms;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentSms newInstance(int sectionNumber) {
        FragmentSms fragment = new FragmentSms();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sms, container, false);

        smsListView = (ListView) rootView.findViewById(R.id.sms_list);
        smsAdpter = new SMSAdpter(getActivity());
        rsms = new RexseeSMS(getActivity());
        List<SMSBean> list_mmt = rsms.getThreadsNum(rsms.getThreads(0));
        Contacts.initialize(getContext());
        Query query = Contacts.getQuery();
        for(int i = 0; i < list_mmt.size(); i++){
            if (list_mmt.get(i).getAddress().startsWith("+86")){
                String phone = list_mmt.get(i).getAddress().substring(3);
                query.whereEqualTo(Contact.Field.PhoneNumber,phone);
                List<Contact> contacts = query.find();
                if (contacts.size()>0)
                    list_mmt.get(i).setAddress(contacts.get(0).getDisplayName());
            }else {
                query.whereEqualTo(Contact.Field.PhoneNumber,list_mmt.get(i).getAddress());
                List<Contact> contacts = query.find();
                if (contacts.size()>0)
                    list_mmt.get(i).setAddress(contacts.get(0).getDisplayName());
            }
        }
        Intent intent = getActivity().getIntent();
        final Bundle bundle = intent.getExtras();
        if (bundle != null){
            smsAdpter.assignment(list_mmt);
            smsListView.setAdapter(smsAdpter);
            smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Map<String, String> map = new HashMap<String, String>();
                    SMSBean sb = (SMSBean) smsAdpter.getItem(position);
                    map.put("phoneNumber", sb.getAddress());
                    map.put("threadId", sb.getThread_id());
                    BaseIntentUtil.intentSysDefault(getActivity(),
                            MessageBoxActivity.class, map,bundle.getInt("theme"));
                }
            });
        }else {
            smsAdpter.assignment(list_mmt);
            smsListView.setAdapter(smsAdpter);
            smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Map<String, String> map = new HashMap<String, String>();
                    SMSBean sb = (SMSBean) smsAdpter.getItem(position);
                    map.put("phoneNumber", sb.getAddress());
                    map.put("threadId", sb.getThread_id());
                    BaseIntentUtil.intentSysDefault(getActivity(),
                            MessageBoxActivity.class, map,0);
                }
            });
        }


        return rootView;
    }
}