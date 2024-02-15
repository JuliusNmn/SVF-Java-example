package org.svf.example.console;

import org.svf.lib.SVFLibrary.*;

import java.io.File;

import static org.svf.lib.SVFLibrary.llvm_shutdown;

public class Demo {
    /**
     * Run the demo SVF Java example console application
     * @param args module names (path to modules) to load for the example
     */
    public static void main(String[] args) {
        System.out.println("SVF Java Demo...\n");

        // Create the graphs output directory
        new File("graphs").mkdir();

        ExtAPI.setExtBcPath("/usr/local/lib/node_modules/SVF/Release-build/lib/extapi.bc");

        // Get SVF Module and build symbols
        LLVMModuleSet llvm = LLVMModuleSet.getLLVMModuleSet();
        SVFModule svf = llvm.buildSVFModule(args);
        //svf.buildSymbolTableInfo();

        // Build PAG, Andersen, and get call graph
        SVFIR pag = new SVFIRBuilder(svf).build();
        Andersen ander = AndersenWaveDiff.createAndersenWaveDiff(pag);
        PTACallGraph callgraph = ander.getPTACallGraph();

        // Build ICFG
        ICFG icfg = pag.getICFG();
        icfg.dump("graphs/icfg");

        // Build VFG
        VFG vfg = new VFG(callgraph);
        vfg.dump("graphs/vfg");

        // Build SVFG
        SVFG svfg = new SVFGBuilder().buildFullSVFG(ander);
        svfg.dump("graphs/svfg");

        // Cleanup resources
        AndersenWaveDiff.releaseAndersenWaveDiff();
        SVFIR.releaseSVFIR();
        llvm.dumpModulesToFile(".svf.bc");
        LLVMModuleSet.releaseLLVMModuleSet();
        llvm_shutdown();
    }
}
