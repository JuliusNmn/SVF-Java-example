package org.svf.example.console;

import org.svf.lib.SVFLibrary.*;

import static org.svf.lib.SVFLibrary.llvm_shutdown;

public class Demo {
    public static void main(String[] args) {
        System.out.println("SVF Java Demo...\n");

        // Get SVF Module and build symbols
        LLVMModuleSet llvm = LLVMModuleSet.getLLVMModuleSet();
        StringVector modules = new StringVector(args);
        SVFModule svf = llvm.buildSVFModule(modules);
        svf.buildSymbolTableInfo();

        // Build PAG, Andersen, and get call graph
        SVFIR pag = new SVFIRBuilder().build(svf);
        Andersen ander = AndersenWaveDiff.createAndersenWaveDiff(pag);
        PTACallGraph callgraph = ander.getPTACallGraph();

        // Build ICFG
        ICFG icfg = pag.getICFG();
        icfg.dump("icfg");

        // Build VFG
        VFG vfg = new VFG(callgraph);
        vfg.dump("vfg");

        // Build SVFG
        SVFG svfg = new SVFGBuilder().buildFullSVFG(ander);
        svfg.dump("svfg");

        // Cleanup resources
        AndersenWaveDiff.releaseAndersenWaveDiff();
        SVFIR.releaseSVFIR();
        llvm.dumpModulesToFile(".svf.bc");
        LLVMModuleSet.releaseLLVMModuleSet();
        llvm_shutdown();
    }
}
