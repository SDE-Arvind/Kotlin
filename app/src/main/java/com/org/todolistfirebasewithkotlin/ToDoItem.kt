package com.org.todolistfirebasewithkotlin

/**
 * Created by arvind on 2019-12-18
 */
data class ToDoItem (var id:String="" , var level:String="", var status:Boolean=false){

    public fun statusChanged(){
        status=!status
    }
}