package vn.udn.vku.vkufinalproject.onlineshoppingapp.Models

import java.io.Serializable
import java.util.ArrayList

//class HistoryModel (
//    var currentDate: String = "",
//    var currentTime: String = "",
//    var productName: String = "",
//    var productNumber: Int = 0,
//    var productPrice: Int = 0,
//    var totalPrice: Int = 0,
//    var statusCart: String = ""
//) {
//}
class HistoryModel: Serializable {
    var addressDelivery: String = ""
    var timeOrder: String = ""
    var listOrder: ArrayList<HistoryModel01> = ArrayList()
}
class HistoryModel01 (
    var currentDate: String = "",
    var currentTime: String = "",
    var productName: String = "",
    var productNumber: Int = 0,
    var productPrice: Int = 0,
    var totalPrice: Int = 0,
    var statusCart: String = ""
) {
}


