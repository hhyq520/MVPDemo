package com.ztd.mvpstandardpro_as.mvpactivity;

import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ztd.mvpstandardpro_as.R;
import com.ztd.mvpstandardpro_as.annotation.LayoutId;
import com.ztd.mvpstandardpro_as.base.BaseActivity;
import com.ztd.mvpstandardpro_as.base.BasePresenter;
import com.ztd.mvpstandardpro_as.bean.Siteinfo;
import com.ztd.mvpstandardpro_as.mvpactivity.adapter.ComSelRecyAdapter;
import com.ztd.mvpstandardpro_as.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018-09-26.
 */
@LayoutId(id = R.layout.com_select)
public class CommonSelectActivity extends BaseActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edit_listcode)
    EditText editListcode;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    MyTestWatcher watcher;
    private List<Siteinfo> itemList=new ArrayList<Siteinfo>();
    ComSelRecyAdapter adapter;
    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        editListcode.addTextChangedListener(watcher);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView testview1=(TextView)view.findViewById(R.id.tv_no);
                TextView testview2=(TextView)view.findViewById(R.id.tv_name);
                Intent intent=new Intent();
                intent.putExtra("data_codeid", testview1.getText().toString().trim());
                intent.putExtra("data_codename",testview2.getText().toString().trim());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    protected void initView() {
        watcher=new MyTestWatcher();
        itemList=daoSession.getSiteinfoDao().loadAll();
        adapter=new ComSelRecyAdapter(itemList,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycleView.setHasFixedSize(true);
        recycleView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 3, R.color.dark_gray_text_color));
        recycleView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return this.getClass().getAnnotation(LayoutId.class).id();
    }

    @Override
    protected void receiveScancode(String billcode) {

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    private class MyTestWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            List<Siteinfo> replaceList=itemList;
            if(itemList.size()>0)
            {
                if("".equals(editListcode.getText().toString()))
                {
                    adapter.setNewData(itemList);
                } else {
                    String inputText=editListcode.getText().toString();
                    List<Siteinfo> tempList=new ArrayList<Siteinfo>();
                    for (Siteinfo item : itemList) {
                        if(TextUtils.isEmpty(item.getSiteCode()) || TextUtils.isEmpty(item.getSiteName()))
                        {
                            continue;
                        }
                        if(item.getSiteCode().contains(inputText) || item.getSiteName().contains(inputText))
                        {
                            tempList.add(item);
                        }
                    }
                    itemList=tempList;
                    adapter.setNewData(itemList);
                    itemList=replaceList;
                }

            }
        }
    }
}
