package com.study.Spring.ioc;

import com.study.Spring.AopBeanPostProcesor;
import com.study.Spring.aop.gpaop.GPAopProxy;
import com.study.Spring.aop.gpaop.GPJdkDynamicAopProxy;
import com.study.Spring.aop.config.GPAopConfig;
import com.study.Spring.aop.gpaop.support.GPAdvisedSupport;
import com.study.Spring.aop.myaop.MyAdvisedSupport;
import com.study.Spring.aop.myaop.MyJdkDynamicAopProxy;
import com.study.Spring.aop.myaop2.MyJdkDynamicAopProxy2;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ApplicaionContext {
    String location;
    MyBeanDefinitionReader reader;
    HashMap<String, MyBeanDefinition> beanDefinitionHashMap = new HashMap<>();


    /**
     * Cache of singleton objects: bean name --> bean instance 一级缓存
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);

    /**
     * Cache of singleton factories: bean name --> ObjectFactory  三级缓存
     */
    private final Map<String, Object> singletonFactories = new HashMap<String, Object>(16);

    /**
     * Cache of early singleton objects: bean name --> bean instance 二级缓存
     * 只用一级和三级缓存似乎就能解决循环依赖问题，但是在aop下，三级缓存每次会返回新的对象，所以需要二级缓存
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

    public ApplicaionContext(String s) {
        // 注册成beanDefinition
        // 实例化bean到容器
        this.location = s;
        refresh();
    }

    private void refresh() {

        reader = new MyBeanDefinitionReader(this.location);
        // 注册成beanDefinition
        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        for (MyBeanDefinition beanDefinition : beanDefinitions) {
            beanDefinitionHashMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //4、把不是延时加载的类，有提前初始化
        initBean();
    }

    private void initBean() {
        for (Map.Entry<String, MyBeanDefinition> stringMyBeanDefinitionEntry : beanDefinitionHashMap.entrySet()) {
            getBean(stringMyBeanDefinitionEntry.getValue());
        }
    }

    private Object getBean(MyBeanDefinition beanDefinition) {

        //这个逻辑还不严谨，自己可以去参考Spring源码
        //工厂模式 + 策略模式
        // GPBeanPostProcessor postProcessor = new GPBeanPostProcessor();

        //   postProcessor.postProcessBeforeInitialization(instance,beanName);
        if (singletonObjects.containsKey(beanDefinition.getFactoryBeanName())) {
            return singletonObjects.get(beanDefinition.getFactoryBeanName());
        }
        Object instance = instantiateBean(beanDefinition);

        //3、把这个对象封装到BeanWrapper中


        //4、把BeanWrapper存到IOC容器里面

        //2、拿到BeanWraoper之后，把BeanWrapper保存到IOC容器中去

        //  postProcessor.postProcessAfterInitialization(instance,beanName);

//        //3、注入
        populateBean(instance);

        // 如果匹配判定需要切面 则创建代理对象
        instance = initialBean(instance);

        singletonObjects.put(beanDefinition.getFactoryBeanName(), instance);
        return instance;
    }

    private Object initialBean(Object instance) {
        // 先执行各种BeanPostProcesorBeforeInitial  比如设置applicationcontextaware
        // 然后执行init方法
        // 先执行各种BeanPostProcesorAfterInitial  比如AopBeanPostProcesor
        AopBeanPostProcesor beanPostProcesor = new AopBeanPostProcesor();
        beanPostProcesor.setContext(this);
        return beanPostProcesor.postAfterInitial(instance);
    }


    public <T> T getBean(Class<T> personClass) {
        return (T) getBean(personClass.getSimpleName());
    }

    public Object getBean(String beanName) {
        return getBean(beanDefinitionHashMap.get(beanName));
    }

    private void populateBean(Object instance) {

        Class<?> clazz = instance.getClass();

        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(GPAutowired.class)) {
                continue;
            }

            GPAutowired autowired = field.getAnnotation(GPAutowired.class);

            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getSimpleName();
            }

            //强制访问
            field.setAccessible(true);

            try {
                Object singleton = getSingleton(autowiredBeanName);
                if (singleton == null) {
                    singleton = getBean(autowiredBeanName);
                }
                field.set(instance, singleton);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    protected Object getSingleton(String beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null) {
            synchronized (this.singletonObjects) {
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (singletonObject == null) {
                    singletonObject = this.singletonFactories.get(beanName);
                    if (singletonObject != null) {
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    private Object instantiateBean(MyBeanDefinition gpBeanDefinition) {
        //1、拿到要实例化的对象的类名
        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
//            gpBeanDefinition.getFactoryBeanName()
            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if (this.singletonObjects.containsKey(gpBeanDefinition.getFactoryBeanName())) {
                instance = this.singletonObjects.get(gpBeanDefinition.getFactoryBeanName());
            } else {
                instance = Class.forName(gpBeanDefinition.getBeanClassName()).newInstance();
                this.singletonFactories.put(gpBeanDefinition.getFactoryBeanName(), instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    private GPAopProxy createProxy(GPAdvisedSupport config) {

        Class targetClass = config.getTargetClass();
//        if(targetClass.getInterfaces().length > 0){
//            return new GPJdkDynamicAopProxy(config);
//        }
//        return new GPCglibAopProxy(config);

        return new GPJdkDynamicAopProxy(config);
    }

    private GPAdvisedSupport instantionAopConfig() {
        GPAopConfig config = new GPAopConfig();
        config.setPointCut(this.reader.getConfig().get("pointCut"));
        config.setAspectClass(this.reader.getConfig().get("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().get("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().get("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().get("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().get("aspectAfterThrowingName"));
        return new GPAdvisedSupport(config);
    }
}
