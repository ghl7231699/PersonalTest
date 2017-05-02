package com.example.liangge.rxjavatest.common.rx.subscriber;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 类名称：ProgressDialogHandler
 * 类描述：ProgressDialog管理类
 * 创建人：ghl
 * 创建时间：2017/5/2 14:12
 * 修改人：ghl
 * 修改时间：2017/5/2 14:12
 *
 * @version v1.0
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 0;

    private ProgressDialog mProgressDialog;
    private OnProgressCancelListener mProgressCancelListener;
    private Context context;

    public ProgressDialogHandler(Context context) {
        this(context, false);
    }

    public ProgressDialogHandler(Context context,
                                 boolean cancelable) {
        super();
        this.context = context;

        initProgressDialog();
    }


    private void initProgressDialog() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setTitle("提示");
            mProgressDialog.setMessage("加载中.........");
            mProgressDialog.setCancelable(false);
        }
    }

    public void showProgressDialog() {

        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

    public interface OnProgressCancelListener {

        void onCancelProgress();
    }
}
