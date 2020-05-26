package indi.zdj.djandroid.util_widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class IView extends View {
    private Context mContext;
    private int rootViewResId;

    public IView(Builder context) {
        super(context.mContext);
        mContext = context.mContext;
        rootViewResId = context.rootViewResId;
        initView();
    }

    private void initView() {
        if (mContext != null && rootViewResId != 0) {
            Log.e("tag_z","来啦");
            inflate(mContext,rootViewResId,null);
        }
    }

    public static final class Builder {
        private Context mContext;
        private int rootViewResId;

        public Builder context(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder rootViewResId(int resId) {
            this.rootViewResId = resId;
            return this;
        }

        public IView build() {
            return new IView(this);
        }
    }
}
