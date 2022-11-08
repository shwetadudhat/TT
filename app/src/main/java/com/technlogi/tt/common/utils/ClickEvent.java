package com.technlogi.tt.common.utils;

import com.technlogi.tt.user.interfaces.OnFragmentChangeLisnter;

public class ClickEvent {

    public static ClickEvent clickEvent;
    private OnFragmentChangeLisnter changeLisnter;

    public static ClickEvent getInstance(){
        if(clickEvent != null) return clickEvent;
        else return clickEvent = new ClickEvent();
    }

    public OnFragmentChangeLisnter getClickEvent() {
        return changeLisnter;
    }

    public void setClickEvent(OnFragmentChangeLisnter changeLisnter) {
        this.changeLisnter = changeLisnter;
    }
}
