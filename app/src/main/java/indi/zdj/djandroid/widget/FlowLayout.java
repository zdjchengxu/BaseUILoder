package indi.zdj.djandroid.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import indi.zdj.djandroid.R;

public class FlowLayout extends ViewGroup {

    private List<List<View>> allViews;
    private List<Integer> linesHeight;
    private static final int DIVIDER = 10;
    private int horizontalDivider;
    private int verticalDivider;
    private List<String> textList;
    private Context mContext;

    private OnFlowLayoutItemClick mListener;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        assert attrs != null;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        horizontalDivider = (int) typedArray.getDimension(R.styleable.FlowLayout_horizontalDivider, 0);
        verticalDivider = (int) typedArray.getDimension(R.styleable.FlowLayout_verticalDivider, 0);
        mContext = context;
    }

    public void setListener(OnFlowLayoutItemClick listener) {
        this.mListener = listener;
    }

    public void setTextList(List<String> data) {
        this.textList = data;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        //获取限制高宽
        int selfWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int selfHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        //记录当前行的宽度和高度
        //宽度是当前行的子View的宽度之和
        int lineWidth = 0;
        //高度是当前行所有子View中高度最大值
        int lineHeight = 0;

        //流式布局的宽和高
        //宽为每一行的宽度最大值
        int flowLayoutWidth = 0;
        //高为每一行累加
        int flowLayoutHeight = 0;



        //保寸一行子View
        List<View> lineView = new ArrayList<>();

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //调用系统已有的测量子View的方法
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childeWidthSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, child.getLayoutParams().width);
            int childeHeightSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, child.getLayoutParams().height);

            child.measure(childeWidthSpec, childeHeightSpec);

            //获取子View的宽高
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (lineWidth + childWidth > (selfWidthSize - getPaddingLeft() - getPaddingRight())) {
                allViews.add(lineView);
                lineView = new ArrayList<>();
                flowLayoutWidth = Math.max(flowLayoutWidth, lineWidth);
                flowLayoutHeight += lineHeight + verticalDivider;
                linesHeight.add(flowLayoutHeight);
                lineWidth = 0;
                lineHeight = 0;
            }
            lineWidth = lineWidth + childWidth + horizontalDivider;
            lineHeight = Math.max(lineHeight, childHeight);
            lineView.add(child);


            if (i + 1 == childCount) {
                allViews.add(lineView);
                flowLayoutHeight += lineHeight + verticalDivider;
                linesHeight.add(flowLayoutHeight);
            }

        }

        Log.e("tag_z", "allVIew : " + allViews.size());

        int realWidth = (selfWidthMode == MeasureSpec.EXACTLY) ? selfWidthSize : flowLayoutWidth + getPaddingLeft() + getPaddingRight();
        int realHeight = (selfHeightMode == MeasureSpec.EXACTLY) ? selfHeightSize : flowLayoutHeight + getPaddingTop() + getPaddingBottom();

        //测量本身高宽
        setMeasuredDimension(realWidth, realHeight);

    }

    private void init() {
        allViews = new ArrayList<>();
        linesHeight = new ArrayList<>();
        /*for (int i = 0; i < textList.size(); i++) {
            TextView tvChild = new TextView(mContext, null, R.style.flowLayout_text);
            tvChild.setText(textList.get(i));
            tvChild.setTextSize(16);
            tvChild.setBackgroundResource(R.drawable.tv_bg_blue_corner);
            tvChild.setPadding(22, 6, 22, 6);
            tvChild.setTextColor(mContext.getResources().getColor(R.color.white));
            addView(tvChild);
            int finalI = i;
            tvChild.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onClick(finalI);
                }
            });
        }*/
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCounts = allViews.size();


        //起点
        int currentX = getPaddingLeft();
        int currentY = 0;

        for (int i = 0; i < lineCounts; i++) {
            //取出一行
            List<View> lineView = allViews.get(i);
            int lineHeight = linesHeight.get(i);
            for (int j = 0; j < lineView.size(); j++) {
                View child = lineView.get(j);
                int left = currentX;
                int top = currentY;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                child.layout(left  , top + getPaddingTop() , right + getPaddingRight() , bottom + getPaddingBottom());
                currentX = right + horizontalDivider;
            }
            currentX = getPaddingLeft();
            currentY = lineHeight;
        }

    }

    public interface OnFlowLayoutItemClick {
        void onClick(int position);
    }


}
