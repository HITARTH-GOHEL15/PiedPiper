package org.example.project.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.p
import kotlinx.coroutines.delay
import org.example.project.design.MockMateColors
import org.jetbrains.compose.resources.painterResource

data class FeedbackData(
    val confidenceScore: Int = 85,
    val confidenceLabel: String = "Very Confident!",
    val toneEmoji: String = "😊",
    val toneTitle: String = "Positive & Enthusiastic",
    val toneDescription: String = "Your tone is engaging and projects confidence.",
    val keyPhrases: List<String> = listOf("Teamwork", "Problem-solving", "Communication", "Leadership", "Adaptability"),
    val structureSteps: List<StructureStep> = listOf(
        StructureStep("Intro", "Clear Opening", true, Icons.Filled.PlayArrow),
        StructureStep("Body", "Detailed Examples", true, Icons.Filled.Home),
        StructureStep("Conclusion", "Strong Summary", true, Icons.Filled.CheckCircle)
    ),
    val tips: List<Tip> = listOf(
        Tip("Use the STAR method", "Provide specific examples using Situation, Task, Action, Result.", "⭐", TipPriority.HIGH),
        Tip("Maintain a positive attitude", "Frame challenges positively and focus on what you learned.", "💪", TipPriority.MEDIUM),
        Tip("Practice active listening", "Show engagement by nodding and asking follow-up questions.", "👂", TipPriority.LOW)
    )
)

data class StructureStep(
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val icon: ImageVector
)

data class Tip(
    val title: String,
    val description: String,
    val emoji: String,
    val priority: TipPriority
)

enum class TipPriority { HIGH, MEDIUM, LOW }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    onBackToHome: () -> Unit,
    onTryAgain: () -> Unit,
    onSettingsClick: () -> Unit,
    feedbackData: FeedbackData = FeedbackData()
) {
    var isVisible by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }
    var expandedTips by remember { mutableStateOf(setOf<Int>()) }

    // Animation trigger
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color(0xFF8B5CF6), Color(0xFF06B6D4))
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.p),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "MockMate",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Notifications */ }) {
                        Badge(
                            containerColor = Color(0xFFEF4444)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "Notifications"
                            )
                        }
                    }

                    IconButton(onClick = onSettingsClick) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = Color(0xFFFF6B6B).copy(alpha = 0.2f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "A",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF6B6B)
                                )
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(top = 70.dp , bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        initialOffsetY = { -50 },
                        animationSpec = tween(600, easing = EaseOutBack)
                    ) + fadeIn(animationSpec = tween(600))
                ) {
                    Column {
                        Text(
                            text = "Real-time AI Feedback",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Get instant feedback on your responses to improve your interview skills.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        initialOffsetY = { 100 },
                        animationSpec = tween(800, delayMillis = 200, easing = EaseOutBack)
                    ) + fadeIn(animationSpec = tween(800, delayMillis = 200))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Enhanced Confidence Score
                        AnimatedConfidenceScore(
                            score = feedbackData.confidenceScore,
                            label = feedbackData.confidenceLabel,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(24.dp))

                        // Enhanced Tone Analysis
                        AnimatedToneAnalysis(
                            emoji = feedbackData.toneEmoji,
                            title = feedbackData.toneTitle,
                            description = feedbackData.toneDescription,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        initialOffsetY = { 100 },
                        animationSpec = tween(1000, delayMillis = 400, easing = EaseOutBack)
                    ) + fadeIn(animationSpec = tween(1000, delayMillis = 400))
                ) {
                    Column {
                        Text(
                            text = "Key Phrases",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        AnimatedKeyPhrases(phrases = feedbackData.keyPhrases)
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        initialOffsetY = { 100 },
                        animationSpec = tween(1200, delayMillis = 600, easing = EaseOutBack)
                    ) + fadeIn(animationSpec = tween(1200, delayMillis = 600))
                ) {
                    Column {
                        Text(
                            text = "Response Structure",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AnimatedStructureSteps(steps = feedbackData.structureSteps)
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        initialOffsetY = { 100 },
                        animationSpec = tween(1400, delayMillis = 800, easing = EaseOutBack)
                    ) + fadeIn(animationSpec = tween(1400, delayMillis = 800))
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "AI Tips",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            // Priority Filter Tabs
                            TabRow(
                                selectedTabIndex = selectedTab,
                                modifier = Modifier.width(200.dp),
                                containerColor = Color.Transparent,
                                indicator = { }
                            ) {
                                listOf("All", "High", "Medium").forEachIndexed { index, title ->
                                    Tab(
                                        selected = selectedTab == index,
                                        onClick = { selectedTab = index },
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(
                                                if (selectedTab == index) MockMateColors.Primary
                                                else Color.Transparent
                                            )
                                    ) {
                                        Text(
                                            text = title,
                                            fontSize = 12.sp,
                                            color = if (selectedTab == index) Color.White
                                            else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Filter tips based on selected tab
            val filteredTips = when (selectedTab) {
                1 -> feedbackData.tips.filter { it.priority == TipPriority.HIGH }
                2 -> feedbackData.tips.filter { it.priority == TipPriority.MEDIUM }
                else -> feedbackData.tips
            }

            itemsIndexed(filteredTips) { index, tip ->
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(
                            400,
                            delayMillis = 1000 + (index * 100),
                            easing = EaseOutCubic
                        )
                    ) + fadeIn(animationSpec = tween(400, delayMillis = 1000 + (index * 100)))
                ) {
                    ExpandableTipCard(
                        tip = tip,
                        isExpanded = expandedTips.contains(index),
                        onExpandToggle = {
                            expandedTips = if (expandedTips.contains(index)) {
                                expandedTips - index
                            } else {
                                expandedTips + index
                            }
                        }
                    )
                }
            }

            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        initialOffsetY = { 100 },
                        animationSpec = tween(800, delayMillis = 1500, easing = EaseOutBack)
                    ) + fadeIn(animationSpec = tween(800, delayMillis = 1500))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AnimatedActionButton(
                            text = "Try Again",
                            onClick = onTryAgain,
                            isPrimary = false,
                            modifier = Modifier.weight(1f)
                        )

                        AnimatedActionButton(
                            text = "Back to Home",
                            onClick = onBackToHome,
                            isPrimary = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimatedConfidenceScore(
    score: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    var animatedScore by remember { mutableStateOf(0f) }

    LaunchedEffect(score) {
        val animation = Animatable(0f)
        animation.animateTo(
            targetValue = score / 100f,
            animationSpec = tween(durationMillis = 2000, easing = EaseOutCubic)
        ) {
            animatedScore = value
        }
    }

    Column(modifier = modifier) {
        Text(
            text = "Confidence Score",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp)
        ) {
            CircularProgressIndicator(
                progress = animatedScore,
                modifier = Modifier.fillMaxSize(),
                color = MockMateColors.Primary,
                strokeWidth = 8.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${(animatedScore * 100).toInt()}%",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AnimatedToneAnalysis(
    emoji: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        isVisible = true
    }

    Column(modifier = modifier) {
        Text(
            text = "Tone Analysis",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Add tone details */ },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = scaleIn(animationSpec = tween(600, easing = EaseOutBack))
                ) {
                    Text(
                        text = emoji,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }

                Column {
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedKeyPhrases(phrases: List<String>) {
    Column {
        phrases.chunked(3).forEachIndexed { rowIndex, rowPhrases ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowPhrases.forEachIndexed { phraseIndex, phrase ->
                    var isVisible by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay((rowIndex * 3 + phraseIndex) * 200L)
                        isVisible = true
                    }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = scaleIn(
                            animationSpec = tween(400, easing = EaseOutBack)
                        ) + fadeIn(animationSpec = tween(400))
                    ) {
                        KeyPhraseChip(phrase = phrase)
                    }
                }
            }
        }
    }
}

@Composable
private fun KeyPhraseChip(phrase: String) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100)
    )

    Card(
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { isPressed = !isPressed },
        colors = CardDefaults.cardColors(
            containerColor = if (isPressed) MockMateColors.Primary.copy(alpha = 0.2f)
            else MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = phrase,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = if (isPressed) MockMateColors.Primary
            else MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun AnimatedStructureSteps(steps: List<StructureStep>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            var isVisible by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                delay(index * 300L)
                isVisible = true
            }

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(600, easing = EaseOutBack)
                ) + fadeIn(animationSpec = tween(600))
            ) {
                EnhancedStructureStep(step = step)
            }

            // Connection line
            if (index < steps.size - 1) {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = scaleIn(
                        animationSpec = tween(400, delayMillis = 200, easing = EaseOutCubic)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EnhancedStructureStep(step: StructureStep) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    if (step.isCompleted) MockMateColors.Primary
                    else MaterialTheme.colorScheme.surfaceVariant
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = step.icon,
                contentDescription = step.title,
                tint = if (step.isCompleted) Color.White
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = step.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )

        Text(
            text = step.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun ExpandableTipCard(
    tip: Tip,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit
) {
    val priorityColor = when (tip.priority) {
        TipPriority.HIGH -> Color(0xFFFF5722)
        TipPriority.MEDIUM -> Color(0xFFFF9800)
        TipPriority.LOW -> Color(0xFF4CAF50)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandToggle() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(priorityColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tip.emoji,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tip.title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = tip.priority.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = priorityColor,
                            modifier = Modifier
                                .background(
                                    priorityColor.copy(alpha = 0.1f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = tip.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Filled.Clear else Icons.Filled.Create,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(start = 68.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Divider(
                        modifier = Modifier.padding(bottom = 12.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )

                    Text(
                        text = "💡 Additional insights and examples would appear here based on the tip category.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedActionButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100)
    )

    if (isPrimary) {
        Button(
            onClick = {
                isPressed = true
                onClick()
            },
            modifier = modifier
                .height(48.dp)
                .scale(scale),
            colors = ButtonDefaults.buttonColors(
                containerColor = MockMateColors.Primary
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(text)
        }
    } else {
        OutlinedButton(
            onClick = {
                isPressed = true
                onClick()
            },
            modifier = modifier
                .height(48.dp)
                .scale(scale),
            shape = RoundedCornerShape(12.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 2.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(MockMateColors.Primary, MockMateColors.Primary.copy(alpha = 0.7f))
                )
            )
        ) {
            Text(text, color = MockMateColors.Primary)
        }
    }
}