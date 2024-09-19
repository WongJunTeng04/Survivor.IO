import scalafx.collections.ObservableBuffer


/***************************************************************************************
 *    Title: AddressApp (Database)
 *    Author: Dr. Chin Teck Min (Lecturer)
 *    Date: 21/08/2024
 *    Code version: SCALA 2.12.19/ ScalaFX8
 *    Availability: Tutorial from Uni work
 *
 ***************************************************************************************/

// Common Util used for database
object CommonUtil {
  def listToObservableBuffer[A](list: List[A]): ObservableBuffer[A] = {
    val observableBuffer = new ObservableBuffer[A]()
    list.foreach(item => observableBuffer.add(item))
    observableBuffer
  }
}