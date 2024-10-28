

object OrderUtils {

  def calculateTotal(order: Order): Double = {
    order.items.map(o => o.price).sum;
  }

  def applyDiscount(order: Order, discountRate: Double): Order = {

    def discountedItems: List[Item] = order.items.map(i => i.copy(price = i.price * (1 - discountRate)));

    order.copy(items = discountedItems)
  }

  def processOrders(orders: List[Order], discounts: Map[String, Double]): List[Double] = {
    orders.map(order => {
      val discountRate: Double = order.discountCode.flatMap(discounts.get).getOrElse(0.0)
      val discountedOrder = applyDiscount(order, discountRate)

      calculateTotal(discountedOrder)
    })
  }
}
