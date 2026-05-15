package com.example.siridhanyahub.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// ── TABLE 1: Millet ──────────────────────────────────────────────
@Entity(tableName = "millet_table")
data class MilletEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val milletName: String,
    val milletType: String,
    val price: String,
    val trend: String,
    val high7Days: String,
    val low7Days: String,
    val waterSaving: String
)

// ── TABLE 2: Recipe ──────────────────────────────────────────────
@Entity(tableName = "recipe_table")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val emoji: String,
    val name: String,
    val milletType: String,
    val language: String,
    val steps: String,        // stored as "step1|step2|step3"
    val isSaved: Boolean = false,
    val imageName: String? = null    // NEW: drawable name like "foxtail_upma"
)

// ── TABLE 3: HealthFact ──────────────────────────────────────────
@Entity(tableName = "health_table")
data class HealthFactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val emoji: String,
    val title: String,
    val description: String,
    val milletName: String,
    val imageName: String? = null    // NEW: drawable name like "navane"
)

// ── TABLE 4: Farmer ──────────────────────────────────────────────
@Entity(tableName = "farmer_table")
data class FarmerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String,
    val millets: String,
    val contact: String,
    val fpoName: String,
    val imageName: String? = null    // NEW: drawable name like "farmer1"
)

// ── TABLE 5: CartItem ────────────────────────────────────────────
@Entity(tableName = "cart_table")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val farmerName: String,
    val millet: String,
    val qty: Int = 1
)