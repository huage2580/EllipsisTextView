## textView 改造成可以缩略展开的demo

![view](https://github.com/huage2580/EllipsisTextView/blob/master/screenshot.png)

原理解析

```java
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

```
