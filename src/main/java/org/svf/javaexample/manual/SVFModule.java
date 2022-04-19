package org.svf.javaexample.manual;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Namespace;
import org.bytedeco.javacpp.annotation.NoOffset;
import org.bytedeco.javacpp.annotation.StdString;

@Namespace("SVF")
@NoOffset
public class SVFModule extends Pointer {
    static { Loader.load(); }

    public SVFModule(Pointer p) { super(p); }

    public static native void setPagFromTXT(@StdString BytePointer txt);
    public static native void setPagFromTXT(@StdString String txt);

    public native @StdString BytePointer getModuleIdentifier();
}
