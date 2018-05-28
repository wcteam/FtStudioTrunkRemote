package cn.ftoutiao.account.android.activity.notebook.presenter;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.db.ATypeListEntity;
import cn.ftoutiao.account.android.model.db.CategoryEntity;

/**
 * Created by weichyang on 2018/3/8.
 */

public class AddCategoryPresenter extends BasePresenter<AddCategoryContract.View> implements AddCategoryContract.Presenter {
    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public AddCategoryPresenter(AddCategoryContract.View pView) {
        super(pView);
    }


    @Override
    public void addCategory(CategoryEntity category) {
        mView.showLoadingDialog(true);
        request().addCategory(UrlConstans.BASE_URL + UrlConstans.REQUEST_ADDBOOKCATEGORY
                , category.aId, category.rId, category.cName, category.color, category.icon).enqueue(new BindCallback<CategoryEntity>() {
            @Override
            public void succeed(@NonNull CategoryEntity pData) {
                super.succeed(pData);
                mView.addCategorySuccess(pData);
                mView.hideLoadingDialog();

            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                mView.hideLoadingDialog();
            }
        });
    }

    @Override
    public void requestSortCategoryList(ATypeListEntity aTypeListEntity) {
        mView.showLoadingDialog(true);
        request().requestSortCategory(UrlConstans.BASE_URL + UrlConstans.REQUEST_SORTBOOKCATEGORY,
                Integer.valueOf(aTypeListEntity.aId),
                aTypeListEntity.incomeSeq1, aTypeListEntity.incomeSeq2,
                aTypeListEntity.outgoSeq1, aTypeListEntity.outgoSeq2).enqueue(new BindCallback<ATypeListEntity>() {
            @Override
            public void succeed(@NonNull ATypeListEntity pData) {
                super.succeed(pData);
                mView.sortCategoryListSuccess(pData);
                mView.hideLoadingDialog();
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                mView.hideLoadingDialog();
                Toaster.show(pE.getMsg());
            }
        });

    }
}

