package indi.zdj.djandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import indi.zdj.djandroid.R;

public class BslMainView extends FrameLayout {
    private Context mContext;

    public BslMainView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.layout_bsl_main_test,this);
    }


}
