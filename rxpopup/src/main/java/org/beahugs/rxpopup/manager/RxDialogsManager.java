package org.beahugs.rxpopup.manager;


import org.beahugs.rxpopup.RxDialog;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author: wangyibo
 * @Version: 1.0
 */

public class RxDialogsManager {

    private volatile boolean showing = false;//是否有dialog在展示
    private ConcurrentLinkedQueue<DialogWrapper> dialogQueue = new ConcurrentLinkedQueue<>();

    private RxDialogsManager() {
    }

    public static RxDialogsManager getInstance() {
        return DialogHolder.instance;
    }

    private static class DialogHolder {
        private static RxDialogsManager instance = new RxDialogsManager();
    }

    /**
     * 请求加入队列并展示
     *
     * @param dialogWrapper DialogWrapper
     * @return 加入队列是否成功
     */
    public synchronized boolean requestShow(DialogWrapper dialogWrapper) {
        boolean b = dialogQueue.offer(dialogWrapper);
        checkAndDispatch();
        return b;
    }

    /**
     * 结束一次展示 并且检查下一个弹窗
     */
    public synchronized void over() {
        showing = false;
        next();
    }

    private synchronized void checkAndDispatch() {
        if (!showing) {
            next();
        }
    }

    /**
     * 弹出下一个弹窗
     */
    private synchronized void next() {
        DialogWrapper poll = dialogQueue.poll();
        if (poll == null) return;
        RxDialog.Builder dialog = poll.getDialog();
        if (dialog != null) {
            showing = true;
            dialog.show();
        }
    }


}
