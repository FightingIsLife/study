package com.jrymos.spring.ioc.refresh;

import com.jrymos.spring.ioc.utils.PrintUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MyInterfaceImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    public MyInterfaceImportBeanDefinitionRegistrar() {
        PrintUtils.errorLog();
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        PrintUtils.errorLog(importingClassMetadata.toString());
        ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry, importBeanNameGenerator);
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(MyInterfaceScan.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(mapperScanAttrs, registry);
        }
    }

    void registerBeanDefinitions(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry) {

        TestClassPathBeanDefinitionScanner scanner = new TestClassPathBeanDefinitionScanner(registry);

        // this check is needed in Spring 3.1
        Optional.ofNullable(resourceLoader).ifPresent(scanner::setResourceLoader);

        List<String> basePackages = new ArrayList<>();

        basePackages.addAll(
                Arrays.stream(annoAttrs.getStringArray("basePackages"))
                        .filter(StringUtils::hasText)
                        .collect(Collectors.toList()));


        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        PrintUtils.errorLog(resourceLoader.toString());
    }


    public static class TestClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

        public TestClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        public void registerFilters() {
            addIncludeFilter(new AnnotationTypeFilter(MyInterface.class));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
        }

        @Override
        protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
            super.registerBeanDefinition(definitionHolder, registry);
            GenericBeanDefinition definition = (GenericBeanDefinition) definitionHolder.getBeanDefinition();
            String beanClassName = definitionHolder.getBeanDefinition().getBeanClassName();
            String beanName = definitionHolder.getBeanName();
            // the interface is the original class of the bean
            // but, the actual class of the bean is MapperFactoryBean
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            definition.setBeanClass(MyInterfaceFactoryBean.class);
            definition.getPropertyValues().add(MyInterfaceFactoryBean.Fields.beanName, beanName);
            PrintUtils.errorLog(beanName);
        }


        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            return super.doScan(basePackages);
        }
    }
}
