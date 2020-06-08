package indi.zdj.djandroid.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import indi.zdj.djandroid.R;

public class LoadingDialog extends Dialog {

    private Context mContext;
    private ImageView ivLoad;
    private ObjectAnimator objectAnimator;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
        mContext = context;
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        setContentView(rootView);

//        setContentView(rootView ,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ivLoad = findViewById(R.id.iv_loading);
        setCancelable(false);
//        getWindow().setBackgroundDrawableResource(R.color.colorWhite00F);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.MATCH_PARENT;
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                attr.gravity = Gravity.CENTER_VERTICAL;
                window.setAttributes(attr);
            }
        }
        objectAnimator = ObjectAnimator.ofFloat(ivLoad,"rotation",380f);
        objectAnimator.setDuration(1500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void showLoading(){
        show();
        objectAnimator.start();
    }

    public void dismissLoading(){
        objectAnimator.cancel();
        dismiss();
    }

    @Override
    public void show() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    private void fullScreenImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }

}
