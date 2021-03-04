package com.example.textviewmore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * ⣿⣿⣿⣿⣿⣿⢟⣡⣴⣶⣶⣦⣌⡛⠟⣋⣩⣬⣭⣭⡛⢿⣿⣿⣿⣿
 * ⣿⣿⣿⣿⠋⢰⣿⣿⠿⣛⣛⣙⣛⠻⢆⢻⣿⠿⠿⠿⣿⡄⠻⣿⣿⣿
 * ⣿⣿⣿⠃⢠⣿⣿⣶⣿⣿⡿⠿⢟⣛⣒⠐⠲⣶⡶⠿⠶⠶⠦⠄⠙⢿
 * ⣿⠋⣠⠄⣿⣿⣿⠟⡛⢅⣠⡵⡐⠲⣶⣶⣥⡠⣤⣵⠆⠄⠰⣦⣤⡀
 * ⠇⣰⣿⣼⣿⣿⣧⣤⡸ ⣿⡀⠂⠁⣸⣿⣿⣿⣿⣇⠄⠈⢀⣿⣿⠿
 * ⣰⣿⣿⣿⣿⣿⣿⣿⣷⣤⣈⣙⠶⢾⠭⢉⣁⣴⢯⣭⣵⣶⠾⠓⢀⣴
 * ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣉⣤⣴⣾⣿⣿⣦⣄⣤⣤⣄⠄⢿⣿
 * ⣿⣿⣿⣿⣿⣿⣿⣿⠿⠿⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠈⢿
 * ⣿⣿⣿⣿⣿⣿⡟⣰⣞⣛⡒⢒⠤⠦⢬⣉⣉⣉⣉⣉⣉⣉⡥⠴⠂⢸
 * ⠻⣿⣿⣿⣿⣏⠻⢌⣉⣉⣩⣉⡛⣛⠒⠶⠶⠶⠶⠶⠶⠶⠶⠂⣸⣿
 * ⣥⣈⠙⡻⠿⠿⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⣿⠿⠛⢉⣠⣶⣶⣿⣿
 * ⣿⣿⣿⣶⣬⣅⣒⣒⡂⠈⠭⠭⠭⠭⠭⢉⣁⣄⡀⢾⣿⣿⣿⣿⣿⣿
 *
 *
 * Created on 2021/3/4 15:22.
 * @author hua
 * @file EllipsisTextView
 */
public class EllipsisTextView extends AppCompatTextView {
    //用于缩略的符号
    public static String EXPAND_TEXT = "…";
    //箭头和缩略符之间的空格
    public static int SPACE_WITH_ICON = 1;


    private boolean hasEllipsis = false;
    //绘制箭头
    private Bitmap moreIcon;
    private Rect iconRect = new Rect();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public EllipsisTextView(Context context) {
        super(context);
    }

    public EllipsisTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsisTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //init
    {
        moreIcon = BitmapFactory.decodeResource(getResources(),R.drawable.arrow);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getLineCount() < getMaxLines()){
            return;
        }
        hasEllipsis = getText().toString().endsWith(EXPAND_TEXT);

        if (!hasEllipsis){
            return;
        }
        //绘制箭头
        int iconWidth = 48;
        int iconHeight = 48;
        int left = getMeasuredWidth() - getCompoundPaddingRight() - iconWidth;
        int top = getMeasuredHeight() - getCompoundPaddingBottom() - iconHeight;
        int right = left + iconWidth;
        int bottom = top + iconHeight;
        iconRect.set(left,top,right,bottom);
        canvas.drawBitmap(moreIcon,null,iconRect,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getLineCount() < getMaxLines()){//内容不够最大行数
            return;
        }
        //添加缩略符
        int lastLine = getMaxLines() -1;
        int lineStartIndex = getLayout().getLineStart(lastLine);
        int lineEllipsisStart = lineStartIndex + getLayout().getEllipsisStart(lastLine);
        int ellipsisCount = getLayout().getEllipsisCount(lastLine);
        if (ellipsisCount == 0){//刚好最后一行，不用缩略
            return;
        }
        int expandTextLength = EXPAND_TEXT.length();
        String text = getText().subSequence(0,lineEllipsisStart - expandTextLength + 1 - SPACE_WITH_ICON) + EXPAND_TEXT;
        setText(text);
    }

}
