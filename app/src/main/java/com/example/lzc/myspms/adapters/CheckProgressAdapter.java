package com.example.lzc.myspms.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lzc.myspms.R;
import com.example.lzc.myspms.activitys.queryactivitys.checkitemactivitys.CheckProjectActivity;
import com.example.lzc.myspms.models.Constant;
import com.example.lzc.myspms.models.LoginInfoModel;
import com.example.lzc.myspms.models.NewCheckInfoModel;
import com.example.lzc.myspms.models.QyJsonModel;
import com.example.lzc.myspms.utils.GpsUtil;
import com.example.lzc.myspms.utils.NetUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LZC on 2017/10/30.
 */
public class CheckProgressAdapter extends BaseAdapter implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private List<NewCheckInfoModel.NewCheckMsgInfoModel.ListBean> data;
    private LayoutInflater inflater;
    private SimpleDateFormat simpleDateFormat;
    private String format;
    private Activity activity;
    private Gson gson = new Gson();
    //任务状态
    private String rwzt;
    private TextView tvType;
    private TextView tvUnqualifiedNumber;
    private TextView tvCompony;
    private TextView tvSupervision;
    private TextView tvDate;

    public CheckProgressAdapter(List<NewCheckInfoModel.NewCheckMsgInfoModel.ListBean> data, Context context, Activity activity, String rwzt) {
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
        this.inflater = LayoutInflater.from(context);
        this.activity = activity;
        this.rwzt = rwzt;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e(TAG, "getView: ");
        View view = inflater.inflate(R.layout.fragment_check_progress_item, parent, false);
        tvType = (TextView) view.findViewById(R.id.fragment_check_progress_item_tv_type);
        tvUnqualifiedNumber = (TextView) view.findViewById(R.id.fragment_check_progress_item_tv_unqualified_number);
        tvCompony = (TextView) view.findViewById(R.id.fragment_check_progress_item_tv_compony);
        tvSupervision = (TextView) view.findViewById(R.id.fragment_check_progress_item_tv_supervision);
        tvDate = (TextView) view.findViewById(R.id.fragment_check_progress_item_tv_date);
        tvType.setText((position+1)+"");
//        if (data.get(position).getJcjg()==0) {
//            tvStatus.setText("不合格");
//        }else if(data.get(position).getJcjg()==0){
//            tvStatus.setText("合格");
//        }else{
//            tvStatus.setText("未检查");
//        }
        // TODO: 2018/6/21 不合格数量
        tvUnqualifiedNumber.setText(data.get(position).getUnPassCount()+"");
        String qyJson = data.get(position).getQyJson();
        QyJsonModel qyJsonModel = gson.fromJson(qyJson, QyJsonModel.class);
        tvCompony.setText(qyJsonModel.getQymc());
        tvSupervision.setText(data.get(position).getJgfj());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (data.get(position).getJzsj() > 0) {
            format = simpleDateFormat.format(new Date(data.get(position).getJzsj()));
        }
        tvDate.setText(format);
//        tvStatus
        return view;
    }

    @Override
    public void onClick(View v) {
//        if (v != null) {
        final int position = (int) v.getTag();

    }
}
