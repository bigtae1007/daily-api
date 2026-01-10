package com.tobby.dailyapp.common.config

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

@Configuration
class MybatisConfig(
    private val dataSource: DataSource // 스프링이 생성한 데이터소스를 주입받음
) {
    @Bean
    fun sqlSessionFactory(): SqlSessionFactory {
        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(dataSource)
        // 만약 XML 매퍼가 있다면 아래 주석 해제
         val resolver = PathMatchingResourcePatternResolver()
         sessionFactory.setMapperLocations(*resolver.getResources("classpath:mybatis/*.xml"))
        return sessionFactory.`object`!!
    }

    @Bean
    fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }
}