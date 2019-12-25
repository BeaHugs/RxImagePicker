package org.beahugs.rxpopup.manager;


import org.beahugs.rxpopup.RxDialog;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */

public class DialogWrapper {

    private RxDialog.Builder dialog;//统一管理dialog的弹出顺序

    public DialogWrapper(RxDialog.Builder dialog) {
        this.dialog = dialog;
    }

    public RxDialog.Builder getDialog() {
        return dialog;
    }

    public void setDialog(RxDialog.Builder dialog) {
        this.dialog = dialog;
    }

}
