package com.fireflyingup.deerlet.core.transformer;


import org.apache.commons.lang3.ObjectUtils;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 统一管理transform
 */
public class TransformerManager {

    private final Instrumentation inst;

    private final InformationGenTransformer informationGenTransformer;

    private final List<AbstractTransformer> transformerList = new CopyOnWriteArrayList<>();

    public TransformerManager(Instrumentation inst) {
        this.inst = inst;
        informationGenTransformer = new InformationGenTransformer();
        addTransform(informationGenTransformer);
        reTransformClasses();
    }

    public void addTransform(AbstractTransformer transformer) {
        inst.addTransformer(transformer, true);
        transformerList.add(transformer);
    }

    public void removeTransform(AbstractTransformer transformer) {
        inst.removeTransformer(transformer);
        transformerList.remove(transformer);
    }

    public void uninstall() {
        for (AbstractTransformer transformer : transformerList) {
            inst.removeTransformer(transformer);
        }
        transformerList.clear();
    }

    public void reTransformClasses() {
        Class<?>[] allLoadedClasses = inst.getAllLoadedClasses();
        try {
            if (ObjectUtils.isNotEmpty(allLoadedClasses)) {
                for (Class<?> clazz : allLoadedClasses) {
                    if (inst.isModifiableClass(clazz)) {
                        inst.retransformClasses(clazz);
                    }
                }
            }
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }

}
