package com.shri.doordashlite.restaurants.model

data class RestaurantDTO(
    val is_first_time_user: Boolean,
    val next_offset: Int,
    val num_results: Int,
    val show_list_as_pickup: Boolean,
    val sort_order: String,
    val stores: List<Store>
)

data class Store(
    val average_rating: Double,
    val business_id: Int,
    val cover_img_url: String? = null,
    val delivery_fee: Int,
    val delivery_fee_monetary_fields: DeliveryFeeMonetaryFields,
    val description: String,
    val display_delivery_fee: String,
    val distance_from_consumer: Double,
    val extra_sos_delivery_fee: Int,
    val extra_sos_delivery_fee_monetary_fields: ExtraSosDeliveryFeeMonetaryFields,
    val header_img_url: String? = null,
    val id: Int,
    val is_consumer_subscription_eligible: Boolean,
    val is_newly_added: Boolean,
    val location: Location,
    val menus: List<Menu>,
    val merchant_promotions: List<MerchantPromotion>,
    val name: String,
    val next_close_time: String,
    val next_open_time: String,
    val num_ratings: Int,
    val price_range: Int,
    val promotion_delivery_fee: Int,
    val service_rate: Any,
    val status: Status,
    val url: String
)

data class DeliveryFeeMonetaryFields(
    val currency: String,
    val decimal_places: Int,
    val display_string: String,
    val unit_amount: Int
)

data class ExtraSosDeliveryFeeMonetaryFields(
    val currency: String,
    val decimal_places: Int,
    val display_string: String,
    val unit_amount: Int
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Menu(
    val id: Int,
    val is_catering: Boolean,
    val name: String,
    val popular_items: List<PopularItem>,
    val subtitle: String
)

data class MerchantPromotion(
    val category_new_store_customers_only: Boolean,
    val daypart_constraints: List<Any>,
    val delivery_fee: Int,
    val delivery_fee_monetary_fields: DeliveryFeeMonetaryFieldsX,
    val id: Int,
    val minimum_subtotal: Int,
    val minimum_subtotal_monetary_fields: MinimumSubtotalMonetaryFields
)

data class Status(
    val asap_available: Boolean,
    val asap_minutes_range: List<Int>,
    val pickup_available: Boolean,
    val scheduled_available: Boolean,
    val unavailable_reason: Any
)

data class PopularItem(
    val description: String,
    val id: Int,
    val img_url: String,
    val name: String,
    val price: Int
)

data class DeliveryFeeMonetaryFieldsX(
    val currency: String,
    val decimal_places: Int,
    val display_string: String,
    val unit_amount: Int
)

data class MinimumSubtotalMonetaryFields(
    val currency: String,
    val decimal_places: Int,
    val display_string: String,
    val unit_amount: Int
)
