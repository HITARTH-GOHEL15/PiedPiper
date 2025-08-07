package org.example.project.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.trendingUp
import org.example.project.design.MockMateColors
import org.example.project.design.MockMateTheme
import org.jetbrains.compose.resources.painterResource
import kotlin.math.cos
import kotlin.math.sin

data class InterviewHistoryItem(
    val id: String,
    val title: String,
    val date: String,
    val type: InterviewType,
    val domain: String,
    val score: Int,
    val keyMetrics: List<String>,
    val duration: String,
    val feedback: String
)

data class ProgressStats(
    val totalInterviews: Int,
    val averageScore: Int,
    val improvementRate: String,
    val strongestSkills: List<String>,
    val areasToImprove: List<String>
)

enum class InterviewType(
    val displayName: String,
    val icon: ImageVector,
    val color: Color,
    val bgGradient: List<Color>
) {
    VOICE("Voice", Icons.Default.Call, MockMateColors.Primary,
        listOf(MockMateColors.Primary.copy(0.8f), MockMateColors.Primary.copy(0.2f))),
    CHAT("Chat", Icons.Default.Email, MockMateColors.Secondary,
        listOf(MockMateColors.Secondary.copy(0.8f), MockMateColors.Secondary.copy(0.2f))),
    VIDEO("Video", Icons.Default.Person, MockMateColors.Accent,
        listOf(MockMateColors.Accent.copy(0.8f), MockMateColors.Accent.copy(0.2f)))
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    onViewFeedback: (String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showFilters by remember { mutableStateOf(false) }

    // Enhanced sample data
    val historyItems = remember {
        listOf(
            InterviewHistoryItem(
                id = "1",
                title = "Software Engineer Interview",
                date = "July 26, 2024",
                type = InterviewType.VOICE,
                domain = "Technology",
                score = 85,
                keyMetrics = listOf("Problem-solving", "Communication", "Technical depth"),
                duration = "45 min",
                feedback = "Strong technical foundation with clear communication"
            ),
            InterviewHistoryItem(
                id = "2",
                title = "Product Manager Interview",
                date = "July 20, 2024",
                type = InterviewType.CHAT,
                domain = "Product",
                score = 78,
                keyMetrics = listOf("Strategy", "Leadership", "Analytics"),
                duration = "35 min",
                feedback = "Good strategic thinking, needs improvement in data analysis"
            ),
            InterviewHistoryItem(
                id = "3",
                title = "Data Scientist Interview",
                date = "July 15, 2024",
                type = InterviewType.VOICE,
                domain = "Analytics",
                score = 92,
                keyMetrics = listOf("Machine Learning", "Statistics", "Python"),
                duration = "50 min",
                feedback = "Exceptional technical skills and problem-solving approach"
            ),
            InterviewHistoryItem(
                id = "4",
                title = "Frontend Developer Interview",
                date = "July 10, 2024",
                type = InterviewType.VIDEO,
                domain = "Technology",
                score = 76,
                keyMetrics = listOf("React", "Design", "Performance"),
                duration = "40 min",
                feedback = "Good coding skills, could improve on system design"
            )
        )
    }

    val progressStats = remember {
        ProgressStats(
            totalInterviews = historyItems.size,
            averageScore = historyItems.map { it.score }.average().toInt(),
            improvementRate = "+12%",
            strongestSkills = listOf("Problem-solving", "Technical depth", "Communication"),
            areasToImprove = listOf("System design", "Data analysis", "Time management")
        )
    }

    MockMateTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MockMateColors.BackgroundDark,
                            MockMateColors.BackgroundDark.copy(0.95f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Enhanced Top Bar
                EnhancedTopBar(
                    showFilters = showFilters,
                    onToggleFilters = { showFilters = !showFilters }
                )

                // Tab Navigation
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = MockMateColors.Primary,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = MockMateColors.Primary,
                            height = 3.dp
                        )
                    },
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                "Overview",
                                fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                "History",
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = {
                            Text(
                                "Analytics",
                                fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Animated Content
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        slideInHorizontally(
                            initialOffsetX = { if (targetState > initialState) 300 else -300 }
                        ) with slideOutHorizontally(
                            targetOffsetX = { if (targetState > initialState) -300 else 300 }
                        )
                    }
                ) { tab ->
                    when (tab) {
                        0 -> OverviewTab(progressStats = progressStats, historyItems = historyItems)
                        1 -> HistoryTab(
                            historyItems = historyItems,
                            showFilters = showFilters,
                            onViewFeedback = onViewFeedback
                        )
                        2 -> AnalyticsTab(progressStats = progressStats, historyItems = historyItems)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnhancedTopBar(
    showFilters: Boolean,
    onToggleFilters: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(MockMateColors.Primary, MockMateColors.PrimaryVariant)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "M",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "MockMate",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Interview Progress",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(0.7f)
                )
            }
        }

        Row {
            IconButton(
                onClick = onToggleFilters,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        if (showFilters) MockMateColors.Primary.copy(0.2f)
                        else Color.Transparent
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Filters",
                    tint = if (showFilters) MockMateColors.Primary else Color.White.copy(0.7f)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White.copy(0.7f)
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MockMateColors.Accent.copy(0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = MockMateColors.Accent,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun OverviewTab(
    progressStats: ProgressStats,
    historyItems: List<InterviewHistoryItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = "Your Journey Overview",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Track your progress and see how you're improving over time",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(0.7f)
            )
        }

        item {
            ProgressStatsSection(progressStats)
        }

        item {
            RecentInterviewsSection(
                recentInterviews = historyItems.take(3),
                onViewAll = { /* Navigate to History tab */ }
            )
        }

        item {
            SkillsOverviewSection(progressStats)
        }
    }
}

@Composable
private fun HistoryTab(
    historyItems: List<InterviewHistoryItem>,
    showFilters: Boolean,
    onViewFeedback: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        AnimatedVisibility(
            visible = showFilters,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            FilterSection()
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(historyItems) { index, item ->
                EnhancedInterviewCard(
                    item = item,
                    index = index,
                    onViewFeedback = { onViewFeedback(item.id) }
                )
            }
        }
    }
}

@Composable
private fun AnalyticsTab(
    progressStats: ProgressStats,
    historyItems: List<InterviewHistoryItem>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = "Performance Analytics",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        item {
            ScoreProgressChart(historyItems)
        }

        item {
            InterviewTypeBreakdown(historyItems)
        }

        item {
            SkillsRadarChart(progressStats)
        }
    }
}

@Composable
private fun ProgressStatsSection(progressStats: ProgressStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MockMateColors.SurfaceDark.copy(0.8f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(
                    value = progressStats.totalInterviews.toString(),
                    label = "Interviews",
                    color = MockMateColors.Primary
                )
                StatItem(
                    value = "${progressStats.averageScore}%",
                    label = "Avg Score",
                    color = MockMateColors.Accent
                )
                StatItem(
                    value = progressStats.improvementRate,
                    label = "Growth",
                    color = MockMateColors.Success
                )
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(0.7f)
        )
    }
}

@Composable
private fun EnhancedInterviewCard(
    item: InterviewHistoryItem,
    index: Int,
    onViewFeedback: () -> Unit
) {
    val animatedScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(animatedScale)
            .clickable { onViewFeedback() },
        colors = CardDefaults.cardColors(
            containerColor = MockMateColors.SurfaceDark.copy(0.9f)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = item.type.bgGradient
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.type.icon,
                            contentDescription = item.type.displayName,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "${item.date} • ${item.duration}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(0.6f)
                        )
                    }
                }

                // Score Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            when {
                                item.score >= 85 -> MockMateColors.Success.copy(0.2f)
                                item.score >= 70 -> MockMateColors.Warning.copy(0.2f)
                                else -> MockMateColors.Error.copy(0.2f)
                            }
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${item.score}%",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            item.score >= 85 -> MockMateColors.Success
                            item.score >= 70 -> MockMateColors.Warning
                            else -> MockMateColors.Error
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Key Metrics Tags
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(item.keyMetrics) { metric ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MockMateColors.Primary.copy(0.15f))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = metric,
                            style = MaterialTheme.typography.labelSmall,
                            color = MockMateColors.Primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.feedback,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(0.7f),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onViewFeedback,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MockMateColors.Primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "View Detailed Feedback",
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun FilterSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MockMateColors.SurfaceDark.copy(0.8f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Filter Interviews",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    selected = true,
                    onClick = { },
                    label = { Text("All Time") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("This Month") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = false,
                    onClick = { },
                    label = { Text("High Scores") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun RecentInterviewsSection(
    recentInterviews: List<InterviewHistoryItem>,
    onViewAll: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MockMateColors.SurfaceDark.copy(0.8f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Interviews",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                TextButton(onClick = onViewAll) {
                    Text("View All", color = MockMateColors.Primary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            recentInterviews.forEach { interview ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(interview.type.color.copy(0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = interview.type.icon,
                            contentDescription = null,
                            tint = interview.type.color,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = interview.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = interview.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(0.6f)
                        )
                    }
                    Text(
                        text = "${interview.score}%",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = interview.type.color
                    )
                }
            }
        }
    }
}

@Composable
private fun SkillsOverviewSection(progressStats: ProgressStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MockMateColors.Success.copy(0.1f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.trendingUp),
                    contentDescription = null,
                    tint = MockMateColors.Success,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Strongest Skills",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                progressStats.strongestSkills.take(2).forEach { skill ->
                    Text(
                        text = "• $skill",
                        style = MaterialTheme.typography.bodySmall,
                        color = MockMateColors.Success
                    )
                }
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MockMateColors.Warning.copy(0.1f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.trendingUp),
                    contentDescription = null,
                    tint = MockMateColors.Warning,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Areas to Improve",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                progressStats.areasToImprove.take(2).forEach { area ->
                    Text(
                        text = "• $area",
                        style = MaterialTheme.typography.bodySmall,
                        color = MockMateColors.Warning
                    )
                }
            }
        }
    }
}

@Composable
private fun ScoreProgressChart(historyItems: List<InterviewHistoryItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MockMateColors.SurfaceDark.copy(0.8f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Score Progression",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Simplified chart representation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                historyItems.forEach { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height((item.score.toFloat() / 100f * 80).dp)
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            item.type.color,
                                            item.type.color.copy(0.3f)
                                        )
                                    )
                                )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${item.score}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InterviewTypeBreakdown(historyItems: List<InterviewHistoryItem>) {
    val typeCount = historyItems.groupBy { it.type }.mapValues { it.value.size }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MockMateColors.SurfaceDark.copy(0.8f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Interview Types",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            InterviewType.values().forEach { type ->
                val count = typeCount[type] ?: 0
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = type.icon,
                        contentDescription = null,
                        tint = type.color,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = type.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = count.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = type.color
                    )
                }
            }
        }
    }
}

@Composable
private fun SkillsRadarChart(progressStats: ProgressStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MockMateColors.SurfaceDark.copy(0.8f)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Skills Assessment",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Simplified radar chart representation
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // Concentric circles background
                repeat(3) { ring ->
                    Box(
                        modifier = Modifier
                            .size((200 - ring * 50).dp)
                            .clip(CircleShape)
                            .border(
                                1.dp,
                                Color.White.copy(0.1f),
                                CircleShape
                            )
                    )
                }

                // Skills points
                val skills = listOf(
                    "Technical" to 85f,
                    "Communication" to 78f,
                    "Problem Solving" to 92f,
                    "Leadership" to 72f,
                    "Creativity" to 88f
                )

                skills.forEachIndexed { index, (skill, score) ->
                    val angle = (index * 360f / skills.size) - 90f
                    val radius = (score / 100f) * 80f


                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(MockMateColors.Primary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Skills legend
            Column {
                val skills = listOf(
                    "Technical Skills" to 85,
                    "Communication" to 78,
                    "Problem Solving" to 92,
                    "Leadership" to 72,
                    "Creativity" to 88
                )

                skills.forEach { (skill, score) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MockMateColors.Primary)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = skill,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$score%",
                            style = MaterialTheme.typography.labelSmall,
                            color = MockMateColors.Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// Additional animated components for enhanced UX

@Composable
private fun PulsatingDot(
    color: Color,
    size: Int = 8,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .size(size.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
private fun AnimatedProgressBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = EaseOutCubic)
    )

    Box(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White.copy(0.1f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            color.copy(0.6f),
                            color
                        )
                    )
                )
        )
    }
}

@Composable
private fun FloatingActionSection(
    onStartNewInterview: () -> Unit
) {
    FloatingActionButton(
        onClick = onStartNewInterview,
        containerColor = MockMateColors.Primary,
        contentColor = Color.White,
        modifier = Modifier.size(64.dp),
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Start New Interview",
            modifier = Modifier.size(28.dp)
        )
    }
}