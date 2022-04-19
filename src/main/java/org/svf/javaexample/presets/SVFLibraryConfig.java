package org.svf.javaexample.presets;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

@Properties(
        target = "org.svf.javaexample.generated.SVFLibrary",
        value = @Platform(
                includepath = {
                        "~/node_modules/llvm-13.0.0.obj/include",
                        "~/node_modules/SVF/include"
                },
                linkpath = {"~/node_modules/SVF/Release-build/lib/"},
                include = {
                        "SVF-FE/LLVMModule.h",
                        "Util/SVFModule.h",
                },
                preload = { "libSvf" },
                compiler = { "cpp14" }
        )
)
public class SVFLibraryConfig implements InfoMapper {
    @Override
    public void map(InfoMap infoMap) {
    }
}
