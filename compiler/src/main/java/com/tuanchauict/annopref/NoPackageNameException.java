package com.tuanchauict.annopref;

import javax.lang.model.element.TypeElement;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class NoPackageNameException extends Exception {
    public NoPackageNameException(TypeElement typeElement){
        super("The package of " + typeElement.getSimpleName() + " has no name");
    }
}
