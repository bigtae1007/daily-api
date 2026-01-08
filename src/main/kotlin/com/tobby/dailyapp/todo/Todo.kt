package com.tobby.dailyapp.todo

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "todo")
class Todo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(name = "is_done", nullable = false)
    var isDone: Boolean = false,

    @Column(nullable = false)
    var priority: Int = 1,

    @Column(name = "due_date")
    val dueDate: LocalDate? = null,

    @Column(name = "created_at", updatable = false, insertable = false)
    val createdAt: LocalDateTime? = null,

    @Column(name = "updated_at", insertable = false, updatable = false)
    val updatedAt: LocalDateTime? = null
){
    fun update( title: String?, priority: Int?, isDone: Boolean?) {
        title?.let { this.title = it }
        priority?.let { this.priority = it }
        isDone?.let { this.isDone = it }
    }
}