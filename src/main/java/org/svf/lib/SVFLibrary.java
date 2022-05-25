package org.svf.lib;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.*;

@Platform(
        include = {
                "SVF-FE/LLVMUtil.h",
                "Graphs/SVFG.h",
                "WPA/Andersen.h",
                "SVF-FE/SVFIRBuilder.h",
                "Util/Options.h"
        },
        link = {
                "svf#",
                "cudd#",
                "LLVMBitWriter#",
                "LLVMCore#",
                "LLVMipo#",
                "LLVMirreader#",
                "LLVMinstcombine#",
                "LLVMinstrumentation#",
                "LLVMtarget#",
                "LLVMlinker#",
                "LLVManalysis#",
                "LLVMscalaropts#",
                "LLVMsupport#",
                "z3",
                "LLVMAsmParser#",
                "LLVMAggressiveInstCombine#",
                "LLVMFrontendOpenMP#",
                "LLVMVectorize#",
                "LLVMTransformUtils#",
                "LLVMObject#",
                "LLVMBitReader#",
                "LLVMMCParser#",
                "LLVMTextAPI#",
                "LLVMProfileData#",
                "LLVMRemarks#",
                "LLVMBitstreamReader#",
                "LLVMMC#",
                "LLVMBinaryFormat#",
                "LLVMDebugInfoCodeView#",
                "LLVMDemangle#",
        }
)
public class SVFLibrary {
    @Namespace("SVF")
    public static class LLVMModuleSet extends Pointer {
        static { Loader.load(); }
        public LLVMModuleSet(Pointer p) { super(p); }
        public static native LLVMModuleSet getLLVMModuleSet();
        public static native void releaseLLVMModuleSet();
        public native SVFModule buildSVFModule(@Const @ByRef StringVector moduleNameVec);
        public native void dumpModulesToFile(@Const @StdString String suffix);
    }

    @Namespace("SVF")
    public static class SVFModule extends Pointer {
        static { Loader.load(); }
        public SVFModule(Pointer p) { super(p); }

        public native @StdString BytePointer getModuleIdentifier();
        public native void buildSymbolTableInfo();
    }

    @Namespace("SVF")
    public static class SVFIRBuilder extends Pointer {
        static { Loader.load(); }
        private SVFIRBuilder(Pointer p) { super(p); }
        public SVFIRBuilder() { super((Pointer)null); allocate(); }
        public native SVFIR build(SVFModule module);
        private native void allocate();
    }

    @Namespace("SVF")
    public static class PointerAnalysis extends Pointer {
        static { Loader.load(); }
        public PointerAnalysis(Pointer p) { super(p); }
        @Const public native ICFG getICFG();
        public native PTACallGraph getPTACallGraph();
        @Const public native SVFIR getPAG();
//        @Const public native PointsTo getPts(@Cast("NodeID") int ptr);
    }

    @Namespace("llvm")
    public static class Value extends Pointer {
        public Value(Pointer p) { super(p); }
    }

    @Namespace("SVF")
    public static class IRGraph extends PointerAnalysis {
        static { Loader.load(); }
        public IRGraph(Pointer p) { super(p); }
        @Cast("SVF::NodeID") public native int getValueNode(@Const Value V);
    }

    @Namespace("SVF")
    public static class SVFIR extends IRGraph {
        static { Loader.load(); }
        public SVFIR(Pointer p) { super(p); }
        public static native void releaseSVFIR();
    }

    @Namespace("SVF")
    public static class AndersenWaveDiff extends Pointer {
        static { Loader.load(); }
        public AndersenWaveDiff(Pointer p) { super(p); }
        public static native Andersen createAndersenWaveDiff(SVFIR svfir);
        public static native void releaseAndersenWaveDiff();
    }

    @Namespace("SVF")
    public static class Andersen extends PointerAnalysis {
        static { Loader.load(); }
        public Andersen(Pointer p) { super(p); }
    }

    @Namespace("SVF")
    public static class PTACallGraph extends Pointer {
        static { Loader.load(); }
        public PTACallGraph(Pointer p) { super(p); }
    }

    @Namespace("SVF")
    public static class ICFG extends Pointer {
        static { Loader.load(); }
        public ICFG(Pointer p) { super(p); }
        public void dump(@Const @StdString String file) { dump(file, false);}
        public native void dump(@Const @StdString String file, boolean simple);
    }

    @Namespace("SVF")
    public static class VFG extends Pointer {
        static { Loader.load(); }
        public VFG(Pointer p) { super(p); }
        public VFG(PTACallGraph callGraph) { super((Pointer)null); allocate(callGraph, VFGK.FULLSVFG); }
        public VFG(PTACallGraph callGraph, VFGK vfgk) { super((Pointer)null); allocate(callGraph, vfgk); }
        public enum VFGK {
            FULLSVFG((byte)(0)),
            PTRONLYSVFG((byte)(1)),
            FULLSVFG_OPT((byte)(2)),
            PTRONLYSVFG_OPT((byte)(3));

            public final byte value;
            private VFGK(byte v) { this.value = v; }
            private VFGK(VFGK e) { this.value = e.value; }
            public VFGK intern() { for (VFGK e : values()) if (e.value == value) return e; return this; }
            @Override public String toString() { return intern().name(); }
        }
        public void dump(@Const @StdString String file) { dump(file, false); }
        public native void dump(@Const @StdString String file, boolean simple);
        private native void allocate(PTACallGraph callGraph, VFGK vfgk);
    }

    @Namespace("SVF")
    public static class SVFGBuilder extends Pointer {
        static { Loader.load(); }
        public SVFGBuilder() { super((Pointer)null); allocate(false); }
        public native SVFG buildFullSVFG(Andersen ander);
        private native void allocate(boolean SVFGWithIndCall);
    }

    @Namespace("SVF")
    public static class SVFG extends Pointer {
        static { Loader.load(); }
        public SVFG(Pointer p) { super(p); }
        public void dump(@Const @StdString String file) { dump(file, false); }
        public native void dump(@Const @StdString String file, boolean simple);
    }

    @Name("std::vector<std::string>")
    public static class StringVector extends Pointer {
        static { Loader.load(); }
        /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
        public StringVector(Pointer p) { super(p); }
        public StringVector(BytePointer value) { this(1); put(0, value); }
        public StringVector(BytePointer ... array) { this(array.length); put(array); }
        public StringVector(String value) { this(1); put(0, value); }
        public StringVector(String ... array) { this(array.length); put(array); }
        public StringVector()       { allocate();  }
        public StringVector(long n) { allocate(n); }
        private native void allocate();
        private native void allocate(@Cast("size_t") long n);
        public native @Name("operator =") @ByRef StringVector put(@ByRef StringVector x);

        public boolean empty() { return size() == 0; }
        public native long size();
        public void clear() { resize(0); }
        public native void resize(@Cast("size_t") long n);

        @Index(function = "at") public native @StdString BytePointer get(@Cast("size_t") long i);
        public native StringVector put(@Cast("size_t") long i, BytePointer value);
        @ValueSetter @Index(function = "at") public native StringVector put(@Cast("size_t") long i, @StdString String value);

        public native @ByVal Iterator insert(@ByVal Iterator pos, @StdString BytePointer value);
        public native @ByVal Iterator erase(@ByVal Iterator pos);
        public native @ByVal Iterator begin();
        public native @ByVal Iterator end();
        @NoOffset @Name("iterator") public static class Iterator extends Pointer {
            public Iterator(Pointer p) { super(p); }
            public Iterator() { }

            public native @Name("operator ++") @ByRef Iterator increment();
            public native @Name("operator ==") boolean equals(@ByRef Iterator it);
            public native @Name("operator *") @StdString BytePointer get();
        }

        public BytePointer[] get() {
            BytePointer[] array = new BytePointer[size() < Integer.MAX_VALUE ? (int)size() : Integer.MAX_VALUE];
            for (int i = 0; i < array.length; i++) {
                array[i] = get(i);
            }
            return array;
        }
        @Override public String toString() {
            return java.util.Arrays.toString(get());
        }

        public BytePointer pop_back() {
            long size = size();
            BytePointer value = get(size - 1);
            resize(size - 1);
            return value;
        }
        public StringVector push_back(BytePointer value) {
            long size = size();
            resize(size + 1);
            return put(size, value);
        }
        public StringVector put(BytePointer value) {
            if (size() != 1) { resize(1); }
            return put(0, value);
        }
        public StringVector put(BytePointer ... array) {
            if (size() != array.length) { resize(array.length); }
            for (int i = 0; i < array.length; i++) {
                put(i, array[i]);
            }
            return this;
        }

        public StringVector push_back(String value) {
            long size = size();
            resize(size + 1);
            return put(size, value);
        }
        public StringVector put(String value) {
            if (size() != 1) { resize(1); }
            return put(0, value);
        }
        public StringVector put(String ... array) {
            if (size() != array.length) { resize(array.length); }
            for (int i = 0; i < array.length; i++) {
                put(i, array[i]);
            }
            return this;
        }
    }

    @Namespace("llvm")
    public static native void llvm_shutdown();
}
