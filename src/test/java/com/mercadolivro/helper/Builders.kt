import com.mercadolivro.config.security.Role
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel
import java.math.BigDecimal
import java.util.*

fun buildCustomerModel(
    id: Int? = null,
    name: String = "Customer name",
    email: String = "${UUID.randomUUID()}@email.com.br",
    password: String = "password",
) = CustomerModel(
    id = id,
    name = name,
    email = email,
    roles = setOf(Role.CUSTOMER),
    password = password
)


fun buildPurchaseModel(
    id: Int? = null,
    customerModel: CustomerModel = buildCustomerModel(id = Math.random().toInt()),
    books: MutableList<BookModel> = mutableListOf(),
    nfe: String?,
    price: BigDecimal = BigDecimal.TEN,
) = PurchaseModel(
    id = id,
    customerModel = customerModel,
    books = books,
    nfe = nfe,
    price = price
)



