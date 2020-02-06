package asiantech.internship.summer.recyclerview

data class Food(val avatar: Int, val name: String, val picture: Int, var like: Boolean = false, var numberLike: Int, val description: String) {
}