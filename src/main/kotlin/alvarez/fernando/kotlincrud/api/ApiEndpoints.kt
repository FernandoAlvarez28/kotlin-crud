package alvarez.fernando.kotlincrud.api

class ApiEndpoints {

    companion object {
        const val ROOT = "/"

        const val HEALTH_CHECK = "/health-check"

        const val API_V1_LOGIN = "/api/v1/login"

        const val API_V1_PRODUCTS = "/api/v1/products"

        const val API_V1_PURCHASES = "/api/v1/purchases"

        const val API_V1_PURCHASES_DETAIL = "/api/v1/purchases/{id}"

        val PUBLIC = arrayOf(ROOT, HEALTH_CHECK)
    }

}