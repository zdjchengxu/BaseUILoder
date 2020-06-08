package indi.zdj.djandroid.widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import indi.zdj.djandroid.R;

public class DownTimeTestPanel extends FrameLayout {

    private Context mContext;
    private Button btnBack;
    private onDtPanelListener mListener;
    private DownTimeView dtv;

    public DownTimeTestPanel(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.layout_dt, this);
        btnBack = findViewById(R.id.btn_dt_back);
        dtv = findViewById(R.id.dtv);

        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                assert mListener != null;
                mListener.onClickBack();
            }
        });
    }

    public void setDownNum(int downNum){
        dtv.setDownTime(downNum);
    }

    public void setmListener(onDtPanelListener mListener) {
        this.mListener = mListener;
    }

    public interface onDtPanelListener{
        void onClickBack();
    }
}
