package com.example.notesappwithjetpackcompose.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesappwithjetpackcompose.R
import com.example.notesappwithjetpackcompose.entity.Note
import com.example.notesappwithjetpackcompose.ui.theme.md_theme_light_primary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onDeleteNote:()->Unit
) {

    val isExpanded = remember { mutableStateOf(false) }
    val isLongPressed = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(3.dp)
            .combinedClickable(
                onClick = { isLongPressed.value = false },
                onLongClick = {
                    isLongPressed.value = true
                },
            ),
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_primary
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 5.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = note.note_title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = note.note_date!!,
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    if (isLongPressed.value == true){
                        IconButton(
                            onClick = onDeleteNote,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete_icon),
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }else{
                        if (isExpanded.value) {
                            IconButton(
                                onClick = {
                                    isExpanded.value = false
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_top_icon),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    isExpanded.value = true
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_down_icon),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                if (isExpanded.value == true) {
                    Text(
                        text = note.note_detail,
                        color = Color.White,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoteCardPreview(
    modifier: Modifier = Modifier
) {
    NoteCard(
        note = Note(1, "sasad", "asdas", "sadasd"),
        onDeleteNote = {}
    )
}