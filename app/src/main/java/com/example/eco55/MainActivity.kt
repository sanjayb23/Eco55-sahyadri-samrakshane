package com.example.eco55

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.lazy.items
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.TextStyle


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Eco55App()
        }
    }
}

@Composable
fun Eco55App() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable("signup") {
            SignupScreen(navController)
        }

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("report") {
            ReportScreen()
        }

        composable("viewReports") {
            ViewReportsScreen()
        }
        composable("plantDoctor") {
            PlantDoctorScreen()
        }

        composable("liveMap") {
            LiveMapScreen()
        }
    }
}

val AppBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF081C15),
        Color(0xFF1B4332),
        Color(0xFF2D6A4F)
    )
)

val CardGreen = Color(0xAA1B4332)

val PrimaryGreen = Color(0xFF2E7D32)

val SecondaryGreen = Color(0xFF40916C)

val SoftText = Color(0xFFD8F3DC)

data class ReportData(
    val alertType: String = "",
    val description: String = "",
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class FeatureItem(
    val title: String,
    val description: String,
    val emoji: String
)

@Composable
fun WelcomeScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier.size(240.dp),

                shape = RoundedCornerShape(40.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0x2200FF66)
                )
            ) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "🌿",
                        fontSize = 120.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Welcome to Eco55",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Report environmental issues and help protect our planet.",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    navController.navigate("dashboard")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {

                Text(
                    text = "Continue",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SignupScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Join Eco55",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    navController.navigate("dashboard")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                shape = RoundedCornerShape(30.dp)
            ) {

                Text("Continue")
            }
        }
    }
}

@Composable
fun DashboardScreen(
    navController: NavHostController
) {

    val features = listOf(

        FeatureItem(
            "Report Incident",
            "Report environmental issues",
            "⚠️"
        ),

        FeatureItem(
            "View Reports",
            "See all reported incidents",
            "📄"
        ),

        FeatureItem(
            "AI Plant Doctor",
            "Scan and diagnose plants",
            "🌱"
        ),

        FeatureItem(
            "Live Map",
            "View incidents on map",
            "📍"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ){

            Text(
                text = "Hello, Sanjay 👋",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Let's protect our environment together.",
                color = Color.LightGray,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = CardGreen
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),

                    horizontalArrangement = Arrangement.SpaceBetween,

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {

                        Text(
                            text = "Total Reports",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "24",
                            color = Color.White,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "+12 this week",
                            color = Color.Green,
                            fontSize = 14.sp
                        )
                    }

                    Text(
                        text = "🌿",
                        fontSize = 70.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),

                verticalArrangement = Arrangement.spacedBy(16.dp),

                horizontalArrangement = Arrangement.spacedBy(16.dp),

                modifier = Modifier.height(340.dp)
            ) {

                items(
                    count = features.size
                ) { index ->

                    val item = features[index]

                    FeatureCard(
                        item = item,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun FeatureCard(
    item: FeatureItem,
    navController: NavHostController
) {

    Card(

        onClick = {

            when(item.title) {

                "Report Incident" -> {
                    navController.navigate("report")
                }

                "View Reports" -> {
                    navController.navigate("viewReports")
                }

                "AI Plant Doctor" -> {
                    navController.navigate("plantDoctor")
                }

                "Live Map" -> {
                    navController.navigate("liveMap")
                }
            }
        },

        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0x2200FF66)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),

            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.emoji,
                fontSize = 42.sp
            )

            Column {

                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.description,
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen() {

    val database = Firebase.database.reference

    val context = LocalContext.current

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    var locationText by remember {
        mutableStateOf("Location not fetched")
    }

    var latitude by remember {
        mutableDoubleStateOf(0.0)
    }

    var longitude by remember {
        mutableDoubleStateOf(0.0)
    }

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    var description by remember {
        mutableStateOf("")
    }

    var submitMessage by remember {
        mutableStateOf("")
    }

    var selectedAlert by remember {
        mutableStateOf("Select Alert Type")

    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val alertTypes = listOf(
        "Forest Fire",
        "Wildlife Threat",
        "Landslide",
        "Illegal Tree Cutting"
    )

    val imageLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            selectedImage = uri
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                if (
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->

                            if (location != null) {

                                locationText =
                                    "Lat: ${location.latitude}, Lng: ${location.longitude}"

                                latitude = location.latitude
                                longitude = location.longitude
                            }
                        }
                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(20.dp)
    ) {

        Column {

            Text(
                text = "Report Incident",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    imageLauncher.launch("image/*")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen
                )
            ) {

                Text(
                    text = "Upload Incident Image",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            selectedImage?.let {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),

                    shape = RoundedCornerShape(20.dp)
                ) {

                    AsyncImage(
                        model = it,
                        contentDescription = null,

                        modifier = Modifier.fillMaxSize(),

                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,

                onExpandedChange = {
                    expanded = !expanded
                }
            ) {

                OutlinedTextField(
                    value = selectedAlert,
                    onValueChange = {},
                    readOnly = true,

                    textStyle = TextStyle(
                        color = Color.White
                    ),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.LightGray
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),

                    label = {
                        Text(
                            "Alert Type",
                            color = Color.White
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,

                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    alertTypes.forEach {

                        DropdownMenuItem(
                            text = {
                                Text(it)
                            },

                            onClick = {
                                selectedAlert = it
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },

                label = {
                    Text(
                        "Description",
                        color = Color.White
                    )
                },

                textStyle = TextStyle(
                    color = Color.White
                ),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )


            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {

                    if (
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->

                                if (location != null) {

                                    locationText =
                                        "Lat: ${location.latitude}, Lng: ${location.longitude}"

                                    latitude = location.latitude
                                    longitude = location.longitude
                                }
                            }

                    } else {

                        permissionLauncher.launch(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {

                Text(
                    text = "Get Current Location",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = locationText,
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    val reportData = mapOf(
                        "alertType" to selectedAlert,
                        "description" to description,
                        "location" to locationText,
                        "latitude" to latitude,
                        "longitude" to longitude,
                        "time" to System.currentTimeMillis()
                    )

                    database.child("reports")
                        .push()
                        .setValue(reportData)

                    submitMessage = "Report Submitted Successfully"

                    description = ""
                    selectedAlert = "Select Alert Type"

                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {

                Text(
                    text = "Submit Report",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = submitMessage,
                color = Color.Green,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ViewReportsScreen() {

    val database = Firebase.database.reference

    var reports by remember {
        mutableStateOf(listOf<ReportData>())
    }

    DisposableEffect(Unit) {

        val listener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val reportList = mutableListOf<ReportData>()

                for (data in snapshot.children) {

                    val report =
                        data.getValue(ReportData::class.java)

                    report?.let {
                        reportList.add(it)
                    }
                }

                reports = reportList
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        database.child("reports")
            .addValueEventListener(listener)

        onDispose {
            database.child("reports")
                .removeEventListener(listener)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(20.dp)
    ) {

        LazyColumn {

            item {

                Text(
                    text = "Submitted Reports",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(25.dp))
            }

            items(
                items = reports
            ) { report ->

                ReportCard(
                    title = report.alertType,
                    description = report.description,
                    latitude = report.latitude,
                    longitude = report.longitude
                )
            }
        }
    }
}

@Composable
fun PlantDoctorScreen() {

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    var isScanning by remember {
        mutableStateOf(false)
    }

    var resultVisible by remember {
        mutableStateOf(false)
    }

    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            selectedImage = uri
            resultVisible = false
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "🌱",
                fontSize = 90.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "AI Plant Doctor",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Upload a plant image and get instant diagnosis",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {

                Text(
                    text = "Upload Plant Image",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            selectedImage?.let {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),

                    shape = RoundedCornerShape(25.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xAA1B4332)
                    )
                ) {

                    AsyncImage(
                        model = it,
                        contentDescription = null,

                        modifier = Modifier.fillMaxSize(),

                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        isScanning = true

                        Handler(Looper.getMainLooper()).postDelayed({
                            isScanning = false
                            resultVisible = true
                        }, 2500)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                ) {

                    Text(
                        text = "Scan Plant",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            if (isScanning) {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(20.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x2200FF66)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        CircularProgressIndicator(
                            color = Color.Green
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Analyzing plant health...",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (resultVisible) {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(25.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x2200FF66)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {

                        Text(
                            text = "Diagnosis Result",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "✅",
                                fontSize = 28.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "🧠",
                                        fontSize = 28.sp
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = "AI Confidence: 96%",
                                        color = Color.Cyan,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(15.dp))

                                LinearProgressIndicator(
                                    progress = 0.96f,

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(10.dp),

                                    color = Color.Green,
                                    trackColor = Color.DarkGray
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "✅",
                                        fontSize = 28.sp
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = "Plant is Healthy",
                                        color = Color.Green,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "No major disease detected. Leaves look fresh and healthy.",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Care Tips",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Disease Risk Level",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = 0.15f,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp),

                            color = Color.Green,
                            trackColor = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Low Risk Detected",
                            color = Color.Green,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "• Water the plant regularly",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "• Keep under indirect sunlight",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "• Use organic fertilizer every 2 weeks",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun LiveMapScreen() {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "📍",
                fontSize = 120.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Live Incident Map",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Open Google Maps to view incidents",
                color = Color.LightGray,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            Button(

                onClick = {

                    val latitude = 12.9716
                    val longitude = 77.5946

                    val gmmIntentUri = Uri.parse(
                        "geo:$latitude,$longitude?q=$latitude,$longitude(Environment Incident)"
                    )

                    val mapIntent = Intent(
                        Intent.ACTION_VIEW,
                        gmmIntentUri
                    )

                    mapIntent.setPackage("com.google.android.apps.maps")

                    context.startActivity(mapIntent)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen

                )
            ) {

                Text(
                    text = "Open Google Maps",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun ReportCard(
    title: String,
    description: String,
    latitude: Double,
    longitude: Double
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0x55338833)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "https://maps.google.com/?q=$latitude,$longitude"
                        )
                    )

                    context.startActivity(intent)
                }
            ) {

                Text("Open in Maps")
            }
        }
    }
}