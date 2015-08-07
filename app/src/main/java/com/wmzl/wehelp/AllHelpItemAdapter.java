package com.wmzl.wehelp;

/**
 * Created by leo on 15-7-9.
 */
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class AllHelpItemAdapter extends BaseAdapter {

    private Context context;

    private Activity activity;

    private List<HelpModel> listViewData;

    private int layoutResId;// ListView每个Item的布局文件

    public AllHelpItemAdapter(Context context, int layoutResId, Activity activity) {
        this.context = context;
        this.layoutResId = layoutResId;
        listViewData = new ArrayList<HelpModel>();
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HelpModel model = listViewData.get(position);
        ViewItemHolder viewItemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResId,
                    null);
            viewItemHolder = new ViewItemHolder();
            viewItemHolder.imgHead = (ImageView) convertView
                    .findViewById(R.id.imgHead);
            viewItemHolder.tvName = (TextView) convertView
                    .findViewById(R.id.tvName);
            viewItemHolder.tvDate = (TextView) convertView
                    .findViewById(R.id.tvDate);
            viewItemHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tvContent);
            viewItemHolder.ivPhoto = (ImageView) convertView
                    .findViewById(R.id.ivPhoto);
            viewItemHolder.ivAddress = (ImageView) convertView
                    .findViewById(R.id.ivAddress);
            viewItemHolder.tvAddress = (TextView) convertView
                    .findViewById(R.id.tvAddress);
            viewItemHolder.ivAgree = (ImageView) convertView
                    .findViewById(R.id.ivGoHelp);
            viewItemHolder.ivComment = (ImageView) convertView
                    .findViewById(R.id.ivShare);
            viewItemHolder.tvComment = (TextView) convertView
                    .findViewById(R.id.tvShare);
            viewItemHolder.ivAgreeShow = (ImageView) convertView
                    .findViewById(R.id.ivAgreeShow);
            viewItemHolder.tvAgreeShow = (TextView) convertView
                    .findViewById(R.id.tvAgreeShow);
            convertView.setTag(viewItemHolder);
        } else {
            viewItemHolder = (ViewItemHolder) convertView.getTag();
        }
        viewItemHolder.imgHead.setImageBitmap(BitmapFactory.decodeResource(
                context.getResources(), model.getImgHead()));
        viewItemHolder.tvName.setText(model.getName());
        viewItemHolder.tvDate.setText(model.getDate());
        viewItemHolder.tvContent.setText(model.getContent());
        if (model.getType() == FinalVar.MSG_IMAGE) {// 图片资源
            viewItemHolder.ivPhoto.setImageResource(R.drawable.pic_begging);
            viewItemHolder.ivPhoto.setVisibility(View.VISIBLE);
        } else {
            viewItemHolder.ivPhoto.setVisibility(View.GONE);
        }
        if (!model.getAddress().isEmpty()) {
            viewItemHolder.ivAddress.setVisibility(View.VISIBLE);
            viewItemHolder.tvAddress.setVisibility(View.VISIBLE);
            viewItemHolder.tvAddress.setText(model.getAddress());
        } else {
            viewItemHolder.ivAddress.setVisibility(View.GONE);
            viewItemHolder.tvAddress.setVisibility(View.GONE);
        }
        viewItemHolder.ivAgree
                .setOnClickListener(new ListViewButtonOnClickListener(position));
        if (model.isAgree()) {
            viewItemHolder.ivAgree
                    .setImageResource(R.drawable.ic_assistant_black_24dp);
        } else {
            viewItemHolder.ivAgree
                    .setImageResource(R.drawable.ic_assistant_black_24dp);
        }
        viewItemHolder.ivAgree.setFocusable(false);
        if (null != model.getAgreeShow() && model.getAgreeShow().size() > 0) {
            viewItemHolder.ivAgreeShow.setVisibility(View.VISIBLE);
            viewItemHolder.tvAgreeShow.setVisibility(View.VISIBLE);
            viewItemHolder.tvAgreeShow.setText(model.getAgreeShow().toString()
                    + "可以帮忙！");
        } else {
            viewItemHolder.ivAgreeShow.setVisibility(View.GONE);
            viewItemHolder.tvAgreeShow.setVisibility(View.GONE);
        }
        viewItemHolder.ivComment
                .setOnClickListener(new ListViewButtonOnClickListener(position));
        viewItemHolder.ivComment.setFocusable(false);
        viewItemHolder.tvComment
                .setOnClickListener(new ListViewButtonOnClickListener(position));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listViewData.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (null == listViewData) {
            return 0;
        }
        return listViewData.size();
    }

    /**
     * 添加一条记录
     *
     * @param model
     */
    public void addModel(HelpModel model) {
        listViewData.add(model);
    }

    /**
     * 添加一条记录
     *
     * @param model
     * @param insertHead
     *            true:插入在头部
     */
    public void addModel(HelpModel model, boolean insertHead) {
        if (insertHead) {
            listViewData.add(0, model);
        } else {
            listViewData.add(model);
        }
    }

    /**
     * 获取一条记录
     *
     * @param i
     * @return
     */
    public HelpModel getModel(int i) {
        if (i < 0 || i > listViewData.size() - 1) {
            return null;
        }
        return listViewData.get(i);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        listViewData.clear();
    }

    class ViewItemHolder {
        ImageView imgHead;
        TextView tvName;
        TextView tvDate;
        TextView tvContent;
        ImageView ivPhoto;
        ImageView ivAddress;
        TextView tvAddress;
        ImageView ivAgree;
        ImageView ivComment;
        TextView tvComment;
        ImageView ivAgreeShow;
        TextView tvAgreeShow;
    }

    class ListViewButtonOnClickListener implements OnClickListener {
        private int position;// 记录ListView中Button所在的Item的位置

        public ListViewButtonOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivGoHelp:
                    ImageView ivAgree = (ImageView) v;
                    HelpModel model = listViewData.get(position);
                    List<String> agreeShow = model.getAgreeShow();
                    if (null == agreeShow || agreeShow.size() <= 0) {
                        agreeShow = new ArrayList<String>();
                    }
                    if (model.isAgree()) {
                        agreeShow.remove("我");
                        ivAgree.setImageResource(R.drawable.ic_assistant_black_24dp);
                    } else {
                        agreeShow.add("我");
                        ivAgree.setImageResource(R.drawable.ic_assistant_black_24dp);
                    }
                    model.setAgree(!model.isAgree());
                    model.setAgreeShow(agreeShow);
                    notifyDataSetChanged();
                    // Toast.makeText(context, "你点了赞", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ivShare:
                case R.id.tvShare:

                    break;
                default:
                    break;
            }
        }
    }
}

