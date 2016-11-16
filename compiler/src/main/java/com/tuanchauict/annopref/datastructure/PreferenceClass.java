package com.tuanchauict.annopref.datastructure;

import com.tuanchauict.annopref.EnumType;
import com.tuanchauict.annopref.NoPackageNameException;
import com.tuanchauict.annopref.Preference;
import com.tuanchauict.annopref.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class PreferenceClass {
    private String mPrefix;
    private boolean mAntiHack;
    private EnumType mType;
    private TypeElement mTypeElement;

    private final List<PreferenceField> mFields = new ArrayList<>();

    public PreferenceClass(TypeElement typeElement) {
        mTypeElement = typeElement;
        Preference preference = typeElement.getAnnotation(Preference.class);
        mPrefix = preference.prefix();
        mAntiHack = preference.antiHack();
        mType = preference.type();
    }

    public String getPackage(Elements elementUtils) throws NoPackageNameException {
        return Utils.getPackageName(elementUtils, mTypeElement);
    }

    public String getPrefix(){
        return Utils.isEmpty(mPrefix) ? mTypeElement.getQualifiedName().toString() : mPrefix;
    }

    public List<PreferenceField> getFields(){
        return mFields;
    }

    public void addField(PreferenceField field){
        mFields.add(field);
    }

    public TypeElement getTypeElement(){
        return mTypeElement;
    }

    public String getClassName(){
        return mTypeElement.getSimpleName().toString();
    }

    public boolean isSingleton(){
        return mType == EnumType.SINGLETON;
    }

    public boolean isAntiHack() {
        return mAntiHack;
    }
}
