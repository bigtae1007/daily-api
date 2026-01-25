package com.tobby.dailyapp

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@MapperScan("com.tobby.dailyapp.**.mapper")
class DailyAppApplication

fun main(args: Array<String>) {
    runApplication<DailyAppApplication>(*args)
}


