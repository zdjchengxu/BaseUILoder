package indi.zdj.djandroid;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.flipboard.bottomsheet.BottomSheetLayout;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indi.zdj.djandroid.widget.BslMainView;
import indi.zdj.djandroid.widget.FlowLayout;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_main_row_down)
    ImageView ivMainRowDown;
    @BindView(R.id.bsl_main)
    BottomSheetLayout bslMain;
    @BindView(R.id.fl_main)
    FlowLayout flMain;
    private TranslateAnimation translateDown;
    private TranslateAnimation translateUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initMatcher();
    }



    private void initView() {
      /*  List<String> data = new ArrayList<>();
        data.add("aewfawe");
        data.add("aweoglnkwelknl");
        data.add("埃尔文赶快来玩");
        data.add("安慰");
        data.add("特纳老婆婆");
        data.add("啊喂啊喂料理");
        data.add("跑品牌文化");
        data.add("侧啊啊我");
        data.add("2a1we561");
        data.add("wuloawnlnaklnklglew1");
        flMain.setTextList(data);
        flMain.setListener(position -> {
            Toast.makeText(this,data.get(position),Toast.LENGTH_SHORT).show();
        });*/
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

    private void initMatcher() {
        String test = "<div class='ocr_page' id='page_1' title='image \"\"; bbox 0 0 218 72; ppageno 0'>\n" +
                "       <div class='ocr_carea' id='block_1_1' title=\"bbox 3 33 210 50\">\n" +
                "        <p class='ocr_par' id='par_1_1' lang='eng' title=\"bbox 3 33 210 50\">\n" +
                "         <span class='ocr_line' id='line_1_1' title=\"bbox 3 33 210 50; baseline 0.014 -2; x_size 20.592592; x_descenders 5.1481481; x_ascenders 5.1481481\"><span class='ocrx_word' id='word_1_1' title='bbox 3 33 210 50; x_wconf 72'><strong><em>1234567891123456</em></strong></span> \n" +
                "         </span>\n" +
                "        </p>\n" +
                "       </div>\n" +
                "      </div>";
//        String test = "aae1e2g3456l7n8l9";

        Log.e("tag_z : ", getTelNum(test));

    }


    //        private static Pattern pattern = Pattern.compile("(1|861)\\d{10}$*");
    private static Pattern pattern = Pattern.compile("\\d{16}");

    private static StringBuilder bf = new StringBuilder();

    public static String getParam(String sParam){

        Pattern pat = Pattern.compile("[^0-9]");

        if(TextUtils.isEmpty(sParam)){
            return "";
        }

//        Log.e("tag_z","sParam : "+sParam);

        Matcher matcher = pat.matcher(sParam);
        String trim = matcher.replaceAll("").trim();
        String substring = trim.substring(trim.toCharArray().length - 11, trim.toCharArray().length);
        /*bf.delete(0, bf.length());

        while (matcher.find()) {
            bf.append(matcher.group()).append("\n");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }*/
        return substring;
    }

    public static String getTelNum(String sParam){
        if(TextUtils.isEmpty(sParam)){
            return "";
        }

        Log.e("tag_z","sParam : "+sParam);

        Matcher matcher = pattern.matcher(sParam.trim());
        bf.delete(0, bf.length());

        while (matcher.find()) {
            bf.append(matcher.group()).append("\n");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }


}
