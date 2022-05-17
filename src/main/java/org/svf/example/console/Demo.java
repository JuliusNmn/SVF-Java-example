package org.svf.example.console;

import org.svf.lib.SVFLibrary.*;

public class Demo {
    public static void main(String[] args) {
        System.out.println("SVF Java Demo...");
        var llvm = LLVMModuleSet.getLLVMModuleSet();
        var modules = new StringVector(args);
        var svf = llvm.buildSVFModule(modules);
        System.out.println("SVF MODULE: " + svf.getModuleIdentifier().getString());
        LLVMModuleSet.releaseLLVMModuleSet();
    }
}
