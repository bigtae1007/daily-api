package com.tobby.dailyapp.todo

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "todo")
class Todo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String,

    @Column(name = "is_done", nullable = false)
    val isDone: Boolean = false,

    @Column(nullable = false)
    val priority: Int = 2,

    @Column(name = "due_date")
    val dueDate: LocalDate? = null,

    @Column(name = "created_at", updatable = false, insertable = false)
    val createdAt: LocalDateTime? = null,

    @Column(name = "updated_at", insertable = false, updatable = false)
    val updatedAt: LocalDateTime? = null
)