package com.ruanyun.chezhiyi.commonlib.util;


import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.ruanyun.chezhiyi.commonlib.view.widget.RyLodingDialog;

/**
 * Description:
 * author: jery on 2016/5/6 10:54.
 */
public class LoadingDialogHelper {
    private RyLodingDialog ryLodingDialog = null;
    private static final String TAG="RyLodingDialog";
  /*  public static void showLoading(FragmentActivity activity, String msg) {
        if (ryLodingDialog == null) {
            ryLodingDialog = RyLodingDialog.newInstance();
        }
        ryLodingDialog.setMessage(msg);
        ryLodingDialog.show(activity.getSupportFragmentManager(), TAG);

    }*/

    public  void showLoading(FragmentActivity activity, String msg) {
        if (ryLodingDialog == null) {
            ryLodingDialog = RyLodingDialog.newInstance();
        }
        ryLodingDialog.setMessage(msg);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (null == fragment) {
            ft.add(ryLodingDialog, TAG).show(ryLodingDialog).commitAllowingStateLoss();
        } else {
            ((RyLodingDialog)fragment).setMessage(msg);
            ft.show(fragment).commit();
        }
    }

    public  void showIgnoreStatu(FragmentActivity activity, String msg) {
        if (ryLodingDialog == null) {
            ryLodingDialog = RyLodingDialog.newInstance();
        }
        ryLodingDialog.setMessage(msg);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (null == fragment) {
            ft.add(ryLodingDialog, TAG).show(ryLodingDialog).commitAllowingStateLoss();
        } else {
            ((RyLodingDialog)fragment).setMessage(msg);
            ft.show(fragment).commitAllowingStateLoss();
        }
    }

    public  void showLoading(FragmentActivity activity, @StringRes int stringId) {
        showLoading(activity, activity.getResources().getString(stringId));
    }

    public  void dissMiss() {
        try {
            if (ryLodingDialog != null) {
                ryLodingDialog.dismiss();
                /*if( ryLodingDialog.getActivity()!=null){
                    ryLodingDialog.getActivity().getSupportFragmentManager()
                            .beginTransaction().detach(ryLodingDialog).commit();
                }*/
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
}
