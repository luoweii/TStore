/***************************************************************************
 * 
 * Copyright (c) by ihiyo.com, Inc. All Rights Reserved
 * 
 **************************************************************************/
package com.luowei.tstore.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 功能：16:9长方形的ImageView<br>
 * 作者：骆巍<br>
 * 时间：2014-12-24<br>
 */
public class RectangleImageView extends ImageView {
	public RectangleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RectangleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RectangleImageView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		System.out.println(MeasureSpec.getSize(widthMeasureSpec)+" "+MeasureSpec.getSize(heightMeasureSpec));
		//方法一(16:9):根据宽度,直接计算高度的值
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(widthMeasureSpec)*9/16);
		//方法二(16:9):根据宽度,获取高度的heightMeasureSpec,然后调用父类方法
//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec)*9/16, MeasureSpec.getMode(widthMeasureSpec));
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
