package com.example.lzc.myspms.fragments.homepagefragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lzc.myspms.R;
import com.example.lzc.myspms.adapters.SafeInfoStaffAdapter;
import com.example.lzc.myspms.adapters.SafeInfoTzzyAdapter;
import com.example.lzc.myspms.adapters.SafeInfoZsryAdapter;
import com.example.lzc.myspms.custom.ClearEditText;
import com.example.lzc.myspms.fragments.BaseFragment;
import com.example.lzc.myspms.models.Constant;
import com.example.lzc.myspms.models.EnumModel;
import com.example.lzc.myspms.models.FindByIdWithStaffModel;
import com.example.lzc.myspms.models.JsonModel;
import com.example.lzc.myspms.models.TzzyryModel;
import com.example.lzc.myspms.models.ZsryJsonModel;
import com.example.lzc.myspms.utils.NetUtil;
import com.example.lzc.myspms.utils.ValidateUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LZC on 2017/11/27.
 */

public class ComponyStaffInfoFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = ComponyStaffInfoFragment.class.getSimpleName();
    private String which;
    private String qyId;
    private FindByIdWithStaffModel.FindByIdWithStaffMsgModel componyInfo;
    private ClearEditText etQyfr;
    private ClearEditText etLxdh;
    private ClearEditText etDzyx;
    private ClearEditText etZyfzr;
    private ClearEditText etZyfzrxxqk;
    private ClearEditText etZyfzryddh;
    private ClearEditText etZyfzrgddh;
    private ClearEditText etAqfzr;
    private ClearEditText etAqfzrxxqk;
    private ClearEditText etAqfzryddh;
    private ClearEditText etAqfzrgddh;
    private ClearEditText etCyrysl;
    private ImageView imgCyryAdd;
    private ListView listCyry;
    private ClearEditText etTzzyrysl;
    private ImageView imgTzzyryAdd;
    private ListView listTzzyry;
    private ClearEditText etZzaqscsl;
    private ImageView imgZzaqscAdd;
    private ListView listZzaqsc;
    private ClearEditText etZzyjglsl;
    private ImageView imgZzyjglAdd;
    private ListView listZzyjgl;
    private ClearEditText etZcaqgcssl;
    private ImageView imgZcaqgcsAdd;
    private ListView listZcaqgcs;
    private ClearEditText etFdxm;
    private ClearEditText etFddh;
    private ImageView imgZsryAdd;
    private ListView listZsry;
    private LinearLayout linearLayout;
    private Button btnUp;
    private Button btnDown;
    //通用数据
    private String[] commonArray = new String[]{"", "是", "否"};
    //存储人员信息
    private ArrayList<JsonModel> cyryList = new ArrayList<>();
    private ArrayList<JsonModel> zzaqscList = new ArrayList<>();
    private ArrayList<JsonModel> zzyjglList = new ArrayList<>();
    private ArrayList<JsonModel> zcaqgcsList = new ArrayList<>();
    private ArrayList<ZsryJsonModel> zsryList = new ArrayList<>();
    private ArrayList<TzzyryModel> tzzyryList = new ArrayList<>();
    private Gson gson = new Gson();
    private SafeInfoStaffAdapter adapterCyry;
    private SafeInfoStaffAdapter adapterZzaqsc;
    private SafeInfoStaffAdapter adapterZzyjgl;
    private SafeInfoStaffAdapter adapterZcaqgcs;
    private SafeInfoTzzyAdapter adapterTzzyry;
    private List<EnumModel> dataStructure = new ArrayList<>();
    private SafeInfoZsryAdapter adapterZsry;
    private Fragment mShowFragment = this;
    //公共数据
    private List<EnumModel> commonList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_staff_info, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        which = getArguments().getString("which");
        qyId = getArguments().getString("qyId");
        componyInfo = (FindByIdWithStaffModel.FindByIdWithStaffMsgModel) getArguments().getSerializable("componyInfo");
        initView();
        initData();
        initListener();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mShowFragment = this;
            componyInfo = (FindByIdWithStaffModel.FindByIdWithStaffMsgModel) getArguments().getSerializable("componyInfo");
        }
    }

    private void initListener() {
        if (which.equals("view")) {
            //让所有控件不可响应
            etQyfr.setEnabled(false);
            etLxdh.setEnabled(false);
            etDzyx.setEnabled(false);
            etZyfzr.setEnabled(false);
            etZyfzrxxqk.setEnabled(false);
            etZyfzrgddh.setEnabled(false);
            etZyfzryddh.setEnabled(false);
            etAqfzr.setEnabled(false);
            etAqfzrxxqk.setEnabled(false);
            etAqfzrgddh.setEnabled(false);
            etAqfzryddh.setEnabled(false);
            etCyrysl.setEnabled(false);
            etZzyjglsl.setEnabled(false);
            etZzaqscsl.setEnabled(false);
            etFddh.setEnabled(false);
            etFdxm.setEnabled(false);
            etTzzyrysl.setEnabled(false);
            etZcaqgcssl.setEnabled(false);
            //将控件设为不可编辑状态
            etQyfr.isEdittextCanEdit(false);
            etLxdh.isEdittextCanEdit(false);
            etDzyx.isEdittextCanEdit(false);
            etZyfzr.isEdittextCanEdit(false);
            etZyfzrxxqk.isEdittextCanEdit(false);
            etZyfzrgddh.isEdittextCanEdit(false);
            etZyfzryddh.isEdittextCanEdit(false);
            etAqfzr.isEdittextCanEdit(false);
            etAqfzrxxqk.isEdittextCanEdit(false);
            etAqfzrgddh.isEdittextCanEdit(false);
            etAqfzryddh.isEdittextCanEdit(false);
            etCyrysl.isEdittextCanEdit(false);
            etZzyjglsl.isEdittextCanEdit(false);
            etZzaqscsl.isEdittextCanEdit(false);
            etFddh.isEdittextCanEdit(false);
            etFdxm.isEdittextCanEdit(false);
            etTzzyrysl.isEdittextCanEdit(false);
            etZcaqgcssl.isEdittextCanEdit(false);

        } else {
            imgCyryAdd.setOnClickListener(this);
            imgCyryAdd.setTag(0);
            imgZzaqscAdd.setOnClickListener(this);
            imgZzaqscAdd.setTag(2);
            imgZzyjglAdd.setOnClickListener(this);
            imgZzyjglAdd.setTag(3);
            imgZcaqgcsAdd.setOnClickListener(this);
            imgZcaqgcsAdd.setTag(4);
            imgTzzyryAdd.setOnClickListener(this);
            imgZsryAdd.setOnClickListener(this);
            btnDown.setOnClickListener(this);
            btnUp.setOnClickListener(this);
            etZyfzrxxqk.setFocusable(false);
            etZyfzrxxqk.setOnClickListener(this);
            etAqfzrxxqk.setFocusable(false);
            etAqfzrxxqk.setOnClickListener(this);
        }
    }

    private void initData() {
        commonList.add(new EnumModel("1", "是"));
        commonList.add(new EnumModel("2", "否"));
        //建筑结构单位
        initEnumData("/enum/getEnums", "ARCHITECTURAL_STRUCTURE_TYPE", dataStructure);
        if (which.equals("edit")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    linearLayout.setVisibility(View.VISIBLE);
                    setData();
                }
            }, 500);
        } else if (which.equals("add")) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }, 500);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setData();
                    linearLayout.setVisibility(View.GONE);
                }
            }, 500);
        }
    }

    private void initEnumData(String url, final String params, final List<EnumModel> data) {
        OkHttpUtils.post()
                .url(Constant.SERVER_URL + url)
                .addParams("code", params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                NetUtil.errorTip(getContext(), e.getMessage());
            }

            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
                Log.e(TAG, "initEnumData: " + params + response);
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //解析json数据 这里用不了gson 因为gson要求object开头
                            JSONObject o = (JSONObject) jsonArray.get(i);
                            //监管等级 标准化等级 企业风险分级 企业规模
                            String value = "";
                            value = o.getString("value");
                            String key = o.getString("key");
                            data.add(new EnumModel(key, value));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * @desc 将上个页面传过来的数据设置到对应的控件上
     * @date 2018/6/20 19:17
     */
    private void setData() {
        etQyfr.setText(componyInfo.getFddbr());
        etLxdh.setText(componyInfo.getLxdh());
        etDzyx.setText(componyInfo.getDzyx());
        //主要负责人
        etZyfzr.setText(componyInfo.getZyfzr());
        etZyfzrxxqk.setText(commonArray[componyInfo.getIszyfzrxx()]);
        etZyfzryddh.setText(componyInfo.getZyfzryddhhm());
        etZyfzrgddh.setText(componyInfo.getZyfzrgddhhm());
        //安全负责人
        etAqfzr.setText(componyInfo.getAqfzr());
        etAqfzrxxqk.setText(commonArray[componyInfo.getIsaqfzrxx()]);
        etAqfzryddh.setText(componyInfo.getAqfzryddhhm());
        etAqfzrgddh.setText(componyInfo.getAqfzrgddhhm());
        //从业人员
        etCyrysl.setText(componyInfo.getCyrysl() + "");
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(componyInfo.getCyryJson());
            for (int i = 0; i < jsonArray.length(); i++) {
                JsonModel jsonModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), JsonModel.class);
                cyryList.add(jsonModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterCyry = new SafeInfoStaffAdapter(cyryList, getContext());
        listCyry.setAdapter(adapterCyry);
        //专职安全生产
        etZzaqscsl.setText(componyInfo.getZzaqscglrys() + "");
        try {
            jsonArray = new JSONArray(componyInfo.getZzaqscryJson());
            for (int i = 0; i < jsonArray.length(); i++) {
                JsonModel jsonModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), JsonModel.class);
                zzaqscList.add(jsonModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterZzaqsc = new SafeInfoStaffAdapter(zzaqscList, getContext());
        listZzaqsc.setAdapter(adapterZzaqsc);
        //专职应急管理
        etZzyjglsl.setText(componyInfo.getZzyjglrys() + "");
        try {
            jsonArray = new JSONArray(componyInfo.getZzyjglryJson());
            for (int i = 0; i < jsonArray.length(); i++) {
                JsonModel jsonModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), JsonModel.class);
                zzyjglList.add(jsonModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterZzyjgl = new SafeInfoStaffAdapter(zzyjglList, getContext());
        listZzyjgl.setAdapter(adapterZzyjgl);
        //注册安全工程师
        etZcaqgcssl.setText(componyInfo.getZcaqgcsrys() + "");
        try {
            jsonArray = new JSONArray(componyInfo.getZcaqgcsJson());
            for (int i = 0; i < jsonArray.length(); i++) {
                JsonModel jsonModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), JsonModel.class);
                zcaqgcsList.add(jsonModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterZcaqgcs = new SafeInfoStaffAdapter(zcaqgcsList, getContext());
        listZcaqgcs.setAdapter(adapterZcaqgcs);
        //特种作业人员
        etTzzyrysl.setText(componyInfo.getTzzyrysl() + "");
        try {
            jsonArray = new JSONArray(componyInfo.getTzzyryJson());
            for (int i = 0; i < jsonArray.length(); i++) {
                TzzyryModel tzzyryModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), TzzyryModel.class);
                tzzyryList.add(tzzyryModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterTzzyry = new SafeInfoTzzyAdapter(tzzyryList, getContext());
        listTzzyry.setAdapter(adapterTzzyry);
        //住宿人员
        try {
            jsonArray = new JSONArray(componyInfo.getSsJson());
            for (int i = 0; i < jsonArray.length(); i++) {
                ZsryJsonModel zsryJsonModel = gson.fromJson(jsonArray.getJSONObject(i).toString(), ZsryJsonModel.class);
                zsryList.add(zsryJsonModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterZsry = new SafeInfoZsryAdapter(zsryList, getContext(), dataStructure);
        listZsry.setAdapter(adapterZsry);
        //房东信息
        etFdxm.setText(componyInfo.getFdxm());
        etFddh.setText(componyInfo.getFddh());
    }

    private void initView() {
        etQyfr = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_qyfr);
        etLxdh = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_lxdh);
        etDzyx = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_dzyx);
        etZyfzr = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_zyfzr);
        etZyfzrxxqk = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_zyfzrxxqk);
        etZyfzryddh = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_zyfzryddh);
        etZyfzrgddh = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_zyfzrgddh);
        etAqfzr = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_aqfzr);
        etAqfzrxxqk = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_anfzrxxqk);
        etAqfzryddh = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_aqfzryddh);
        etAqfzrgddh = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_aqfzrgddh);
        //从业人员
        etCyrysl = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_cyrysl);
        imgCyryAdd = (ImageView) view.findViewById(R.id.fragment_basic_info_cyrysl).findViewById(R.id.ry_add);
        listCyry = (ListView) view.findViewById(R.id.fragment_basic_info_cyrysl).findViewById(R.id.ry_list);
        //特种作业人员
        etTzzyrysl = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_tzzyrysl);
        imgTzzyryAdd = (ImageView) view.findViewById(R.id.tzzyry_add);
        listTzzyry = (ListView) view.findViewById(R.id.tzzyry_list);
        //专职安全生产管理
        etZzaqscsl = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_zzaqscglrysl);
        imgZzaqscAdd = (ImageView) view.findViewById(R.id.fragment_basic_info_zzaqglrysl).findViewById(R.id.ry_add);
        listZzaqsc = (ListView) view.findViewById(R.id.fragment_basic_info_zzaqglrysl).findViewById(R.id.ry_list);
        //专职应急管理
        etZzyjglsl = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_zzyjglrysl);
        imgZzyjglAdd = (ImageView) view.findViewById(R.id.fragment_basic_info_zzyjglrysl).findViewById(R.id.ry_add);
        listZzyjgl = (ListView) view.findViewById(R.id.fragment_basic_info_zzyjglrysl).findViewById(R.id.ry_list);
        //注册安全工程师
        etZcaqgcssl = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_zcaqgcssl);
        imgZcaqgcsAdd = (ImageView) view.findViewById(R.id.fragment_basic_info_zcaqgcssl).findViewById(R.id.ry_add);
        listZcaqgcs = (ListView) view.findViewById(R.id.fragment_basic_info_zcaqgcssl).findViewById(R.id.ry_list);
        //住宿人员
        etFdxm = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_fdxm);
        etFddh = (ClearEditText) view.findViewById(R.id.fragment_basic_info_et_fddh);
        imgZsryAdd = (ImageView) view.findViewById(R.id.zsry_add);
        listZsry = (ListView) view.findViewById(R.id.zsry_list);
        //两个按钮
        linearLayout = (LinearLayout) view.findViewById(R.id.fragment_staff_info_textview_ll);
        btnUp = (Button) view.findViewById(R.id.fragment_staff_info_btn_up);
        btnDown = (Button) view.findViewById(R.id.fragment_staff_info_btn_down);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.ry_add:
                    int tag = (int) v.getTag();
                    switch (tag) {
                        case 0:
                            cyryList.add(new JsonModel("","",1,0));
                            if (adapterCyry!=null) {
                                listCyry.setAdapter(adapterCyry);
                                adapterCyry.notifyDataSetChanged();
                            }else{
                                adapterCyry = new SafeInfoStaffAdapter(cyryList,getContext());
                                listCyry.setAdapter(adapterCyry);
                            }
                            break;
                        case 2:
                            zzaqscList.add(new JsonModel("","",1,0));
                            if (adapterZzaqsc!=null) {
                                listZzaqsc.setAdapter(adapterZzaqsc);
                                adapterZzaqsc.notifyDataSetChanged();
                            }else{
                                adapterZzaqsc = new SafeInfoStaffAdapter(zzaqscList,getContext());
                                listZzaqsc.setAdapter(adapterZzaqsc);
                            }
                            break;
                        case 3:
                            zzyjglList.add(new JsonModel("","",1,0));
                            if (adapterZzyjgl!=null) {
                                listZzyjgl.setAdapter(adapterZzyjgl);
                                adapterZzyjgl.notifyDataSetChanged();
                            }else{
                                adapterZzyjgl = new SafeInfoStaffAdapter(zzyjglList,getContext());
                                listZzyjgl.setAdapter(adapterZzyjgl);
                            }
                            break;
                        case 4:
                            zcaqgcsList.add(new JsonModel("","",1,0));
                            if (adapterZcaqgcs!=null) {
                                listZcaqgcs.setAdapter(adapterZcaqgcs);
                                adapterZcaqgcs.notifyDataSetChanged();
                            }else{
                                adapterZcaqgcs = new SafeInfoStaffAdapter(zcaqgcsList,getContext());
                                listZcaqgcs.setAdapter(adapterZcaqgcs);
                            }
                            break;

                    }
                    break;
                case R.id.zsry_add:
                    zsryList.add(new ZsryJsonModel("","","","","",""));
                    if (adapterZsry!=null) {
                        listZsry.setAdapter(adapterZsry);
                        adapterZsry.notifyDataSetChanged();
                    }else{
                        adapterZsry = new SafeInfoZsryAdapter(zsryList,getContext(),dataStructure);
                        listZsry.setAdapter(adapterZsry);
                    }
                    break;
                case R.id.tzzyry_add:
                    tzzyryList.add(new TzzyryModel("","","","","","",1,0));
                    if (adapterTzzyry!=null) {
                        listTzzyry.setAdapter(adapterTzzyry);
                        adapterTzzyry.notifyDataSetChanged();
                    }else{
                        adapterTzzyry = new SafeInfoTzzyAdapter(tzzyryList,getContext());
                        listTzzyry.setAdapter(adapterTzzyry);
                    }
                    break;
                case R.id.fragment_staff_info_btn_up:
                    switchPages(ComponyBasicInfoFragment.TAG, ComponyBasicInfoFragment.class);
                    break;
                case R.id.fragment_basic_info_et_anfzrxxqk:
                    showChooseDialog(commonList, etAqfzrxxqk, "安全负责人学习情况");
                    break;
                case R.id.fragment_basic_info_et_zyfzrxxqk:
                    showChooseDialog(commonList, etZyfzrxxqk, "主要负责人学习情况");
                    break;
                case R.id.fragment_staff_info_btn_down:
                    //先判断数据何不合格
                    isDataQualified();
                    break;
            }
        }
    }
    /**
     * @param epointName 显示内容的edittext
     * @param data       dialog显示的内容
     * @param methodName 判断是哪个Edittext
     * @desc 点击对应项弹出dialog供用户选择
     * @date 2018/2/21 11:14
     */
    private void showChooseDialog(final List<EnumModel> data, final EditText epointName, final String methodName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());// 自定义对话框
        final String[] array = data2Array(data);
        int which = 0;
        for (int i = 0; i < array.length; i++) {
            if (epointName.getText().toString().trim().equals(array[i])) {
                which = i;
                break;
            }
        }
        builder.setSingleChoiceItems(array, which, new DialogInterface.OnClickListener() {// 0默认的选中
            @Override
            public void onClick(DialogInterface dialog, final int which) {// which是被选中的位置
                epointName.setText(array[which]);
                switch (methodName) {
                    case "安全负责人学习情况":
                        componyInfo.setIsaqfzrxx(Integer.parseInt(commonList.get(which).getKey()));
                        break;
                    case "主要负责人学习情况":
                        componyInfo.setIszyfzrxx(Integer.parseInt(commonList.get(which).getKey()));
                        break;
                }
                dialog.dismiss();//随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }

    private void isDataQualified() {
        if (getEdittextContent(etQyfr).length()<1) {
            Toast.makeText(getContext(), "企业法人为必填项", Toast.LENGTH_SHORT).show();
            return;
        }else{
            componyInfo.setFddbr(getEdittextContent(etQyfr));
        }
        if (getEdittextContent(etLxdh).length()<1) {
            Toast.makeText(getContext(), "联系电话为必填项", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (ValidateUtil.isPhone(getEdittextContent(etLxdh))||ValidateUtil.isMobile(getEdittextContent(etLxdh))) {
                componyInfo.setLxdh(getEdittextContent(etLxdh));
            }else{
                Toast.makeText(getContext(), "请填写正确的联系方式", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (ValidateUtil.isValidEmail(getEdittextContent(etDzyx))) {
            componyInfo.setDzyx(getEdittextContent(etDzyx));
        }else{
            Toast.makeText(getContext(), "请填写正确的邮箱地址", Toast.LENGTH_SHORT).show();
            return;
        }
        //主要负责人
        if (getEdittextContent(etZyfzr).length()<1) {
            Toast.makeText(getContext(), "主要负责人为必填项", Toast.LENGTH_SHORT).show();
            return;
        }else{
            componyInfo.setZyfzr(getEdittextContent(etZyfzr));
        }
        if (getEdittextContent(etZyfzryddh).length()<1&&getEdittextContent(etZyfzrgddh).length()<1) {
            Toast.makeText(getContext(), "请至少填写主要负责人一种联系方式", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (getEdittextContent(etZyfzryddh).length()>0) {
                if (ValidateUtil.isPhone(getEdittextContent(etZyfzryddh))) {
                    componyInfo.setZyfzryddhhm(getEdittextContent(etZyfzryddh));
                }else{
                    Toast.makeText(getContext(), "请填写主要负责人正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (getEdittextContent(etZyfzrgddh).length()>0) {
                if (ValidateUtil.isMobile(getEdittextContent(etZyfzrgddh))) {
                    componyInfo.setZyfzrgddhhm(getEdittextContent(etZyfzrgddh));
                }else{
                    Toast.makeText(getContext(), "请填写主要负责人正确的固定电话", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        }
        componyInfo.setIszyfzrxx(getEdittextContent(etZyfzrxxqk).equals("是")?1:2);
        //安全负责人
        if (getEdittextContent(etAqfzr).length()<1) {
            Toast.makeText(getContext(), "安全负责人为必填项", Toast.LENGTH_SHORT).show();
            return;
        }else{
            componyInfo.setAqfzr(getEdittextContent(etAqfzr));
        }
        if (getEdittextContent(etAqfzryddh).length()<1&&getEdittextContent(etAqfzrgddh).length()<1) {
            Toast.makeText(getContext(), "请至少填写安全负责人的一种联系方式", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (getEdittextContent(etAqfzryddh).length()>0) {
                if (ValidateUtil.isPhone(getEdittextContent(etAqfzryddh))) {
                    componyInfo.setAqfzryddhhm(getEdittextContent(etAqfzryddh));
                }else{
                    Toast.makeText(getContext(), "请填写安全负责人正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (getEdittextContent(etAqfzrgddh).length()>0) {
                if (ValidateUtil.isMobile(getEdittextContent(etAqfzrgddh))) {
                    componyInfo.setAqfzrgddhhm(getEdittextContent(etAqfzrgddh));
                }else{
                    Toast.makeText(getContext(), "请填写安全负责人正确的固定电话", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        componyInfo.setIsaqfzrxx(getEdittextContent(etAqfzrxxqk).equals("是")?1:2);
        //从业人员
        if (getEdittextContent(etCyrysl).length()>0) {
            componyInfo.setCyrysl(Integer.parseInt((getEdittextContent(etCyrysl))));
        }
        List<JsonModel> jsonModelList = new ArrayList<>();
        for (int i = 0; i < cyryList.size(); i++) {
            if (cyryList.get(i).getLxdh().length()>0) {
                if (ValidateUtil.isPhone(cyryList.get(i).getLxdh())) {
                    jsonModelList.add(cyryList.get(i));
                }else{
                    Log.e(TAG, "isDataQualified: 从业人员的联系方式不对" );
                }
            }
        }
        componyInfo.setCyryJson(gson.toJson(jsonModelList));
        //专职安全生产
        if ((getEdittextContent(etZzaqscsl).length())>0) {
            componyInfo.setZzaqscglrys(Integer.parseInt(getEdittextContent(etZzaqscsl)));
        }
        jsonModelList = new ArrayList<>();
        for (int i = 0; i < zzaqscList.size(); i++) {
            if (zzaqscList.get(i).getLxdh().length()>0) {
                if (ValidateUtil.isPhone(zzaqscList.get(i).getLxdh())) {
                    jsonModelList.add(zzaqscList.get(i));
                }else{
                    Log.e(TAG, "isDataQualified: 专职安全生产人员的联系方式不对" );
                }
            }
        }
        componyInfo.setZzaqscryJson(gson.toJson(jsonModelList));
        //专职应急管理
        if (getEdittextContent(etZzyjglsl).length()>0) {
            componyInfo.setZzyjglrys(Integer.parseInt(getEdittextContent(etZzyjglsl)));
        }
        jsonModelList = new ArrayList<>();
        for (int i = 0; i < zzyjglList.size(); i++) {
            if (zzyjglList.get(i).getLxdh().length()>0) {
                if (zzyjglList.get(i).getLxdh().length()>0) {
                    if (ValidateUtil.isPhone(zzyjglList.get(i).getLxdh())) {
                        jsonModelList.add(zzyjglList.get(i));
                    }else{
                        Log.e(TAG, "isDataQualified: 专职应急管理人员的联系方式不对" );
                    }
                }
            }
        }
        componyInfo.setZzyjglryJson(gson.toJson(jsonModelList));
        //注册安全工程师
        if (getEdittextContent(etZcaqgcssl).length()>0) {
            componyInfo.setZcaqgcsrys(Integer.parseInt(getEdittextContent(etZcaqgcssl)));
        }
        jsonModelList = new ArrayList<>();
        for (int i = 0; i < zcaqgcsList.size(); i++) {
            if (zcaqgcsList.get(i).getLxdh().length()>0) {
                if (zcaqgcsList.get(i).getLxdh().length()>0) {
                    if (ValidateUtil.isPhone(zcaqgcsList.get(i).getLxdh())) {
                        jsonModelList.add(zcaqgcsList.get(i));
                    }else{
                        Log.e(TAG, "isDataQualified: 注册安全工程师的联系方式不对" );
                    }
                }
            }
        }
        componyInfo.setZcaqgcsJson(gson.toJson(jsonModelList));
        //住宿人员
        List<ZsryJsonModel> zsryModelList = new ArrayList<>();
        for (int i = 0; i < zsryList.size(); i++) {
            if (zsryList.get(i).getGlrylxdh().length()>0) {
                if (ValidateUtil.isPhone(zsryList.get(i).getGlrylxdh())) {
                    zsryModelList.add(zsryList.get(i));
                }else{
                    Log.e(TAG, "isDataQualified: 住宿管理人员的联系方式不对" );
                }
            }
        }
        componyInfo.setSsJson(gson.toJson(zsryModelList));
        //特种作业人员
        if (getEdittextContent(etTzzyrysl).length()>0) {
            componyInfo.setTzzyrysl(Integer.parseInt(getEdittextContent(etTzzyrysl)));
        }
        List<TzzyryModel> TzzyryModelList = new ArrayList<>();
        for (int i = 0; i < tzzyryList.size(); i++) {
            if (tzzyryList.get(i).getLxdh().length()>0) {
                if (ValidateUtil.isPhone(tzzyryList.get(i).getLxdh())) {
                    TzzyryModelList.add(tzzyryList.get(i));
                }else{
                    Log.e(TAG, "isDataQualified: 特种作业人员的联系方式不对" );
                }
            }
        }
        componyInfo.setTzzyryJson(gson.toJson(TzzyryModelList));
        if (getEdittextContent(etFddh).length()>0) {
            if (ValidateUtil.isPhone(getEdittextContent(etFddh))) {
                componyInfo.setFddh(getEdittextContent(etFddh));
            }else {
                Toast.makeText(getContext(), "请填写正确的房东电话", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        componyInfo.setFdxm(getEdittextContent(etFdxm));
        //如果能走到这一步证明数据全都合格了
        switchPages(ComponySafeInfoFragment.TAG, ComponySafeInfoFragment.class);
    }
    private String getEdittextContent(EditText editText){
        return editText.getText().toString().trim();
    }

    private void switchPages(String tag, Class<? extends Fragment> cls) {
        /**
         * 将当前显示的碎片进行隐藏，之后将要显示的页面显示出来
         */
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        // 隐藏显示的页面
        transaction.hide(mShowFragment);
        // 根据TAG去FragmentManager中进行查找碎片
        mShowFragment = fm.findFragmentByTag(tag);
        // 如果找到了，直接进行显示
        if (mShowFragment != null) {
            transaction.show(mShowFragment);
        } else {
            // 如果没找到，添加到容器中并且设置了一个标记
            try {
                // 使用反射进行了一个对象的实例化
                mShowFragment = cls.getConstructor().newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("which", which);
                bundle.putString("qyId", qyId);
                bundle.putSerializable("componyInfo", componyInfo);
                mShowFragment.setArguments(bundle);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            transaction.add(R.id.activity_add_enterprise_simple_fl, mShowFragment, tag);
        }
        transaction.commit();
    }
    /**
     * @param data 待转化数据源
     * @desc data2Array 将数据源转化为array
     * @date 2017/11/27 14:56
     */
    private String[] data2Array(List<EnumModel> data) {
        String[] array = new String[data.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = data.get(i).getValue();
        }
        return array;
    }
}
