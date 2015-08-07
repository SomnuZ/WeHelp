//package com.wmzl.wehelp;
//
//import android.widget.ListView;
//import android.widget.Toast;
//
//
//import org.androidannotations.annotations.UiThread;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by leo on 15-7-9.
// */
//public class XListViewListener implements XListView.IXListViewListener {
//
//    private XListView xListView;
//    private HelpItemAdapter helpItemAdapter;
//    public XListViewListener(XListView view, HelpItemAdapter adapter){
//        xListView=view;
//        helpItemAdapter=adapter;
//    }
//
//
//
//
//
//    @Override
//    public void onRefresh() {
//        refreshListViewInBackground();
//    }
//
//    @UiThread
//    void refreshListViewInBackground() {// 模拟刷新数据
//        HelpModel model = new HelpModel();
//        model.setImgHead(R.drawable.head);
//        model.setName("老男孩");
//        model.setContent("为了梦想而努力。。。");
//        model.setType(FinalVar.MSG_TEXT);
//        model.setAgree(false);
//        model.setPhonemodel("Nexus 5");
//        model.setAddress("南京市 新模范马路");
//        model.setDate(new SimpleDateFormat().format(new Date()).toString());
//        helpItemAdapter.addModel(model,true);
//        helpItemAdapter.notifyDataSetChanged();
//        onLoad();
//    }
//
//    @Override
//    public void onLoadMore() {
//        loadMoreInBackground();
//    }
//
//    @UiThread
//    void loadMoreInBackground() {
////        HelpModel model = new HelpModel();
////        model.setImgHead(R.drawable.head);
////        model.setName("高富帅");
////        model.setContent("无聊中...且行且珍惜");
////        model.setType(FinalVar.MSG_TEXT);
////        model.setAgree(false);
////        model.setPhonemodel("Nexus 5");
////        model.setAddress("南京市 高铁南站");
////        model.setDate(new SimpleDateFormat().format(new Date()).toString());
////        helpItemAdapter.addModel(model);
////        helpItemAdapter.notifyDataSetChanged();
////        listView.setSelection(helpItemAdapter.getCount() - 1);// 将光标移动到加载的交界处
////        onLoad();
//    }
//
//
//
//}
