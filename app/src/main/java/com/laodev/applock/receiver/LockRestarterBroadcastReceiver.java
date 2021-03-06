package com.laodev.applock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.laodev.applock.base.AppConstants;
import com.laodev.applock.services.BackgroundManager;
import com.laodev.applock.services.LockService;
import com.laodev.applock.utils.MainUtil;

public class LockRestarterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean lockState= MainUtil.getInstance().getBoolean(AppConstants.LOCK_STATE);
        if (intent != null && lockState) {
            String type = intent.getStringExtra("type");
            if (type.contentEquals("lockservice"))
                BackgroundManager.getInstance().init(context).startService(LockService.class);
            else if (type.contentEquals("startlockserviceFromAM")) {
                if (!BackgroundManager.getInstance().init(context).isServiceRunning(LockService.class)) {
                    BackgroundManager.getInstance().init(context).startService(LockService.class);
                }
                BackgroundManager.getInstance().init(context).startAlarmManager();
            }
        }
    }
}
