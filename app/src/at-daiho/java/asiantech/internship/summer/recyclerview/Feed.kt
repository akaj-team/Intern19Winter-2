package asiantech.internship.summer.recyclerview

data class Feed(
        var name: String,
        var pictureIndex: Int,
        var isLike: Boolean,
        var noOfLikes: Int,
        var commentOwner: String,
        var comment: String
)
