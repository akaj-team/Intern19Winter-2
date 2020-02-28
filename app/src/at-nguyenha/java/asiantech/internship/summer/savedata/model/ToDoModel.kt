package asiantech.internship.summer.savedata.model

data class ToDoModel(val idToDo: Int?,
                     val accountId: Int,
                     var toDoName: String,
                     var status: Int = 0)
