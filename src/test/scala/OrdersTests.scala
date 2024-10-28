
import org.scalatest.funsuite.AnyFunSuite



class OrdersTests extends AnyFunSuite {

  test("calculateTotal doit retourner le total correct pour une commande") {
    val items = List(Item("1", "item1", 10.0), Item("2", "item2", 20.0))
    val order = Order("order1", items, None)
    assert(OrderUtils.calculateTotal(order) == 30.0)
  }

  test("applyDiscount doit appliquer correctement le taux de réduction") {
    val items = List(Item("1", "item1", 10.0), Item("2", "item2", 20.0))
    val order = Order("order1", items, None)
    val discountedOrder = OrderUtils.applyDiscount(order, 0.1)

    assert(discountedOrder.items.head.price == 9.0) // 10.0 * 0.9
    assert(discountedOrder.items(1).price == 18.0) // 20.0 * 0.9
  }

  test("processOrders doit appliquer la réduction correcte depuis la map de réductions") {
    val items1 = List(Item("1", "item1", 50.0))
    val items2 = List(Item("2", "item2", 100.0))
    val order1 = Order("order1", items1, Some("DISCOUNT10"))
    val order2 = Order("order2", items2, Some("DISCOUNT20"))
    val orders = List(order1, order2)
    val discounts = Map("DISCOUNT10" -> 0.1, "DISCOUNT20" -> 0.2)

    val totals = OrderUtils.processOrders(orders, discounts)

    assert(totals.head == 45.0) // 50.0 with 10% discount
    assert(totals(1) == 80.0) // 100.0 with 20% discount
  }

  test("processOrders ne doit pas appliquer de réduction si le code de réduction est invalide") {
    val items = List(Item("1", "item1", 30.0), Item("2", "item2", 70.0))
    val order = Order("order1", items, Some("INVALIDE"))
    val orders = List(order)
    val discounts = Map("DISCOUNT10" -> 0.1)

    val totals = OrderUtils.processOrders(orders, discounts)

    assert(totals.head == 100.0) // No discount applied
  }
}
