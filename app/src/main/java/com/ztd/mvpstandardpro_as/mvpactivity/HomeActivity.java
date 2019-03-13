package com.ztd.mvpstandardpro_as.mvpactivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.google.zxing.client.android.CaptureActivity;
import com.ztd.mvpstandardpro_as.R;
import com.ztd.mvpstandardpro_as.annotation.LayoutId;
import com.ztd.mvpstandardpro_as.base.BaseActivity;
import com.ztd.mvpstandardpro_as.base.BasePresenter;
import com.ztd.mvpstandardpro_as.bean.eventbus.ScanEnity;
import com.ztd.mvpstandardpro_as.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018-09-26.
 */
@LayoutId(id = R.layout.activity_home)
public class HomeActivity extends BaseActivity {
    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return this.getClass().getAnnotation(LayoutId.class).id();
    }

    @Override
    protected void receiveScancode(String billcode) {

    }


    @OnClick({R.id.img_set, R.id.img_zc, R.id.img_xc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_set:
                Intent intent=new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.img_zc:
                String sitecode=(String) SpUtils.get("login_sitecode","");
                String emcode=(String) SpUtils.get("login_empcode","");
                zxing();
                break;
            case R.id.img_xc:
                tupian();
                break;
        }
    }

    private void tupian() {
        //单选
        RxPicker.of().start(this).subscribe(new Consumer<List<ImageItem>>() {
            @Override public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                //得到结果
            }
        });
        //多选
//        RxPicker.of()
//                .single(false)
//                .camera(true)
//                .limit(3,9)
//                .start(this)
//                .subscribe(new Consumer<List<ImageItem>>() {
//                    @Override public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
//                        //得到结果
//                    }
//                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SCAN_QUEST_CODE){
            String result_value = data.getStringExtra("result");
            if(result_value!=null)
            toast(result_value);
        }
    }
}
