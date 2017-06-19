package com.qq.singleangelvip.contact.fragments;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.contacts.Contact;
import com.qq.singleangelvip.contact.contacts.ContactHelper;
import com.qq.singleangelvip.contact.contacts.ContactModel;
import com.qq.singleangelvip.contact.contacts.Contacts;
import com.qq.singleangelvip.contact.contacts.Query;
import com.qq.singleangelvip.contact.contactselector.CharacterParser;
import com.qq.singleangelvip.contact.contactselector.ClearEditText;
import com.qq.singleangelvip.contact.contactselector.PinyinComparator;
import com.qq.singleangelvip.contact.contactselector.SideBar;
import com.qq.singleangelvip.contact.contactselector.SortAdapter;
import com.qq.singleangelvip.contact.contactselector.SortModel;
import com.qq.singleangelvip.contact.popupwindows.SlideFromBottomPopup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by singl on 2017/4/1.
 */
public  class FragmentContact extends Fragment {

    public FragmentContact() {
    }

    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    private ListView sortListView;
    private SideBar sideBar;
    //显示字母的TextView
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    public static FragmentContact newInstance(int sectionNumber) {
        FragmentContact fragment = new FragmentContact();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_contact, container, false);


        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("data");
                if ("refresh".equals(msg)) {
                    Contacts.initialize(getContext());
                    Query q = Contacts.getQuery();
                    q.include(Contact.Field.DisplayName, Contact.Field.PhoneNumber);
                    List<Contact> contacts = q.find();
                    ArrayList<String> date = new ArrayList<>();
                    for (int i = 0; i < contacts.size(); i++)
                        date.add(contacts.get(i).getDisplayName());
                    SourceDateList = filledData(date.toArray(new String[date.size()]),contacts);
                    adapter.updateListView(SourceDateList);

                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);

        init(rootView);

    return rootView;
    }

    public void init(View rootView){ // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) rootView.findViewById(R.id.sidrbar);
        //dialog = (TextView) rootView.findViewById(R.id.dialog);
        //sideBar.setTextView(dialog);
        dialog = (TextView)rootView.findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });
        sortListView = (ListView) rootView.findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Bundle bundle = getActivity().getIntent().getExtras();
                if (bundle != null){
                    SlideFromBottomPopup slideFromBottomPopup =
                            new SlideFromBottomPopup(getActivity(),(SortModel) adapter.getItem(position),bundle.getInt("theme"));
                    slideFromBottomPopup.showPopupWindow();
                }else {
                    SlideFromBottomPopup slideFromBottomPopup =
                            new SlideFromBottomPopup(getActivity(),(SortModel) adapter.getItem(position),0);
                    slideFromBottomPopup.showPopupWindow();
                }

            }
        });

        //填充数据
        Contacts.initialize(getContext());
        Query q = Contacts.getQuery();
        q.include(Contact.Field.DisplayName, Contact.Field.PhoneNumber);
        List<Contact> contacts = q.find();
        ArrayList<String> date = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++)
            date.add(contacts.get(i).getDisplayName());
        SourceDateList = filledData(date.toArray(new String[date.size()]),contacts);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(getContext(), SourceDateList);
        sortListView.setAdapter(adapter);

        mClearEditText = (ClearEditText) rootView.findViewById(R.id.filter_edit);

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }
    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date,List<Contact> contacts) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < contacts.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(contacts.get(i).getDisplayName());
            sortModel.setPhoneNumber(contacts.get(i).getPhoneNumbers().get(0).getNumber());
            sortModel.set_ID(contacts.get(i).get_ID());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                String phone = sortModel.getPhoneNumber();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
                if (phone.indexOf(filterStr.toString()) != -1 || phone.startsWith(filterStr.toString())){
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}
