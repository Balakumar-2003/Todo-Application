package com.balakumar.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity
data class TaskDetails(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    var status:Int=0,
    var taskName:String,
    var label:String,
    var description:String,
    var createdDate:Date,
    var startDate: Date,
    var endDate: Date
)
