package org.beahugs.rxpopup;

import android.view.View;

import org.beahugs.rxpopup.dialog.IDialog;
import org.beahugs.rxpopup.dialog.RxBaseDialog;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */
public class LoadDialog extends RxBaseDialog implements IDialog{
    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected View getDialogView() {
        return null;
    }


    public static class Builder{

    }
}
