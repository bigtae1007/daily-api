package com.tobby.dailyapp.todo.repository

import com.tobby.dailyapp.todo.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long>