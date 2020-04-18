package com.lianxi.dingtu.dingtu_urovo.mvp.ui.widget;


public class HelperManager {

	private HelperManager(){
		
	}

	public static AnimationsHelper getAnimationsHelper(){
		return AnimationsHelper.getSington();
	}

}