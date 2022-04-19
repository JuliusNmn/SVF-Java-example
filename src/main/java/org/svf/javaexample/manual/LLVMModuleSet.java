package org.svf.javaexample.manual;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;

@Namespace("SVF")
@NoOffset
public class LLVMModuleSet extends Pointer {
    static { Loader.load(); }

    public LLVMModuleSet(Pointer p) { super(p); }

    public static native LLVMModuleSet getLLVMModuleSet();

    public static native void releaseLLVMModuleSet();

    public native SVFModule buildSVFModule(@StdString @StdVector BytePointer moduleNameVec);

    public native SVFModule getSVFModule();

    public native void preProcessBCs(@StdString @StdVector BytePointer moduleNameVec);
}
