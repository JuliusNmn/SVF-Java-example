package org.svf.example.console;

import org.svf.lib.SVFLibrary.*;

import static org.svf.lib.SVFLibrary.llvm_shutdown;

public class Demo {
    public static void main(String[] args) {
        System.out.println("SVF Java Demo...\n");

        // Get SVF Module and build symbols
        var llvm = LLVMModuleSet.getLLVMModuleSet();
        var modules = new StringVector(args);
        var svf = llvm.buildSVFModule(modules);
        svf.buildSymbolTableInfo();

        // Build PAG, Andersen, and get call graph
        var pag = new SVFIRBuilder().build(svf);
        var ander = AndersenWaveDiff.createAndersenWaveDiff(pag);
        var callgraph = ander.getPTACallGraph();

        // Build ICFG
        var icfg = pag.getICFG();
        icfg.dump("icfg");

        // Build VFG
        var vfg = new VFG(callgraph);
        vfg.dump("vfg");

        // Build SVFG
        var svfg = new SVFGBuilder().buildFullSVFG(ander);
        svfg.dump("svfg");

        // Cleanup resources
        AndersenWaveDiff.releaseAndersenWaveDiff();
        SVFIR.releaseSVFIR();
        llvm.dumpModulesToFile(".svf.bc");
        LLVMModuleSet.releaseLLVMModuleSet();
        llvm_shutdown();
    }
}
