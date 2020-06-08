package indi.zdj.djandroid.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indi.zdj.djandroid.R;
import indi.zdj.djandroid.widget.DownTimeTestPanel;
import timber.log.Timber;

public class DownTimeTestActivity extends AppCompatActivity {

    @BindView(R.id.bsl_dt)
    BottomSheetLayout bslDt;
    @BindView(R.id.tv_count)
    TextView tvCount;
    private DownTimeTestPanel dtPanel;

    Timer dtTimer;
    TimerTask dtTimerTask;

    private long mLastActionTimes;
    private int COUNTDOWN = 20;
    private int countDown = COUNTDOWN;

    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_time_test);
        ButterKnife.bind(this);
        initPanel();
    }

    private void initPanel() {
        dtPanel = new DownTimeTestPanel(this);
        dtPanel.setmListener(() -> {
//            clearTimer();
            if (bslDt.isSheetShowing()) {
                bslDt.dismissSheet();
            }
        });
        bslDt.addOnSheetDismissedListener(new OnSheetDismissedListener() {
            @Override
            public void onDismissed(BottomSheetLayout bottomSheetLayout) {
                type = 0;
            }
        });
        mLastActionTimes = 0;
    }

    public void clearTimer() {
        if (dtTimer != null) {
            dtTimer.cancel();
            dtTimer = null;
        }
        if (dtTimerTask != null) {
            dtTimerTask.cancel();
            dtTimerTask = null;
        }

    }

    public void startTimer() {
        countDown = COUNTDOWN;
        mLastActionTimes = System.currentTimeMillis();
        if (dtTimer == null) {
            dtTimer = new Timer();
            if (dtTimerTask == null) {
                dtTimerTask = new ITimerTask();
            }
            dtTimer.schedule(dtTimerTask, 0, 1000);
        }
    }


    @OnClick({R.id.btn_on, R.id.btn_off, R.id.tv_action_dt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_on:
                startTimer();
                break;
            case R.id.btn_off:
                clearTimer();
                break;
            case R.id.tv_action_dt:
                if (!bslDt.isSheetShowing()) {
                    type = 1;
                    bslDt.showWithSheetView(dtPanel);
                    startTimer();
                }
                break;
        }
    }


    private class ITimerTask extends TimerTask {
        @Override
        public void run() {
            Timber.e("------run------");
            if (System.currentTimeMillis() - mLastActionTimes > 1000 * COUNTDOWN) {
                //时间到了
                handler.sendEmptyMessage(1);
            } else {
                countDown--;
                handler.sendEmptyMessage(2);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    clearTimer();
                    if (bslDt.isSheetShowing()) {
                        bslDt.dismissSheet();
                    }
                    type = 0;
//                    bslDt.dismissSheet();
                    break;
                case 2:
                    Timber.e("type : " + type);
                    if (type == 0) {
                        tvCount.setText(countDown + "s");
                    } else {
                        dtPanel.setDownNum(countDown);
                    }
                    break;
            }
        }
    };


}
