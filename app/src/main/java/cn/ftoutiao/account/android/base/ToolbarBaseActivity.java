package cn.ftoutiao.account.android.base;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.impl.IActionBarResIdProvider;

/**
 * Created by yangweichao on 2018/3/7.
 * 自定义toolbar 横屏导航栏
 */

public abstract class ToolbarBaseActivity extends BaseActivity implements IActionBarResIdProvider {


    protected Toolbar mToolbar;

    @Override
    protected void init() {
        setupActionBar();
    }

    private void setupActionBar() {
        if (mToolbar == null) {
            mToolbar = findViewById(getActionBarId());
        }
        if (mToolbar == null) {
            throw new RuntimeException("can not find toolbar, did you include in xml??");
        }
        setSupportActionBar(mToolbar);
        int iconResId = provideNavigationIcon();
        if (iconResId > 0) {
            mToolbar.setNavigationIcon(iconResId);
            try {
                Field mNavButtonView = Toolbar.class.getDeclaredField("mNavButtonView");
                if (mNavButtonView != null){
                    mNavButtonView.setAccessible(true);
                    AppCompatImageButton nbv = (AppCompatImageButton) mNavButtonView.get(mToolbar);

                    nbv.setMinimumWidth((int) dp2px(45f));
                    nbv.setPadding((int) dp2px(10f),0,30,0);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        } else {
            mToolbar.setNavigationIcon(null);
        }

        mToolbar.getNavigationIcon().clearColorFilter();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (getSupportActionBar() == null) {
            throw new RuntimeException(
                    "actionbar not found, have you called setSupportActionBar method");
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
    }


    protected int provideNavigationIcon() {
        return R.drawable.com_back_selector;
    }

    protected void setDefaultTitle(int resId) {
        setDefaultTitle(getString(resId));
    }

    protected void setDefaultTitle(String title) {
        TextView textView = mToolbar.findViewById(getActionBarTitleId());
        textView.setText(title);

    }

    protected void setDefaultTitleColor(int color) {
        TextView textView = mToolbar.findViewById(getActionBarTitleId());
        textView.setTextColor(color);
    }

    protected void setToolbarBg(int color) {
        if (mToolbar != null)
            mToolbar.setBackgroundResource(color);
    }
}
