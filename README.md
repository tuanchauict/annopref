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

### gradle dependencies


```groovy
compile 'com.tuanchauict.annopref:annopref:1.0.0'
annotationProcessor 'com.tuanchauict.annopref:annopref-compiler:1.0.0'
```

### Init

In Application's `onCreate`:

```java
AnnoPref.init(getSharedPreferences("name", MODE_PRIVATE));
```

### Define Fields

#### Basic:

```java
@Preference
class Foo{
    int foo;
    String baz;
}
```

We will have these fields in the preference file:

```
foo
baz
```

#### Customize

`@Preference` attributes

* `prefix` : the prefix for each field in the class
* `autoPrefix=true`: will use class name (include package name) be the prefix of the field names
* `antiHack=true`: will hash the field's name by using MD5 and Base64
* `type`:
    * `STATIC` : create static methods
    * `SINGLETON` : create concrete methods of object and a `getInstance()` static function

`@Field`

* `value` : custom the name of field (can be shortened by `@Field("a-key-name")`

`@Ignore`

Add this annotation on a field if we don't want this be generated to preference.

//TODO default value

### Get/Set

In Java code, for get/set field Foo.baz in SharePreferences, just call:

```java
FooPref.getBaz(); //or
FooPref.getBaz(defaultValue);
//and
FooPref.setBaz();
FooPref.hasBaz();
```

# Supported types

- boolean
- int
- long
- float
- String
- List&lt;Integer&gt;
- List&lt;Long&gt;
- List&lt;Float&gt;
- List&lt;String&gt;
- Set&lt;Integer&gt;
- Set&lt;Long&gt;
- Set&lt;String&gt;

# Future Work

* More types:
    * Array
    * ~~List~~
    * ~~Set~~
    * Map
    * Object (json)
* ~~Default value~~
* Get rid of `autoPrefix` attribute
