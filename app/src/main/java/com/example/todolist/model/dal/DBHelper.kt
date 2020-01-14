package com.example.todolist.model.dal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolist.model.*

class DBHelper(context: Context?): SQLiteOpenHelper(context, NAME, null, VERSION) {
    companion object DATABASE {
        const val NAME: String = "tasks"
        const val VERSION: Int = 1


        object TABLES {
            const val TASKS: String = "tasks"
            const val USERS: String = "users"
            const val USERS_LOGINS: String = "users_logins"
        }

        object COLUMNS {
            const val ID: String = "id"
            const val TITLE: String = "title"
            const val DESCRIPTION: String = "description"
            const val PRIORITY: String = "priority"
            const val STATUS: String = "status"
            const val CREATION_DATE: String = "creation_date"
            const val EXPIRATION_DATE: String = "expiration_date"
            const val AUTHOR_ID: String = "author_id"
            const val RECEIVER_ID: String = "receiver_id"
        }

        object COMMANDS {
            const val CREATE_TABLE_TASKS: String =
                "CREATE TABLE IF NOT EXISTS ${TABLES.TASKS} ( " +
                "${COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${COLUMNS.TITLE} VARCHAR NOT NULL," +
                "${COLUMNS.DESCRIPTION} VARCHAR NOT NULL," +
                "${COLUMNS.PRIORITY} VARCHAR NOT NULL DEFAULT 'Niski'," +
                "${COLUMNS.STATUS} VARCHAR NOT NULL DEFAULT 'Nowe'," +
                "${COLUMNS.CREATION_DATE} VARCHAR NOT NULL," +
                "${COLUMNS.EXPIRATION_DATE} VARCHAR NOT NULL," +
                "${COLUMNS.AUTHOR_ID} INTEGER NOT NULL," +
                "${COLUMNS.RECEIVER_ID} INTEGER DEFAULT NULL);"
            const val DROP_DATABASE_TASKS: String = "DROP TABLE IF EXISTS ${TABLES.TASKS};"
            const val SELECT_ALL_TASKS_ORDER_BY_STATUS =
                "SELECT * FROM ${TABLES.TASKS} " +
                "ORDER BY ${COLUMNS.STATUS} ASC, ${COLUMNS.EXPIRATION_DATE} DESC;"
        }

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(COMMANDS.CREATE_TABLE_TASKS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(COMMANDS.DROP_DATABASE_TASKS)
        onCreate(db)
    }

    fun addTask(task: Task) {
        val db = this.writableDatabase
        val newTask = ContentValues().apply {
            put(COLUMNS.ID, task.id)
            put(COLUMNS.TITLE, task.title)
            put(COLUMNS.DESCRIPTION, task.description)
            put(COLUMNS.PRIORITY, task.priority.value)
            put(COLUMNS.STATUS, task.status.value)
            put(COLUMNS.CREATION_DATE, task.creationDate)
            put(COLUMNS.EXPIRATION_DATE, task.expirationDate)
            put(COLUMNS.AUTHOR_ID, task.authorId)
            put(COLUMNS.RECEIVER_ID, task.receiverId)
        }

        db.insert(TABLES.TASKS, null, newTask)
    }

    fun updateTask(task: Task) {
        val db = this.writableDatabase
        val newTask = ContentValues().apply {
            put(COLUMNS.ID, task.id)
            put(COLUMNS.TITLE, task.title)
            put(COLUMNS.DESCRIPTION, task.description)
            put(COLUMNS.PRIORITY, task.priority.value)
            put(COLUMNS.STATUS, task.status.value)
            put(COLUMNS.CREATION_DATE, task.creationDate)
            put(COLUMNS.EXPIRATION_DATE, task.expirationDate)
            put(COLUMNS.AUTHOR_ID, task.authorId)
            put(COLUMNS.RECEIVER_ID, task.receiverId)
        }

        db.update(TABLES.TASKS, newTask, "${COLUMNS.ID} = ${task.id}", null)
    }

    fun deleteTask(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLES.TASKS, "${COLUMNS.ID} = $id", null)
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

    fun getAllTasksOrderByStatus() : MutableList<Task> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(COMMANDS.SELECT_ALL_TASKS_ORDER_BY_STATUS, null)
        val tasks = mutableListOf<Task>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMNS.ID))
            val title = cursor.getString(cursor.getColumnIndex(COLUMNS.TITLE))
            val description = cursor.getString(cursor.getColumnIndex(COLUMNS.DESCRIPTION))
            val creationDate = cursor.getString(cursor.getColumnIndex(COLUMNS.CREATION_DATE))
            val expirationDate = cursor.getString(cursor.getColumnIndex(COLUMNS.EXPIRATION_DATE))
            val authorId = cursor.getInt((cursor.getColumnIndex(COLUMNS.AUTHOR_ID)))
            val receiverId = cursor.getInt((cursor.getColumnIndex(COLUMNS.RECEIVER_ID)))

            val statusRaw = cursor.getString(cursor.getColumnIndex(COLUMNS.STATUS))
            val status = parseStatus(statusRaw)

            val priotityRaw = cursor.getString(cursor.getColumnIndex(COLUMNS.PRIORITY))
            val priority = parsePriority(priotityRaw)

            tasks.add(Task(id, title, status, priority, description, creationDate, expirationDate, authorId, receiverId))
        }
        cursor.close()

        return tasks
    }
}