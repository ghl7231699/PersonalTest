package com.example.liangge.rxjavatest.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.common.utils.FileUtil;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guhongliang on 2017/4/13.
 */

public class PdfActivity extends BaseActivity implements OnPageChangeListener {
//    @BindView(R.id.pdf_view)
//    PDFView mPdfView;
    @BindView(R.id.text)
    TextView mText;
    String pdfName;

    @Override
    public int getLayoutId() {
        return R.layout.pdf_activity_layout;
    }

    @Override
    public void initView() {

    }

    private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage)
            setTitle(pdfName = assetFileName);
        try {
            File file = new File(assetFileName, "LoadRunner11-中文教程.pdf");
//            mPdfView.fromFile(file)
//                    //                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
//                    .defaultPage(1)//默认展示第一页
//                    .onPageChange(this)//监听页面切换
//                    .load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        pdfName = Environment.getExternalStorageDirectory() +
                "/GlideCache";
        FileUtil.create(pdfName);
        display(pdfName, false);
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        mText.setText(page + "/" + pageCount);
    }

}
