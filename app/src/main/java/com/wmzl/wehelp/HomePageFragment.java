package com.wmzl.wehelp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import as.leap.LASAnalytics;
import as.leap.LASObject;
import as.leap.LASQuery;
import as.leap.LASQueryManager;
import as.leap.LASUser;
import as.leap.callback.FindCallback;
import as.leap.exception.LASException;

/**
 * Created by leo on 15-7-8.
 */
public class HomePageFragment extends Fragment {
    private static final String ARG_POSITION = "position";


    private int position;

    Fragment fragment;

    //Elements in Fragment PublishHelp
    private XListView listViewAllHelp;

    //Data in Fragment PublishHelp
    private AllHelpItemAdapter allHelpItemAdapter;

    //Elements in Fragment PublishHelp
    private ListView listViewHelp;
    private Button btnAddHelp;
    private EditText editTextAddHelp;
    private TextView textViewUser;

    //Data in Fragment PublishHelp
    LinearLayout linearLayoutAddHelp;
    LASObject help;
    List<LASObject> listHelp = new ArrayList<>();
    String helpTitle;
    List<String> listHelpTitle = new ArrayList<>();


    //Elements in Fragment ME
    private LinearLayout linearLayoutMeNotLoggedIn;
    private LinearLayout linearLayoutMeLoggedIn;
    private Button btnSignIn;
    private Button btnSignOut;
    private ImageView imageViewUser;
    private Button btnTest;
    Bitmap yourSelectedImage;


    //Common Data
    private XListView listView;
    private HelpItemAdapter helpItemAdapter;
    LASUser currentUser;
    View viewResult;


    public static HomePageFragment newInstance(int position) {
        HomePageFragment f = new HomePageFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (position == 0) {
            DataManager.getInstance().TrackCurrentPage("View Helps");

            viewResult = inflater.inflate(R.layout.fragment_providehelp, container, false);
            listViewAllHelp = (XListView) viewResult.findViewById(R.id.listViewAllHelp);

            allHelpItemAdapter = new AllHelpItemAdapter(getActivity(), R.layout.listviewallhelp_item, getActivity());
            initXListAllData();
            listViewAllHelp.setAdapter(allHelpItemAdapter);
            // 添加XListView的上拉和下拉刷新监听器
            listViewAllHelp.setXListViewListener(new XListView.IXListViewListener() {
                @Override
                public void onRefresh() {
                    refreshListViewAllHelpInBackground();
                }

                @Override
                public void onLoadMore() {
                    onLoadListViewAllHelp();
                }
            });
            listViewAllHelp.setPullLoadEnable(true);
            listViewAllHelp.setPullRefreshEnable(true);
            listViewAllHelp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                    position = position - 1;
//
//                    if (null != helpItemAdapter.getModel(position)) {
//                        Toast.makeText(getActivity(), "click Item...", Toast.LENGTH_SHORT).show();
//                    }
                }
            });
            //LAS Analytics: log event

            listViewAllHelp.invalidateViews();
        } else if (position == 1) {
            DataManager.getInstance().TrackCurrentPage("View My Helps");
            viewResult = inflater.inflate(R.layout.fragment_publishhelp, container, false);

            if (DataManager.getInstance().GetIsUserLogedIn()) {
                listView = (XListView) viewResult.findViewById(R.id.listView);
                helpItemAdapter = new HelpItemAdapter(getActivity(), R.layout.listview_item, getActivity());
                initXListData();
                listView.setAdapter(helpItemAdapter);
                // 添加XListView的上拉和下拉刷新监听器
                listView.setXListViewListener(new XListView.IXListViewListener() {
                    @Override
                    public void onRefresh() {
                        refreshListViewInBackground();
                    }

                    @Override
                    public void onLoadMore() {
                        onLoadListView();
                    }
                });
                listView.setPullLoadEnable(true);
                listView.setPullRefreshEnable(true);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                });
                listView.invalidateViews();
            }
        } else if (position == 2) {
            DataManager.getInstance().TrackCurrentPage("View My Profile");
            viewResult = inflater.inflate(R.layout.fragment_aboutme, container, false);
            linearLayoutMeLoggedIn = (LinearLayout) viewResult.findViewById(R.id.linearLayoutMeLoggedIn);
            textViewUser = (TextView) viewResult.findViewById(R.id.textViewUser);
            linearLayoutMeNotLoggedIn = (LinearLayout) viewResult.findViewById(R.id.linearLayoutMeNotLoggedIn);
            btnSignOut = (Button) viewResult.findViewById(R.id.btnSignOut);
            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataManager.getInstance().SignOutCurrentUser();
                    DataManager.getInstance().SetIsUserLogedIn(false);
                }
            });
            btnSignIn = (Button) viewResult.findViewById(R.id.btnSignIn);
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), ActivityLogin.class);
                    startActivityForResult(i, DataManager.LOGIN_ID);

                }
            });
            InitUser();
            InitLoginBoxVisibility();


            btnTest = (Button) viewResult.findViewById(R.id.btnTest);
            btnTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), DataManager.SELECT_PHOTO_ID);
                }
            });

            // Test Buttons

            Button btnTestAddHelp = (Button) viewResult.findViewById(R.id.btnTestAddHelp);
            btnTestAddHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LASAnalytics.logEvent("Add Help");
                }
            });

            Button btnTestGetBonus = (Button) viewResult.findViewById(R.id.btnTestGetBonus);
            btnTestGetBonus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LASAnalytics.logEvent("Get Bonus");
                }
            });

            Button btnTestLogin = (Button) viewResult.findViewById(R.id.btnTestLogin);
            btnTestLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LASAnalytics.logEvent("Login");
                }
            });

            Button btnTestProvideHelp = (Button) viewResult.findViewById(R.id.btnTestProvideHelp);
            btnTestProvideHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LASAnalytics.logEvent("Provide Help");
                }
            });

            Button btnTestShowSS = (Button) viewResult.findViewById(R.id.btnTestShowSS);
            btnTestShowSS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), ActivityShowSS.class);
                    startActivityForResult(i, DataManager.ShowSS_ID);
                }
            });

        } else {

        }

        return viewResult;
    }

    private void InitLoginBoxVisibility() {
        if (DataManager.getInstance().GetIsUserLogedIn()) {
            linearLayoutMeNotLoggedIn.setVisibility(View.GONE);
            linearLayoutMeLoggedIn.setVisibility(View.VISIBLE);
            InitUser();
            imageViewUser = (ImageView) viewResult.findViewById(R.id.imageViewUser);
            imageViewUser.setImageResource(R.drawable.accountcircle);

        } else {
            linearLayoutMeLoggedIn.setVisibility(View.GONE);
            linearLayoutMeNotLoggedIn.setVisibility(View.VISIBLE);


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case DataManager.SELECT_PHOTO_ID:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    Agent.getInstance().UploadFile(yourSelectedImage);
                }
            case DataManager.ADDHELP_ID:
                if (resultCode == Activity.RESULT_OK) {
                    refreshListViewInBackground();
                    Toast.makeText(getActivity(), "Publish Accomplished.", Toast.LENGTH_SHORT).show();
                }
            case DataManager.LOGIN_ID:
            {
                // Does not work..????
//                if(resultCode==Activity.RESULT_OK){
                    InitLoginBoxVisibility();
//                }
            }
        }
    }

    private void InitUser() {
        currentUser = LASUser.getCurrentUser();
        if (currentUser != null) {
            DataManager.getInstance().SetIsUserLogedIn(true);
            String userName = currentUser.getUserName();
            textViewUser.setText(userName);
            textViewUser.invalidate();
        }
    }

    void initXListData() {
        LASQuery<LASObject> query = LASQuery.getQuery("Help");

        LASQueryManager.findAllInBackground(query, new FindCallback<LASObject>() {
            public void done(List<LASObject> helpList, LASException e) {
                if (e == null) {
                    //貌似会影响速度？为何？
                    //listHelp.addAll(helpList);
                    for (LASObject o : helpList) {
                        HelpModel model = new HelpModel();
                        String name = o.getString("PublisherName");
                        String currentUserName = DataManager.getInstance().GetCurrentUserName();
                        model.setImgHead(R.drawable.accountcircle);
                        model.setName(name);
                        model.setContent(o.getString("HelpTitle"));
                        model.setType(FinalVar.MSG_TEXT);
                        model.setPhonemodel("一支大鸡腿");
                        model.setAddress("江苏 常熟");
                        model.setDate(new SimpleDateFormat().format(new Date()).toString());
                        if (name.equals(currentUserName)) {
                            helpItemAdapter.addModel(model);
                            helpItemAdapter.notifyDataSetChanged();
                        }
                    }
                } else {

                }
            }
        });

    }

    void initXListAllData() {
        LASQuery<LASObject> query = LASQuery.getQuery("Help");

        LASQueryManager.findAllInBackground(query, new FindCallback<LASObject>() {
            public void done(List<LASObject> helpList, LASException e) {
                if (e == null) {
                    //貌似会影响速度？为何？
                    //listHelp.addAll(helpList);
                    for (LASObject o : helpList) {
                        HelpModel model = new HelpModel();
                        String name = o.getString("PublisherName");
                        model.setImgHead(R.drawable.accountcircle);
                        model.setName(name);
                        model.setContent(o.getString("HelpTitle"));
                        model.setType(FinalVar.MSG_TEXT);
                        model.setPhonemodel("一支大鸡腿");
                        model.setAddress("江苏 常熟");
                        model.setDate(new SimpleDateFormat().format(new Date()).toString());
                        allHelpItemAdapter.addModel(model);
                        allHelpItemAdapter.notifyDataSetChanged();
                    }
                } else {

                }
            }
        });

    }

    private void onLoadListView() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");
    }

    void refreshListViewInBackground() {
        helpItemAdapter.clear();
        initXListData();
        onLoadListView();
    }

    private void refreshListViewAllHelpInBackground() {
        allHelpItemAdapter.clear();
        initXListAllData();
        onLoadListViewAllHelp();
    }

    private void onLoadListViewAllHelp() {
        listViewAllHelp.stopRefresh();
        listViewAllHelp.stopLoadMore();
        listViewAllHelp.setRefreshTime("刚刚");
    }


}