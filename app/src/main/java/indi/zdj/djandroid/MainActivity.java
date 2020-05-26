package indi.zdj.djandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.flipboard.bottomsheet.BottomSheetLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indi.zdj.djandroid.util_widget.IView;
import indi.zdj.djandroid.widget.BslMainView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_main_row_down)
    ImageView ivMainRowDown;
    @BindView(R.id.bsl_main)
    BottomSheetLayout bslMain;
    private TranslateAnimation translateDown;
    private TranslateAnimation translateUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

    }


    private void initIvRowDownAnimation() {
        //相对于自己的高度往下平移

        translateDown = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateDown.setDuration(500);//动画时间500毫秒
        //相对于自己的高度往上平移
        translateUp = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -0.5f);
        translateUp.setDuration(500);
        translateDown.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMainRowDown.startAnimation(translateUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        translateUp.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivMainRowDown.startAnimation(translateDown);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivMainRowDown.startAnimation(translateDown);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (translateDown == null && translateUp == null) {
            initIvRowDownAnimation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (translateDown != null) {
            translateDown.cancel();
            translateDown.setAnimationListener(null);
        }
        if (translateUp != null) {
            translateUp.cancel();
            translateDown.setAnimationListener(null);
        }
    }

    @OnClick(R.id.tv_main_next)
    public void onViewClicked() {
//        IView iView = new IView.Builder()
//                .rootViewResId(R.layout.layout_bsl_main_test)
//                .context(this)
//                .build();
        BslMainView iView = new BslMainView(this);
        bslMain.showWithSheetView(iView);
    }
}
