package otus.homework.coroutines.data

import com.google.gson.annotations.SerializedName

data class Image(
    @field:SerializedName("id")
    var id: String = "",
    @field:SerializedName("url")
    var url: String = "",
    @field:SerializedName("width")
    var width: Int = 0,
    @field:SerializedName("height")
    var height: Int = 0
)