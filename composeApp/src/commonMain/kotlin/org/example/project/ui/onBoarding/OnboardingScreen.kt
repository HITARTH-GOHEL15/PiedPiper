package org.example.project.ui.onBoarding

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.p
import kotlinproject.composeapp.generated.resources.trendingUp
import org.example.project.design.MockMateTheme
import org.jetbrains.compose.resources.painterResource
import kotlin.math.sin
import kotlin.math.PI

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: @Composable () -> Unit,
    val iconColor: Color,
    val backgroundColor: Color,
    val accentColor: Color
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit
) {
    val pages = remember {
        listOf(
            OnboardingPage(
                title = "Practice Interviews with AI",
                description = "Get real feedback from our AI interviewer to improve your interview skills and boost your confidence",
                icon = { InterviewIllustration() },
                iconColor = Color(0xFF8B5CF6),
                backgroundColor = Color(0xFFF8F4FF),
                accentColor = Color(0xFF6D28D9)
            ),
            OnboardingPage(
                title = "Speak or Chat",
                description = "Practice through voice conversations or text-based interviews, whatever feels comfortable for you",
                icon = { CommunicationIllustration() },
                iconColor = Color(0xFF06B6D4),
                backgroundColor = Color(0xFFECFEFF),
                accentColor = Color(0xFF0891B2)
            ),
            OnboardingPage(
                title = "Improve with Smart Tips",
                description = "Receive personalized feedback on tone, confidence, and communication skills in real-time",
                icon = { FeedbackIllustration() },
                iconColor = Color(0xFF10B981),
                backgroundColor = Color(0xFFECFDF5),
                accentColor = Color(0xFF059669)
            )
        )
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })
    var currentPage by remember { mutableIntStateOf(0) }

    // Enhanced page tracking with smoother transitions
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPage = page
        }
    }

    // Dynamic background color based on current page
    val animatedBackgroundColor by animateColorAsState(
        targetValue = pages[currentPage].backgroundColor,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "background_color"
    )

    MockMateTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            animatedBackgroundColor,
                            Color.White.copy(alpha = 0.95f),
                            Color.White
                        )
                    )
                )
        ) {
            // Animated background particles
            AnimatedBackgroundParticles(currentPage = currentPage)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // Enhanced App Logo
                EnhancedAppLogo(currentPage = currentPage)

                Spacer(modifier = Modifier.height(32.dp))

                // Progress bar
                EnhancedProgressBar(
                    currentPage = currentPage,
                    totalPages = pages.size,
                    color = pages[currentPage].accentColor
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Enhanced Pager Content
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    pageSpacing = 16.dp
                ) { page ->
                    EnhancedOnboardingPageContent(
                        page = pages[page],
                        isActive = page == currentPage,
                        pageOffset = pagerState.currentPageOffsetFraction
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Enhanced Page Indicators with preview
                EnhancedPageIndicators(
                    currentPage = currentPage,
                    pages = pages,
                    onPageClick = { targetPage ->
                        // Add haptic feedback here if needed
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Enhanced Action Buttons
                EnhancedActionButtons(
                    currentPage = currentPage,
                    totalPages = pages.size,
                    onNext = {
                        if (currentPage < pages.size - 1) {
                            // Animate to next page
                        } else {
                            onGetStarted()
                        }
                    },
                    onSkip = onGetStarted,
                    accentColor = pages[currentPage].accentColor
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun EnhancedAppLogo(currentPage: Int) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "logo_rotation"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "logo_pulse"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .scale(pulse)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF8B5CF6),
                            Color(0xFF06B6D4),
                            Color(0xFF10B981)
                        ),
                        radius = 100f
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .rotate(rotation * 0.05f)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                    spotColor = Color(0xFF8B5CF6).copy(alpha = 0.3f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.p),
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(1000)) + slideInVertically(tween(1000)) { it },
        ) {
            Text(
                text = "MockMate",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                ),
                color = Color(0xFF1F2937),
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF8B5CF6), Color(0xFF06B6D4))
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun EnhancedProgressBar(
    currentPage: Int,
    totalPages: Int,
    color: Color
) {
    val progress = (currentPage + 1).toFloat() / totalPages.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Step ${currentPage + 1}",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = color
            )
            Text(
                text = "$totalPages",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(
                    color = color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(3.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(color, color.copy(alpha = 0.8f))
                        ),
                        shape = RoundedCornerShape(3.dp)
                    )
            )
        }
    }
}

@Composable
fun EnhancedOnboardingPageContent(
    page: OnboardingPage,
    isActive: Boolean,
    pageOffset: Float
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.3f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "content_alpha"
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (isActive) 1f else 0.85f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "content_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .alpha(animatedAlpha)
            .scale(animatedScale)
            .graphicsLayer {
                translationX = pageOffset * size.width * 0.1f
            }
    ) {
        // Enhanced Illustration with better animations
        Box(
            modifier = Modifier
                .size(280.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            page.backgroundColor,
                            page.backgroundColor.copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        radius = 300f
                    ),
                    shape = RoundedCornerShape(40.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            page.icon()
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Animated title with typewriter effect
        AnimatedTitle(
            text = page.title,
            isActive = isActive,
            color = page.accentColor
        )

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedVisibility(
            visible = isActive,
            enter = fadeIn(tween(800, delayMillis = 300)) + slideInVertically(tween(800, delayMillis = 300)) { it },
            exit = fadeOut(tween(400))
        ) {
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun AnimatedTitle(
    text: String,
    isActive: Boolean,
    color: Color
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(isActive) {
        if (isActive) {
            displayedText = ""
            text.forEachIndexed { index, char ->
                kotlinx.coroutines.delay(50)
                displayedText = text.substring(0, index + 1)
            }
        }
    }

    Text(
        text = displayedText,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        color = color,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun EnhancedPageIndicators(
    currentPage: Int,
    pages: List<OnboardingPage>,
    onPageClick: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        pages.forEachIndexed { index, page ->
            EnhancedPageIndicator(
                isActive = index == currentPage,
                color = page.accentColor,
                onClick = { onPageClick(index) }
            )
        }
    }
}

@Composable
fun EnhancedPageIndicator(
    isActive: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    val width by animateDpAsState(
        targetValue = if (isActive) 32.dp else 12.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "indicator_width"
    )

    val height by animateDpAsState(
        targetValue = if (isActive) 8.dp else 8.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "indicator_height"
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(
                brush = if (isActive) {
                    Brush.horizontalGradient(
                        colors = listOf(color, color.copy(alpha = 0.7f))
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(color.copy(alpha = 0.3f), color.copy(alpha = 0.2f))
                    )
                },
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() }
            .shadow(
                elevation = if (isActive) 8.dp else 0.dp,
                shape = RoundedCornerShape(4.dp),
                ambientColor = color.copy(alpha = 0.3f)
            )
    )
}

@Composable
fun EnhancedActionButtons(
    currentPage: Int,
    totalPages: Int,
    onNext: () -> Unit,
    onSkip: () -> Unit,
    accentColor: Color
) {
    val isLastPage = currentPage == totalPages - 1

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Main action button
        AnimatedContent(
            targetState = isLastPage,
            transitionSpec = {
                slideInVertically { it } + fadeIn() togetherWith
                        slideOutVertically { -it } + fadeOut()
            },
            label = "button_content"
        ) { isLast ->
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = accentColor.copy(alpha = 0.3f)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (isLast) "Get Started" else "Next",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color.White
                    )

                    Icon(
                        imageVector = if (isLast) Icons.Filled.PlayArrow else Icons.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
        }

        // Skip button (only show if not last page)
        AnimatedVisibility(
            visible = !isLastPage,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            TextButton(
                onClick = onSkip,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = "Skip for now",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Enhanced illustration components
@Composable
fun InterviewIllustration() {
    val infiniteTransition = rememberInfiniteTransition()
    val bounce by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "bounce"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.offset(y = bounce.dp)
    ) {
        // Human candidate
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFF6B6B), Color(0xFFFF8787))
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "You",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6B7280)
            )
        }

        // Chat bubbles
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ChatBubble("Tell me about yourself", true)
            ChatBubble("I'm passionate about...", false)
        }

        // AI Interviewer
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF8B5CF6), Color(0xFFA78BFA))
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.p),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "AI Coach",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Composable
fun CommunicationIllustration() {
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulse"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Microphone and text icons
        Row(
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .scale(pulse)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF06B6D4), Color(0xFF67E8F9))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }

            Text(
                text = "OR",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF6B7280)
            )

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF10B981), Color(0xFF6EE7B7))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }
        }

        Text(
            text = "Choose your style",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B7280)
        )
    }
}

@Composable
fun FeedbackIllustration() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    Box(
        contentAlignment = Alignment.Center
    ) {
        // Rotating feedback ring
        Box(
            modifier = Modifier
                .size(150.dp)
                .rotate(rotation)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFF10B981),
                            Color(0xFF059669),
                            Color.Transparent,
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Center icon
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF10B981), Color(0xFF34D399))
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.trendingUp),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun ChatBubble(text: String, isFromUser: Boolean) {
    val backgroundColor = if (isFromUser) Color(0xFFE5E7EB) else Color(0xFF8B5CF6)
    val textColor = if (isFromUser) Color(0xFF374151) else Color.White

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            maxLines = 1
        )
    }
}

@Composable
fun AnimatedBackgroundParticles(currentPage: Int) {
    val infiniteTransition = rememberInfiniteTransition()

    repeat(8) { index ->
        val offsetY by infiniteTransition.animateFloat(
            initialValue = -50f + (index * 100f),
            targetValue = 1000f + (index * 100f),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 10000 + (index * 2000),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ), label = "particle_$index"
        )

        val offsetX by infiniteTransition.animateFloat(
            initialValue = (index * 50f),
            targetValue = (index * 50f) + 100f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 8000 + (index * 1000),
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ), label = "particle_x_$index"
        )

        val density = LocalDensity.current

        Box(
            modifier = Modifier
                .offset(
                    x = with(density) { offsetX.toDp() },
                    y = with(density) { offsetY.toDp() }
                )
                .size(4.dp)
                .background(
                    color = Color(0xFF8B5CF6).copy(alpha = 0.1f),
                    shape = CircleShape
                )
        )
    }
}