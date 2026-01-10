package com.tobby.dailyapp

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.tobby.dailyapp.**.mapper")
class DailyAppApplication

fun main(args: Array<String>) {
	runApplication<DailyAppApplication>(*args)
}


