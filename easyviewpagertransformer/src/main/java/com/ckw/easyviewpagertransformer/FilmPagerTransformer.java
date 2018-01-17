package com.ckw.easyviewpagertransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class FilmPagerTransformer implements ViewPager.PageTransformer {
    private float mMinAlpha = 0.6f;
    private float transitionY = 90.0f;

    /**
     * Position of page relative to the current front-and-center
     * position of the pager. 0 is front and center. 1 is one full
     * page position to the right, and -1 is one page position to the left
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {
        if (position<=-1||position>=1){//中间两边的ViewPager，已经彻底移出
            page.setAlpha(mMinAlpha);
            page.setTranslationY(transitionY);
        } else if (position < 1) {//范围是：[-1,1]
            //从中间向左或向右一点点移动
            if (position>-1&&position < 0) {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                float realTransition = transitionY *(-1 * position);
                page.setAlpha(factor);
                page.setTranslationY(realTransition);
            }else if(position<1&&position>0){
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                float realTransition = transitionY * position;
                page.setAlpha(factor);
                page.setTranslationY(realTransition);
            }
        }
    }
}
