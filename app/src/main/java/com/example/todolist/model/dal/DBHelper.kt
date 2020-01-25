package com.example.todolist.model.dal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.todolist.model.*
import kotlinx.coroutines.processNextEventInCurrentThread

class DBHelper(context: Context?): SQLiteOpenHelper(context, NAME, null, VERSION) {
    companion object DATABASE {
        const val NAME = "tasks"
        const val VERSION = 2

        object TABLES {
            const val TASKS = "tasks"
            const val USERS = "users"
            const val USERS_LOGINS = "users_logins"
            const val LOGGED_USER = "logged_user"
        }

        object TASKS_COLUMNS {
            const val ID = "id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val PRIORITY = "priority"
            const val STATUS = "status"
            const val CREATION_DATE = "creation_date"
            const val EXPIRATION_DATE = "expiration_date"
            const val AUTHOR_ID = "author_id"
            const val RECEIVER_ID = "receiver_id"
        }

        object LOGGED_USER_COLUMNS {
            const val LOGIN = "login"
        }

        object QUERIES {
            const val CREATE_TABLE_TASKS =
                "CREATE TABLE IF NOT EXISTS ${TABLES.TASKS} ( " +
                "${TASKS_COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${TASKS_COLUMNS.TITLE} VARCHAR NOT NULL," +
                "${TASKS_COLUMNS.DESCRIPTION} VARCHAR NOT NULL," +
                "${TASKS_COLUMNS.PRIORITY} VARCHAR NOT NULL DEFAULT 'Niski'," +
                "${TASKS_COLUMNS.STATUS} VARCHAR NOT NULL DEFAULT 'Nowe'," +
                "${TASKS_COLUMNS.CREATION_DATE} VARCHAR NOT NULL," +
                "${TASKS_COLUMNS.EXPIRATION_DATE} VARCHAR NOT NULL," +
                "${TASKS_COLUMNS.AUTHOR_ID} INTEGER NOT NULL," +
                "${TASKS_COLUMNS.RECEIVER_ID} INTEGER DEFAULT NULL);"

            const val CREATE_TABLE_LOGGED_USER =
                "CREATE TABLE IF NOT EXISTS ${TABLES.LOGGED_USER} (" +
                "${LOGGED_USER_COLUMNS.LOGIN} VARCHAR NOT NULL);"

            const val SELECT_ALL_TASKS_ORDER_BY_STATUS =
                "SELECT * FROM ${TABLES.TASKS} " +
                "ORDER BY ${TASKS_COLUMNS.STATUS} ASC, ${TASKS_COLUMNS.EXPIRATION_DATE} DESC;"

            const val SELECT_LOGGED_USER = "SELECT ALL FROM ${TABLES.LOGGED_USER};"

            const val DROP_DATABASE_TASKS = "DROP TABLE IF EXISTS ${TABLES.TASKS};"
            const val DROP_DATABASE_LOGGED_USER = "DROP TABLE IF EXISTS ${TABLES.LOGGED_USER};"
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(QUERIES.CREATE_TABLE_TASKS)
        db?.execSQL(QUERIES.CREATE_TABLE_LOGGED_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(QUERIES.DROP_DATABASE_TASKS)
        db?.execSQL(QUERIES.DROP_DATABASE_LOGGED_USER)
        onCreate(db)
    }


    //#region Task management
    fun addTask(task: Task) {
        val db = this.writableDatabase
        val newTask = ContentValues().apply {
            put(TASKS_COLUMNS.ID, task.id)
            put(TASKS_COLUMNS.TITLE, task.title)
            put(TASKS_COLUMNS.DESCRIPTION, task.description)
            put(TASKS_COLUMNS.PRIORITY, task.priority.value)
            put(TASKS_COLUMNS.STATUS, task.status.value)
            put(TASKS_COLUMNS.CREATION_DATE, task.creationDate)
            put(TASKS_COLUMNS.EXPIRATION_DATE, task.expirationDate)
            put(TASKS_COLUMNS.AUTHOR_ID, task.author.login)
            put(TASKS_COLUMNS.RECEIVER_ID, task.receiver?.login)
        }

        db.insert(TABLES.TASKS, null, newTask)

        Log.i("schaboszczak task", newTask.toString())
    }

    fun getAllTasksOrderByStatus() : MutableList<Task> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(QUERIES.SELECT_ALL_TASKS_ORDER_BY_STATUS, null)
        val tasks = mutableListOf<Task>()

        while (cursor.moveToNext()) {
//            val id = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMNS.ID))
//            val title = cursor.getString(cursor.getColumnIndex(TASKS_COLUMNS.TITLE))
//            val description = cursor.getString(cursor.getColumnIndex(TASKS_COLUMNS.DESCRIPTION))
//            val creationDate = cursor.getString(cursor.getColumnIndex(TASKS_COLUMNS.CREATION_DATE))
//            val expirationDate = cursor.getString(cursor.getColumnIndex(TASKS_COLUMNS.EXPIRATION_DATE))
//            val authorLogin = cursor.getString((cursor.getColumnIndex(TASKS_COLUMNS.AUTHOR_ID)))
//            val receiverLogin = cursor.getString((cursor.getColumnIndex(TASKS_COLUMNS.RECEIVER_ID)))
//
//            val statusRaw = cursor.getString(cursor.getColumnIndex(TASKS_COLUMNS.STATUS))
//            val status = parseStatus(statusRaw)
//
//            val priorityRaw = cursor.getString(cursor.getColumnIndex(TASKS_COLUMNS.PRIORITY))
//            val priority = parsePriority(priorityRaw)

//            tasks.add(Task(id, title, status, priority, description, creationDate, expirationDate, authorLogin, receiverLogin))
        }
        cursor.close()

        Log.i("schaboszczak lista", tasks.size.toString())

        return tasks
    }

    fun updateTask(task: Task) {
        val db = this.writableDatabase
        val newTask = ContentValues().apply {
            put(TASKS_COLUMNS.ID, task.id)
            put(TASKS_COLUMNS.TITLE, task.title)
            put(TASKS_COLUMNS.DESCRIPTION, task.description)
            put(TASKS_COLUMNS.PRIORITY, task.priority.value)
            put(TASKS_COLUMNS.STATUS, task.status.value)
            put(TASKS_COLUMNS.CREATION_DATE, task.creationDate)
            put(TASKS_COLUMNS.EXPIRATION_DATE, task.expirationDate)
            put(TASKS_COLUMNS.AUTHOR_ID, task.author.login)
            put(TASKS_COLUMNS.RECEIVER_ID, task.receiver?.login)
        }

        db.update(TABLES.TASKS, newTask, "${TASKS_COLUMNS.ID} = ${task.id}", null)
    }

    fun deleteTask(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLES.TASKS, "${TASKS_COLUMNS.ID} = $id", null)
    }

//
//    fun dropTableTasks() {
//        val db = this.writableDatabase
//        db.delete(TABLES.TASKS, "id > 0", null)
//    }
//
//    fun dropDatabase() {
//        Log.i("dropping", "drop database")
//        val db = this.writableDatabase
//        db?.execSQL(COMMANDS.DROP_DATABASE_TASKS)
//    }

    //#endregion

    //#region Logged user management
    fun addLoggedUser(login: String) {
        val db = this.writableDatabase
        val newTaskQuery = ContentValues().apply{
            put(LOGGED_USER_COLUMNS.LOGIN, login)
        }
        db.insert(TABLES.LOGGED_USER, null, newTaskQuery)
    }

    fun getLoggedUser(): String {
        val db = this.readableDatabase
        var loginUser = ""
        val cursor = db.rawQuery(QUERIES.SELECT_LOGGED_USER, null)

        while (cursor.moveToNext()) {
            loginUser = cursor.getString(cursor.getColumnIndex(LOGGED_USER_COLUMNS.LOGIN))
        }
        return loginUser
    }

    fun deleteLoggedUser() {
        val db = this.writableDatabase
        db.delete(TABLES.LOGGED_USER, "*", null)
    }
    //#endregion
}