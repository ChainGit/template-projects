package com.chain.project.common.converter;

import com.chain.project.common.utils.JyComUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

public class DatePropertyEditorSupport extends PropertyEditorSupport {

    @Override
    public void setAsText(String s) throws IllegalArgumentException {
        Date date = JyComUtils.parseDateFormString(s);
        this.setValue(date);
    }

}