package com.fireflyingup.deerlet.core.transformer;


import com.fireflyingup.deerlet.core.info.ClassInfo;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("load class size:" + InformationGenTransformer.relationMap.size());
            Set<Map.Entry<Class<?>, ClassInfo>> entries = InformationGenTransformer.relationMap.entrySet();
            for (Map.Entry<Class<?>, ClassInfo> entry : entries) {
                System.out.println(entry.getValue());
            }
        });
        t.start();
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
