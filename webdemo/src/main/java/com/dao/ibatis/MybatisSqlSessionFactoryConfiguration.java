package com.dao.ibatis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Configuration
@Slf4j
public class MybatisSqlSessionFactoryConfiguration {

    @Autowired
    private DataSource dataSource;

    private static Objects BLOCK;

    //枚举处理
    private Set<Class<? extends EnumType>> sets;
    private static String ENUMPAC = "com.enums";

    @Bean
    @ConditionalOnMissingBean(SqlSessionFactory.class)
    public SqlSessionFactory sqlSessionFactory() {
        try {
            String mappers = "classpath*:com/dao/**/master/**/*Mapper*.xml";
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            List<Resource> mapperLocations = new ArrayList<>();

            String[] mapperArr = mappers.split(",");
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            for (String m : mapperArr) {
                try {
                    Resource[] resources = resolver.getResources(m);
                    mapperLocations.addAll(Arrays.asList(resources));
                } catch (IOException e) {
                    throw new RuntimeException("resolver resources error." + e);
                }
            }
            //mapper
            sqlSessionFactoryBean.setMapperLocations(mapperLocations.toArray(new Resource[]{}));
            //register handler
            List<TypeHandler<?>> typeHandlers = new ArrayList<>();
            //build configuration
            sqlSessionFactoryBean.setConfiguration(buildCOnfiguration());
            //typeHandler
            //plugins
            List<Interceptor> interceptors = new ArrayList<>();
            interceptors.add(pageIntercepter());
            interceptors.add(buondValueInterceptor());
            interceptors.add(mybatisLogInterceptor());
            //还要加载配置的plugins

            sqlSessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[]{}));

            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("init sqlSessionFactory error", e);
        }


    }

    private Interceptor mybatisLogInterceptor() {
        return new MybatisLogInterceptor();
    }

    private Interceptor buondValueInterceptor() {
        return new BoundDefaultValueInterceptor();
    }

    private Interceptor pageIntercepter() {
        PageInterceptor interceptor = new PageInterceptor();
        return interceptor;
    }

    /**
     * mybatis configuration
     *
     * @return
     */
    private org.apache.ibatis.session.Configuration buildCOnfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();

        synchronized (BLOCK) {
            if (CollectionUtils.isEmpty(sets)) {
                Reflections ref = new Reflections(ENUMPAC);
                sets = ref.getSubTypesOf(EnumType.class);
            }
        }
        for (Class<? extends EnumType> clz : sets) {
            Class<Enum> enumClz = (Class<Enum>) ReflectionUtils.forName(clz.getName(), null);
            configuration.getTypeHandlerRegistry().register(enumClz, new AplusEnumTypeHandler<>(enumClz));
        }

//        configuration.setCacheEnabled(true);
        configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        configuration.getTypeHandlerRegistry().register(JSONObject.class, JdbcType.VARCHAR, MysqlJsonHandler.class);
        configuration.getTypeHandlerRegistry().register(JSONArray.class, JdbcType.VARCHAR, MysqlJsonHandler.class);
        return configuration;
    }

}
