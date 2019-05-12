package com.example.myapplication.presentation.presenter.main;

import com.example.myapplication.BaseApp;
import com.example.myapplication.framework.retrofit.model.Categories;
import com.example.myapplication.framework.retrofit.model.Category;
import com.example.myapplication.framework.retrofit.services.NetworkCallback;
import com.example.myapplication.framework.retrofit.services.category.CategoryServices;
import com.example.myapplication.framework.retrofit.services.image.ImageServices;
import com.example.myapplication.presentation.presenter.AbstractBasePresenter;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.example.myapplication.RecepiesConstant.CACHE;

public class MainPresenterImpl extends AbstractBasePresenter<MainScreenPresenter.MainContractView>
        implements MainScreenPresenter<MainScreenPresenter.MainContractView> {

//    @Inject
//    CategoryServices categoryService;
//    @Inject
//    ImageServices imageServices;

    private List<Category> list;

    public MainPresenterImpl() {
        //BaseApp.getComponent().inject(this);
    }

    private void setList(List<Category> list) {
        if (!list.isEmpty())
            list.clear();

        this.list.addAll(list);
    }

    @Override
    public void fetch() {
        Timber.d("categories download started!");
//        categoryService.getCategories(CACHE, new NetworkCallback<Categories>() {
//            @Override
//            public void onResponse(Categories categories) {
//                Timber.d("download %s category", categories.getCategories().size());
//                list.addAll(categories.getCategories());
//
//                //adapter.setList(categories.getCategories());
//                //adapter.notifyDataSetChanged();
//                Timber.d("categories download end!");
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                Timber.e("Failed get categories. Messages: %s",throwable.getMessage());
//            }
//        });
    }
}
