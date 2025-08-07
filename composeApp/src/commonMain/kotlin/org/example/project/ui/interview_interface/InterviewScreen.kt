package org.example.project.ui.interview_interface

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import org.example.project.design.MockMateTheme

data class InterviewMessage(
    val id: Int,
    val text: String,
    val isFromBot: Boolean
)

data class InterviewQuestion(
    val id: Int,
    val question: String,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewScreen(
    onInterviewComplete: (Any) -> Unit,
    onBackClick: () -> Unit
) {
    var isVoiceMode by remember { mutableStateOf(true) }
    var currentMessage by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var timeElapsed by remember { mutableIntStateOf(0) }
    var messages by remember { mutableStateOf(listOf<InterviewMessage>()) }

    val questions = remember {
        listOf(
            InterviewQuestion(1, "Tell me about yourself.", "General"),
            InterviewQuestion(2, "What are your strengths and weaknesses?", "General"),
            InterviewQuestion(3, "Describe a time you failed and how you handled it.", "Behavioral"),
            InterviewQuestion(4, "Where do you see yourself in 5 years?", "Future Goals"),
            InterviewQuestion(5, "Why do you want to work here?", "Company Specific")
        )
    }

    // Initialize with first question
    LaunchedEffect(Unit) {
        messages = listOf(
            InterviewMessage(
                id = 0,
                text = "Hello! I'm your AI interviewer. Let's begin with our first question: ${questions[0].question}",
                isFromBot = true
            )
        )

        // Timer
        while (true) {
            delay(1000)
            timeElapsed++
        }
    }

    MockMateTheme {
        Scaffold(
            topBar = {
                InterviewTopBar(
                    currentQuestion = currentQuestionIndex + 1,
                    totalQuestions = questions.size,
                    timeElapsed = timeElapsed,
                    onBackClick = onBackClick,
                    isVoiceMode = isVoiceMode,
                    onToggleMode = { isVoiceMode = !isVoiceMode }
                )
            },
            bottomBar = {
                InterviewInputBar(
                    currentMessage = currentMessage,
                    onMessageChange = { currentMessage = it },
                    onSendMessage = {
                        if (currentMessage.isNotBlank()) {
                            // Add user message
                            messages = messages + InterviewMessage(
                                id = messages.size,
                                text = currentMessage,
                                isFromBot = false
                            )
                            currentMessage = ""

                            // Simulate AI response
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                                messages = messages + InterviewMessage(
                                    id = messages.size,
                                    text = "Great answer! Let's move to the next question: ${questions[currentQuestionIndex].question}",
                                    isFromBot = true
                                )
                            } else {
                                messages = messages + InterviewMessage(
                                    id = messages.size,
                                    text = "Thank you for completing the interview! I'll now analyze your responses and provide feedback.",
                                    isFromBot = true
                                )
                                onInterviewComplete("feedback_data")
                            }
                        }
                    },
                    isVoiceMode = isVoiceMode,
                    isRecording = isRecording,
                    onToggleRecording = { isRecording = !isRecording }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
            ) {
                // Progress Indicator
                InterviewProgress(
                    currentQuestion = currentQuestionIndex + 1,
                    totalQuestions = questions.size
                )

                // Messages Area
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(messages) { message ->
                        InterviewMessageBubble(message = message)
                    }

                    // Typing indicator
                    if (isRecording && isVoiceMode) {
                        item {
                            VoiceRecordingIndicator()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewTopBar(
    currentQuestion: Int,
    totalQuestions: Int,
    timeElapsed: Int,
    onBackClick: () -> Unit,
    isVoiceMode: Boolean,
    onToggleMode: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Q $currentQuestion/$totalQuestions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = formatTime(timeElapsed),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            // Mode Toggle
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isVoiceMode) Color(0xFF8B5CF6) else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clickable { onToggleMode() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = if (isVoiceMode) Icons.Filled.Call else Icons.Filled.Email,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (isVoiceMode) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (isVoiceMode) "Voice" else "Chat",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = if (isVoiceMode) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
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

@Composable
fun InterviewProgress(
    currentQuestion: Int,
    totalQuestions: Int
) {
    val progress = currentQuestion.toFloat() / totalQuestions.toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = Color(0xFF8B5CF6),
            trackColor = Color(0xFF8B5CF6).copy(alpha = 0.1f)
        )
    }
}

@Composable
fun InterviewMessageBubble(message: InterviewMessage) {
    val bubbleColor = if (message.isFromBot)
        MaterialTheme.colorScheme.surface
    else
        Color(0xFF8B5CF6)

    val textColor = if (message.isFromBot)
        MaterialTheme.colorScheme.onSurface
    else
        Color.White

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromBot) Arrangement.Start else Arrangement.End
    ) {
        if (message.isFromBot) {
            // Bot Avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = Color(0xFF8B5CF6).copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color(0xFF8B5CF6),
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(
                topStart = if (message.isFromBot) 4.dp else 16.dp,
                topEnd = if (message.isFromBot) 16.dp else 4.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            color = bubbleColor,
            shadowElevation = if (message.isFromBot) 2.dp else 0.dp
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor,
                    lineHeight = 20.sp
                )
            )
        }

        if (!message.isFromBot) {
            Spacer(modifier = Modifier.width(8.dp))

            // User Avatar
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
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B6B)
                    )
                )
            }
        }
    }
}

@Composable
fun VoiceRecordingIndicator() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = Color(0xFF8B5CF6).copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = Color(0xFF8B5CF6),
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            modifier = Modifier.alpha(alpha),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewInputBar(
    currentMessage: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    isVoiceMode: Boolean,
    isRecording: Boolean,
    onToggleRecording: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isVoiceMode) {
                // Voice Input
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (isRecording) {
                        VoiceWaveform()
                    } else {
                        Text(
                            text = "Tap the microphone to start speaking",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Microphone Button
                FloatingActionButton(
                    onClick = onToggleRecording,
                    modifier = Modifier.size(56.dp),
                    containerColor = if (isRecording) Color(0xFFEF4444) else Color(0xFF8B5CF6)
                ) {
                    Icon(
                        imageVector = if (isRecording) Icons.Filled.Clear else Icons.Filled.Call,
                        contentDescription = if (isRecording) "Stop Recording" else "Start Recording",
                        tint = Color.White
                    )
                }
            } else {
                // Text Input
                OutlinedTextField(
                    value = currentMessage,
                    onValueChange = onMessageChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type your answer here...") },
                    shape = RoundedCornerShape(24.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Send
                    ),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8B5CF6),
                        cursorColor = Color(0xFF8B5CF6)
                    )
                )

                // Send Button
                FloatingActionButton(
                    onClick = onSendMessage,
                    modifier = Modifier.size(56.dp),
                    containerColor = Color(0xFF8B5CF6)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send Message",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun VoiceWaveform() {
    val infiniteTransition = rememberInfiniteTransition()

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val height by infiniteTransition.animateFloat(
                initialValue = 4f,
                targetValue = 20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 600 + (index * 100),
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(height.dp)
                    .background(
                        color = Color(0xFF8B5CF6),
                        shape = RoundedCornerShape(1.5.dp)
                    )
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.toString()
}