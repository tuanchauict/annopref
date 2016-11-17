# AnnoPref

By using the powerful of annotation processor, we can reduce tons of code on getting and saving Preference.
Everything will come up with simple get/set function.

AnnoPref provides:

* Automatically generate `get/set/has` function for each preference field
* "AntiHack" by encoding the key of each preference field name
* Customizable the name of each field (no effect if AntiHack is on)
* Simple create `static` get/set functions or inside a `singleton` for Dependencies Injection

# Example

```java

@Preference
public class Foo {
    private int baz;
}
```

will be generated to

 ```java
public class FooPref {
    public static int getBaz() {
        ...
    }
    public static int getBaz(int defaultValue) {
        ...
    }
    public static void setBaz(int intPref) {
        ...
    }
    public static boolean hasBaz() {
        ...
    }
}
 ```

# Usage
```groovy
repositories{
    maven {url 'https://dl.bintray.com/tuanchauict/maven/'}
}
```
```groovy
compile 'com.tuanchauict.annopref:annopref:0.5'
apt 'com.tuanchauict.annopref:annopref-compiler:0.5'
```

In Java code, for get/set field Foo.baz in SharePreferences, just call:

```java
FooPref.getBaz(); //or
FooPref.getBaz(defaultValue);
//and
FooPref.setBaz();
FooPref.hasBaz();
```

# Future Work

* More supported type (array, list, set, map)
* Customizable get set function for each field group.
* Default value
