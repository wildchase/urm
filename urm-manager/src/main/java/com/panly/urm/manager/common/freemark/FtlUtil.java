package com.panly.urm.manager.common.freemark;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;

/**
 * ftl util
 *
 * @author xuxueli 2018-01-17 20:37:48
 */
public class FtlUtil {

    public static TemplateHashModel generateStaticModel(String packageName) {
        try {
        	BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_27);
        	builder.setUseModelCache(true);
        	builder.setExposeFields(true);
        	BeansWrapper wrapper = builder.build();
            TemplateHashModel staticModels = wrapper.getStaticModels();
            TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName);
            return fileStatics;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
