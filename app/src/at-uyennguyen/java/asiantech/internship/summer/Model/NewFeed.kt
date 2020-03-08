package asiantech.internship.summer.Model

data class NewFeed(val avatar: Int, val name: String, val picture: Int, var like: Boolean = false, var numberLike: Int, val description: String)
