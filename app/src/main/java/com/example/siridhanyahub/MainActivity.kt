package com.example.siridhanyahub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.siridhanyahub.data.entity.CartItemEntity
import com.example.siridhanyahub.data.entity.FarmerEntity
import com.example.siridhanyahub.data.entity.HealthFactEntity
import com.example.siridhanyahub.data.entity.MilletEntity
import com.example.siridhanyahub.data.entity.RecipeEntity

// ─────────────────────────────────────────────
// Color palette
// ─────────────────────────────────────────────
val EarthBrown  = Color(0xFF5D4037)
val DarkBrown   = Color(0xFF3E2723)
val WarmSand    = Color(0xFFD7B896)
val SoftCream   = Color(0xFFFFF8F0)
val MutedTaupe  = Color(0xFFF3E5D8)
val LeafGreen   = Color(0xFF558B2F)
val LightGreen  = Color(0xFFDCEDC8)
val AmberGold   = Color(0xFFFFB300)
val LightAmber  = Color(0xFFFFF3E0)
val TrendUp     = Color(0xFF2E7D32)
val TrendDown   = Color(0xFFC62828)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val milletVm = remember { MilletViewModel() }
            val recipeVm = remember { RecipeViewModel() }
            val healthVm = remember { HealthViewModel() }
            val farmerVm = remember { FarmerViewModel() }
            val cartVm   = remember { CartViewModel() }

            SiriDhanyaApp(
                milletViewModel = milletVm,
                recipeViewModel = recipeVm,
                healthViewModel = healthVm,
                farmerViewModel = farmerVm,
                cartViewModel   = cartVm
            )
        }
    }
}

// ─────────────────────────────────────────────
// Root app
// ─────────────────────────────────────────────
@Composable
fun SiriDhanyaApp(
    milletViewModel: MilletViewModel,
    recipeViewModel: RecipeViewModel,
    healthViewModel: HealthViewModel,
    farmerViewModel: FarmerViewModel,
    cartViewModel:   CartViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }
    val totalQty = cartViewModel.totalQty

    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = DarkBrown, tonalElevation = 0.dp) {
                    listOf(
                        Triple(Icons.Filled.Home,         "Mandi",   0),
                        Triple(Icons.Filled.MenuBook,     "Recipes", 1),
                        Triple(Icons.Filled.Favorite,     "Health",  2),
                        Triple(Icons.Filled.Agriculture,  "Farmers", 3),
                        Triple(Icons.Filled.ShoppingCart, "Cart",    4)
                    ).forEach { (icon, label, idx) ->
                        NavigationBarItem(
                            selected = selectedTab == idx,
                            onClick  = { selectedTab = idx },
                            icon = {
                                BadgedBox(badge = {
                                    if (idx == 4 && totalQty > 0) {
                                        Badge(containerColor = AmberGold) {
                                            Text("$totalQty", color = DarkBrown, fontSize = 10.sp)
                                        }
                                    }
                                }) {
                                    Icon(icon, contentDescription = label)
                                }
                            },
                            label  = { Text(label, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor   = AmberGold,
                                selectedTextColor   = AmberGold,
                                unselectedIconColor = WarmSand,
                                unselectedTextColor = WarmSand,
                                indicatorColor      = EarthBrown
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                color = SoftCream
            ) {
                when (selectedTab) {
                    0 -> MandiWatchScreen(milletViewModel)
                    1 -> RecipeLabScreen(recipeViewModel)
                    2 -> HealthScreen(healthViewModel)
                    3 -> DirectBuyScreen(farmerViewModel, cartViewModel)
                    4 -> CartScreen(cartViewModel)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// Reusable components
// ─────────────────────────────────────────────
@Composable
fun TopBanner(emoji: String, title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(DarkBrown, EarthBrown, Color(0xFF8D6E63))
                ),
                RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .padding(horizontal = 20.dp, vertical = 22.dp)
    ) {
        Column {
            Text(emoji, fontSize = 38.sp)
            Spacer(Modifier.height(6.dp))
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SoftCream)
            Text(subtitle, fontSize = 12.sp, color = WarmSand)
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        placeholder = { Text(placeholder, fontSize = 13.sp, color = Color.Gray) },
        leadingIcon = { Icon(Icons.Filled.Search, null, tint = EarthBrown) },
        trailingIcon = {
            AnimatedVisibility(query.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Filled.Clear, null, tint = EarthBrown)
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(50.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor      = EarthBrown,
            unfocusedBorderColor    = WarmSand,
            cursorColor             = EarthBrown,
            focusedContainerColor   = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

// ─────────────────────────────────────────────
// 1. Mandi Watch
// ─────────────────────────────────────────────
@Composable
fun MandiWatchScreen(vm: MilletViewModel) {
    val query   = vm.searchQuery
    val millets = vm.millets

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        item {
            TopBanner("🌾", "Mandi Watch", "Live simulated (dummy) prices across Karnataka")
            Spacer(Modifier.height(4.dp))
            Box(Modifier.padding(horizontal = 16.dp)) {
                SearchBar(query, vm::onSearchQueryChange, "Search city or millet type…")
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = LightGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🌱", fontSize = 20.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Millets require ~70% less water than paddy — climate‑resilient crops!",
                        fontSize = 12.sp,
                        color = DarkBrown,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
        }
        if (millets.isEmpty()) {
            item {
                Text(
                    "No results for \"$query\"",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        } else {
            items(millets, key = { it.id }) { millet ->
                MandiCard(millet)
            }
        }
    }
}

@Composable
fun MandiCard(millet: MilletEntity) {
    val isUp = millet.trend == "↑"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MutedTaupe),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            if (isUp) LightGreen else Color(0xFFFFEBEE)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        millet.trend,
                        fontSize = 20.sp,
                        color = if (isUp) TrendUp else TrendDown,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        millet.milletName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown
                    )
                    Text("📍 ${millet.city}", fontSize = 12.sp, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        millet.price,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = EarthBrown
                    )
                    Text(
                        if (isUp) "▲ Rising" else "▼ Falling",
                        fontSize = 11.sp,
                        color = if (isUp) TrendUp else TrendDown
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Divider(color = WarmSand, thickness = 0.8.dp)
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth()) {
                Column(
                    Modifier
                        .weight(1f)
                        .background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        "7‑Day HIGH",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        millet.high7Days,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TrendUp
                    )
                }
                Spacer(Modifier.width(8.dp))
                Column(
                    Modifier
                        .weight(1f)
                        .background(Color(0xFFFFEBEE), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        "7‑Day LOW",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        millet.low7Days,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TrendDown
                    )
                }
                Spacer(Modifier.width(8.dp))
                Column(
                    Modifier
                        .weight(1f)
                        .background(LightGreen, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        "🌱 Climate",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        millet.waterSaving,
                        fontSize = 10.sp,
                        color = LeafGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// 2. Recipe Lab
// ─────────────────────────────────────────────
@Composable
fun RecipeLabScreen(vm: RecipeViewModel) {
    val query      = vm.searchQuery
    val filter     = vm.milletFilter
    val recipes    = vm.recipes
    val savedCount = vm.savedRecipes
    var openRecipe by remember { mutableStateOf<RecipeEntity?>(null) }

    fun String.toStepsList() = this.split("|").filter { it.isNotBlank() }

    openRecipe?.let { recipe ->
        Dialog(onDismissRequest = { openRecipe = null }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SoftCream)
            ) {
                LazyColumn(Modifier.padding(20.dp)) {
                    item {
                        Text(
                            "${recipe.emoji} ${recipe.name}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkBrown
                        )
                        Text(
                            "Millet: ${recipe.milletType}  |  ${recipe.language}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "📋 ಹಂತಗಳು / Steps",
                            fontWeight = FontWeight.SemiBold,
                            color = EarthBrown
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    items(recipe.steps.toStepsList()) { step ->
                        Text(
                            step,
                            fontSize = 13.sp,
                            color = DarkBrown,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    item {
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { openRecipe = null },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = EarthBrown),
                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Text("Close", color = SoftCream)
                        }
                    }
                }
            }
        }
    }

    val milletTypes = listOf("All", "Ragi", "Navane", "Saame", "Sajje", "Baragu")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        item {
            TopBanner("🍲", "Recipe Lab", "Step‑by‑step Kannada millet recipes")
            Spacer(Modifier.height(4.dp))
            Box(Modifier.padding(horizontal = 16.dp)) {
                SearchBar(query, vm::onSearchQueryChange, "Search by recipe name or millet type…")
            }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(milletTypes) { type ->
                    val selected = filter == type
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .clickable { vm.onFilterChange(type) }
                            .border(
                                1.dp,
                                if (selected) EarthBrown else WarmSand,
                                RoundedCornerShape(50.dp)
                            ),
                        color = if (selected) EarthBrown else Color.White
                    ) {
                        Text(
                            type,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            fontSize = 12.sp,
                            color = if (selected) SoftCream else EarthBrown,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            if (savedCount.isNotEmpty()) {
                Text(
                    "⭐ ${savedCount.size} recipe(s) saved",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    color = EarthBrown,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        items(recipes, key = { it.id }) { recipe ->
            RecipeCard(
                recipe = recipe,
                onSave = { vm.toggleSaved(recipe) },
                onViewSteps = { openRecipe = recipe }
            )
        }
    }
}

@Composable
fun RecipeCard(
    recipe: RecipeEntity,
    onSave: () -> Unit,
    onViewSteps: () -> Unit
) {
    val stepCount = recipe.steps.split("|").filter { it.isNotBlank() }.size
    val firstStep = recipe.steps.split("|").firstOrNull()?.trim() ?: ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightGreen),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(14.dp)) {

            recipe.imageName?.let { name ->
                val context = LocalContext.current
                val resId = remember(name) {
                    context.resources.getIdentifier(name, "drawable", context.packageName)
                }
                if (resId != 0) {
                    Image(
                        painter = painterResource(resId),
                        contentDescription = recipe.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(recipe.emoji, fontSize = 34.sp)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        recipe.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown
                    )
                    Text(
                        "Millet: ${recipe.milletType}",
                        fontSize = 12.sp,
                        color = EarthBrown
                    )
                    Text(
                        recipe.language,
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic
                    )
                }
                IconButton(onClick = onSave) {
                    Icon(
                        if (recipe.isSaved) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Save",
                        tint = if (recipe.isSaved) TrendDown else EarthBrown
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(firstStep, fontSize = 12.sp, color = DarkBrown)
            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$stepCount steps", fontSize = 11.sp, color = Color.Gray)
                Button(
                    onClick = onViewSteps,
                    colors = ButtonDefaults.buttonColors(containerColor = EarthBrown),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("View steps", fontSize = 12.sp, color = SoftCream)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// 3. Health screen
// ─────────────────────────────────────────────
@Composable
fun HealthScreen(vm: HealthViewModel) {
    val query  = vm.searchQuery
    val facts  = vm.healthFacts

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        item {
            TopBanner("❤️", "Millet Health", "Everyday health facts in simple language")
            Spacer(Modifier.height(4.dp))
            Box(Modifier.padding(horizontal = 16.dp)) {
                SearchBar(query, vm::onSearchQueryChange, "Search by health benefit or millet name…")
            }
        }
        items(facts, key = { it.id }) { fact ->
            HealthFactCard(fact)
        }
    }
}

@Composable
fun HealthFactCard(fact: HealthFactEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightAmber),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(14.dp)) {

            fact.imageName?.let { name ->
                val context = LocalContext.current
                val resId = remember(name) {
                    context.resources.getIdentifier(name, "drawable", context.packageName)
                }
                if (resId != 0) {
                    Image(
                        painter = painterResource(resId),
                        contentDescription = fact.milletName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(fact.emoji, fontSize = 30.sp)
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        fact.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown
                    )
                    Text(
                        "Millet: ${fact.milletName}",
                        fontSize = 12.sp,
                        color = EarthBrown
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(
                fact.description,
                fontSize = 13.sp,
                color = DarkBrown
            )
        }
    }
}

// ─────────────────────────────────────────────
// 4. Direct buy (Farmers + cart)
// ─────────────────────────────────────────────
@Composable
fun DirectBuyScreen(
    farmerViewModel: FarmerViewModel,
    cartViewModel: CartViewModel
) {
    val query   = farmerViewModel.searchQuery
    val farmers = farmerViewModel.farmers

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        item {
            TopBanner(
                "🤝",
                "Direct from Farmers",
                "Simulated connection with FPOs and local millet farmers"
            )
            Spacer(Modifier.height(4.dp))
            Box(Modifier.padding(horizontal = 16.dp)) {
                SearchBar(query, farmerViewModel::onSearchQueryChange, "Search farmer, location, FPO or millet…")
            }
        }
        items(farmers, key = { it.id }) { farmer ->
            FarmerCard(
                farmer = farmer,
                onAddToCart = { milletName ->
                    cartViewModel.addToCart(farmer.name, milletName)
                }
            )
        }
    }
}

@Composable
fun FarmerCard(
    farmer: FarmerEntity,
    onAddToCart: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MutedTaupe),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            farmer.imageName?.let { name ->
                val context = LocalContext.current
                val resId = remember(name) {
                    context.resources.getIdentifier(name, "drawable", context.packageName)
                }
                if (resId != 0) {
                    Image(
                        painter = painterResource(resId),
                        contentDescription = farmer.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.width(12.dp))
                }
            }

            Column(Modifier.weight(1f)) {
                Text(
                    farmer.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBrown
                )
                Text("📍 ${farmer.location}", fontSize = 12.sp, color = Color.Gray)
                Text(
                    "FPO: ${farmer.fpoName}",
                    fontSize = 12.sp,
                    color = EarthBrown,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    "Millets: ${farmer.millets}",
                    fontSize = 12.sp,
                    color = DarkBrown
                )
            }

            Button(
                onClick = { onAddToCart(farmer.millets) },
                colors = ButtonDefaults.buttonColors(containerColor = EarthBrown),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Add to cart", fontSize = 12.sp, color = SoftCream)
            }
        }
    }
}

// ─────────────────────────────────────────────
// 5. Cart screen
// ─────────────────────────────────────────────
@Composable
fun CartScreen(vm: CartViewModel) {
    val items = vm.cartItems
    val totalQty = vm.totalQty
    var showOrderDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        item {
            TopBanner("🧺", "My Cart", "Millets directly from farmers")
            Spacer(Modifier.height(8.dp))
        }
        if (items.isEmpty()) {
            item {
                Text(
                    "Your cart is empty.\nExplore farmers and add some millets!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        } else {
            items(items, key = { it.id }) { item ->
                CartItemCard(
                    item = item,
                    onRemove = { vm.removeItem(item) },
                    onIncrease = { vm.increaseQty(item) },
                    onDecrease = { vm.decreaseQty(item) }
                )
            }
            item {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Total items: $totalQty",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBrown
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { vm.clearCart() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EarthBrown),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Clear cart", color = SoftCream)
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { showOrderDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LeafGreen),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Place order (simulation)", color = SoftCream)
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }

    if (showOrderDialog) {
        Dialog(onDismissRequest = { showOrderDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SoftCream)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Order placed!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "This is a simulated order.\nIn a real app, your details would be sent to the FPO.",
                        fontSize = 13.sp,
                        color = DarkBrown,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { showOrderDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = EarthBrown),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text("OK", color = SoftCream)
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItemEntity,
    onRemove: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LightAmber),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    item.millet,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBrown
                )
                Text(
                    "Farmer: ${item.farmerName}",
                    fontSize = 12.sp,
                    color = EarthBrown
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease) {
                    Text("-", fontSize = 18.sp, color = DarkBrown)
                }
                Text("${item.qty}", fontSize = 14.sp, color = DarkBrown)
                IconButton(onClick = onIncrease) {
                    Text("+", fontSize = 18.sp, color = DarkBrown)
                }
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Filled.Clear, contentDescription = "Remove", tint = TrendDown)
            }
        }
    }
}

// ─────────────────────────────────────────────
// Fake ViewModels (in-memory state)
// ─────────────────────────────────────────────

class MilletViewModel {
    var searchQuery by mutableStateOf("")
        private set

    private val allMillets = listOf(
        MilletEntity(
            id = 1,
            city = "Davangere",
            milletName = "Ragi",
            milletType = "Finger millet",
            price = "₹35/kg",
            trend = "↑",
            high7Days = "₹40",
            low7Days = "₹30",
            waterSaving = "Uses 70% less water"
        ),
        MilletEntity(
            id = 2,
            city = "Bengaluru",
            milletName = "Navane",
            milletType = "Foxtail millet",
            price = "₹50/kg",
            trend = "↓",
            high7Days = "₹55",
            low7Days = "₹45",
            waterSaving = "Suited for dryland fields"
        )
    )

    var millets by mutableStateOf(allMillets)
        private set

    fun onSearchQueryChange(q: String) {
        searchQuery = q
        millets = if (q.isBlank()) {
            allMillets
        } else {
            allMillets.filter {
                it.city.contains(q, true) ||
                        it.milletName.contains(q, true) ||
                        it.milletType.contains(q, true)
            }
        }
    }
}

class RecipeViewModel {
    var searchQuery by mutableStateOf("")
        private set
    var milletFilter by mutableStateOf("All")
        private set

    private val allRecipes = listOf(
        // Ragi recipes
        RecipeEntity(
            id = 1,
            emoji = "🍚",
            name = "Ragi Mudde",
            milletType = "Ragi",
            language = "Kannada",
            steps = "Boil water|Add ragi flour|Stir continuously|Cook until thick|Shape into balls and serve with sambar",
            isSaved = false,
            imageName = "ragi"
        ),
        RecipeEntity(
            id = 2,
            emoji = "🥞",
            name = "Ragi Dosa",
            milletType = "Ragi",
            language = "Kannada",
            steps = "Soak ragi and urad dal|Grind to smooth batter|Ferment overnight|Spread on tawa|Roast with little oil and serve",
            isSaved = false,
            imageName = "milletidli"
        ),

        // Navane / Foxtail recipes
        RecipeEntity(
            id = 3,
            emoji = "🥣",
            name = "Navane Upma",
            milletType = "Navane",
            language = "Kannada",
            steps = "Dry roast navane|Temper mustard, chilli, curry leaves|Add vegetables and fry|Add water and navane|Cook till fluffy",
            isSaved = false,
            imageName = "foxtail_upma"
        ),
        RecipeEntity(
            id = 4,
            emoji = "🍲",
            name = "Navane Pongal",
            milletType = "Navane",
            language = "Kannada",
            steps = "Dry roast navane and moong dal|Cook in cooker with water|Temper pepper, cumin, ginger|Mix with cooked navane",
            isSaved = false,
            imageName = "pongal"
        ),

        // Baragu / Proso millet
        RecipeEntity(
            id = 5,
            emoji = "🍛",
            name = "Baragu Kheer",
            milletType = "Baragu",
            language = "Kannada",
            steps = "Wash baragu|Cook with milk and water|Add jaggery and cardamom|Simmer until thick|Garnish with nuts",
            isSaved = false,
            imageName = "kheer"
        ),

        // Sajje recipe
        RecipeEntity(
            id = 6,
            emoji = "🥗",
            name = "Sajje Rotti",
            milletType = "Sajje",
            language = "Kannada",
            steps = "Mix sajje flour with warm water and salt|Knead to soft dough|Pat thin rotti on tava by hand|Cook on both sides with little oil|Serve hot with chutney or palya",
            isSaved = false,
            imageName = "baragu"   // reuse any suitable photo
        )
    )

    var recipes by mutableStateOf(allRecipes)
        private set

    val savedRecipes: List<RecipeEntity>
        get() = recipes.filter { it.isSaved }

    private fun applyFilter() {
        recipes = allRecipes.filter { r ->
            val qs = searchQuery
            val matchesSearch =
                qs.isBlank() ||
                        r.name.contains(qs, true) ||
                        r.milletType.contains(qs, true)
            val matchesFilter =
                milletFilter == "All" || r.milletType.equals(milletFilter, true)
            matchesSearch && matchesFilter
        }
    }

    fun onSearchQueryChange(q: String) {
        searchQuery = q
        applyFilter()
    }

    fun onFilterChange(type: String) {
        milletFilter = type
        applyFilter()
    }

    fun toggleSaved(recipe: RecipeEntity) {
        recipes = recipes.map {
            if (it.id == recipe.id) it.copy(isSaved = !it.isSaved) else it
        }
    }
}

class HealthViewModel {
    var searchQuery by mutableStateOf("")
        private set

    private val allFacts = listOf(
        // Ragi
        HealthFactEntity(
            id = 1,
            emoji = "🦴",
            title = "High in calcium",
            description = "Ragi has much more calcium than rice and wheat, helping keep bones and teeth strong.",
            milletName = "Ragi",
            imageName = "ragi"
        ),
        HealthFactEntity(
            id = 2,
            emoji = "⚖️",
            title = "Supports weight loss",
            description = "The high fibre in ragi keeps you full for longer and reduces sudden hunger spikes.",
            milletName = "Ragi",
            imageName = "ragi"
        ),

        // Navane
        HealthFactEntity(
            id = 3,
            emoji = "💓",
            title = "Good for diabetics",
            description = "Navane (foxtail millet) has a low glycemic index, helping control blood sugar levels.",
            milletName = "Navane",
            imageName = "navane"
        ),
        HealthFactEntity(
            id = 4,
            emoji = "🌾",
            title = "Rich in fibre",
            description = "Navane has more dietary fibre than polished rice, supporting digestion and gut health.",
            milletName = "Navane",
            imageName = "foxtail"
        ),

        // Baragu
        HealthFactEntity(
            id = 5,
            emoji = "❤️",
            title = "Heart friendly",
            description = "Baragu contains magnesium and antioxidants that support heart health.",
            milletName = "Baragu",
            imageName = "baragu"
        ),

        // Saame
        HealthFactEntity(
            id = 6,
            emoji = "💪",
            title = "Good plant protein",
            description = "Saame provides plant-based protein and fibre, which helps in muscle repair and keeps you energetic.",
            milletName = "Saame",
            imageName = "oodalu"
        )
    )

    var healthFacts by mutableStateOf(allFacts)
        private set

    fun onSearchQueryChange(q: String) {
        searchQuery = q
        healthFacts = if (q.isBlank()) {
            allFacts
        } else {
            allFacts.filter {
                it.title.contains(q, true) ||
                        it.milletName.contains(q, true)
            }
        }
    }
}

class FarmerViewModel {
    var searchQuery by mutableStateOf("")
        private set

    private val allFarmers = listOf(
        FarmerEntity(
            id = 1,
            name = "Ravi",
            location = "Tumakuru",
            millets = "Ragi, Sajje",
            contact = "+91 98765 43001",
            fpoName = "GreenEarth FPO",
            imageName = "farmer1"
        ),
        FarmerEntity(
            id = 2,
            name = "Lakshmi",
            location = "Davangere",
            millets = "Navane, Saame",
            contact = "+91 98765 43002",
            fpoName = "Navane Farmers FPO",
            imageName = "farmer2"
        ),
        FarmerEntity(
            id = 3,
            name = "Manjunath",
            location = "Chitradurga",
            millets = "Ragi, Baragu",
            contact = "+91 98765 43003",
            fpoName = "Sri Siri Dhanya FPO",
            imageName = "farmer3"
        ),
        FarmerEntity(
            id = 4,
            name = "Savitha",
            location = "Ballari",
            millets = "Sajje, Navane",
            contact = "+91 98765 43004",
            fpoName = "Krishi Maitri FPO",
            imageName = "farmer4"
        ),
        FarmerEntity(
            id = 5,
            name = "Suresh",
            location = "Mandya",
            millets = "Ragi, Oodalu",
            contact = "+91 98765 43005",
            fpoName = "Mandya Millets FPO",
            imageName = "farmer5"
        ),
        FarmerEntity(
            id = 6,
            name = "Kavya",
            location = "Hassan",
            millets = "Navane, Korale",
            contact = "+91 98765 43006",
            fpoName = "Hassan Millet Collective",
            imageName = "farmer6"
        )
    )

    var farmers by mutableStateOf(allFarmers)
        private set

    fun onSearchQueryChange(q: String) {
        searchQuery = q
        farmers = if (q.isBlank()) {
            allFarmers
        } else {
            allFarmers.filter {
                it.name.contains(q, true) ||
                        it.location.contains(q, true) ||
                        it.millets.contains(q, true) ||
                        it.fpoName.contains(q, true)
            }
        }
    }
}

class CartViewModel {
    var cartItems by mutableStateOf<List<CartItemEntity>>(emptyList())
        private set

    val totalQty: Int
        get() = cartItems.sumOf { it.qty }

    fun addToCart(farmerName: String, millet: String) {
        val existing = cartItems.find { it.farmerName == farmerName && it.millet == millet }
        cartItems = if (existing == null) {
            val newId = (cartItems.maxOfOrNull { it.id } ?: 0) + 1
            cartItems + CartItemEntity(
                id = newId,
                farmerName = farmerName,
                millet = millet,
                qty = 1
            )
        } else {
            cartItems.map {
                if (it.id == existing.id) it.copy(qty = it.qty + 1) else it
            }
        }
    }

    fun removeItem(item: CartItemEntity) {
        cartItems = cartItems.filterNot { it.id == item.id }
    }

    fun increaseQty(item: CartItemEntity) {
        cartItems = cartItems.map {
            if (it.id == item.id) it.copy(qty = it.qty + 1) else it
        }
    }

    fun decreaseQty(item: CartItemEntity) {
        cartItems = cartItems.map {
            if (it.id == item.id && it.qty > 1) it.copy(qty = it.qty - 1) else it
        }
    }

    fun clearCart() {
        cartItems = emptyList()
    }
}