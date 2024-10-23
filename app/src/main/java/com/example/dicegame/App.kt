@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.dicegame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReusableComposeNode
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.isPopupLayout
import kotlinx.coroutines.launch
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun App(modifier: Modifier = Modifier, innerPadding: PaddingValues = PaddingValues()){
    var scorePlayer1 = remember { mutableStateOf(0) }
    var scorePlayer2 = remember { mutableStateOf(0) }

    var isPlayer1Turn = remember {mutableStateOf(true) }

    var currentImage = remember { mutableStateOf(0) }
    var images = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6

    )

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    var gameState by remember { mutableStateOf("Playing..") }

    //  PLAYERS SCORE



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(color = Color(0xffffffffffff)),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
           ) {


        Row {
            Text(text = "Player1 score: ${scorePlayer1.value}",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Player2 score: ${scorePlayer2.value}",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

          Spacer(modifier = Modifier.height(30.dp))

        //Spacer(modifier = Modifier.height(220.dp))

        if (isPlayer1Turn.value)
            Text(text = "Player 1 turn")
        else {
            Text(text = "Player 2 turn")
        }
        Image(
            painter = if (currentImage.value == 0) {
                painterResource(R.drawable.two_dice)
            } else painterResource(images.get(currentImage.value - 1)),
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            //PLAYER 1 BUTTON
            Button(
                onClick = {
                    val random = Random.nextInt(6) + 1
                    currentImage.value = random
                    scorePlayer1.value = scorePlayer1.value + random  //SCORE
                    isPlayer1Turn.value = !isPlayer1Turn.value

                },
                enabled = if (isPlayer1Turn.value) true else false,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(text = "Player 1")
            }


            Spacer(modifier = Modifier.width(10.dp))

            //PLAYER 2 BUTTON

            Button(
                onClick = {
                    val random = Random.nextInt(6) + 1
                    currentImage.value = random
                    scorePlayer2.value = scorePlayer2.value + random //SCORE
                    isPlayer1Turn.value = !isPlayer1Turn.value

                },
                enabled = if (isPlayer1Turn.value) false else true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(text = "Player 2")
            }


        }
    }


//
    if (scorePlayer1.value >= 20 || scorePlayer2.value >= 20) {
        Column (modifier = Modifier
            .background(
                color = Color.White
            )
            .padding(20.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ){
        AlertDialog(

            title = {

                if (scorePlayer1.value > scorePlayer2.value) {
                    Text(
                        text = "Player 1 won",
                        style = MaterialTheme
                            .typography.headlineLarge
                    )
                } else {
                    Text(
                        text = "Player 2 won",
                        style = MaterialTheme
                            .typography.headlineLarge
                    )
                }



            },

            text = {
                Column{
                    Text(text = "Player1 score: ${scorePlayer1.value}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Player2 score: ${scorePlayer2.value}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            },



            onDismissRequest = { },

                     confirmButton = {
                         Row(modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.Center) {

                             Button(
                                 onClick = {
                                     scorePlayer1.value = 0
                                     scorePlayer2.value = 0
                                     isPlayer1Turn.value = true
                                 },
                                 colors = ButtonDefaults.buttonColors(
                                     containerColor = Color.Black
                                 ),

                                 ) {
                                 Text(text = "Restart")
                             }


                             //}


                         }
                     })
    }

    }


        /* BOTTOM SHEET TRANSITION FOR THE RESULT OF THE GAME  */


//        Box(modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ){
//            Button(onClick = { isSheetOpen = true }) {
//                Text(text = "Open sheet")
//            }
////        }

//     if (isSheetOpen) {
//         ModalBottomSheet(
//             sheetState = sheetState,
//             onDismissRequest = { isSheetOpen = false }) {
//             Image(
//                 painter = painterResource(id = R.drawable.two_dice),
//                 contentDescription = null
//             )
//         }
//     }

//
//        if (scorePlayer1.value >= 20 || scorePlayer2.value >=20) {
//            ModalBottomSheet(sheetState = sheetState,
//                onDismissRequest = { isSheetOpen = false },
//                ) {
//                 Column(modifier = Modifier
//                     .fillMaxWidth()
//                     .height(200.dp)
//                     .padding(16.dp),
//                     horizontalAlignment = Alignment.CenterHorizontally,
//                     verticalArrangement = Arrangement.Center
//                 ) {
//
//
//                Box(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    if (scorePlayer1.value > scorePlayer2.value)
//                        Text(
//                            text = "Player 1 won!",
//                            style = MaterialTheme
//                                .typography.headlineLarge
//                        )
//                    else
//                        Text(
//                            text = "Player 2 won!",
//                            style = MaterialTheme
//                                .typography.headlineLarge
//                        )
//                }
//
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(innerPadding)
//                ) {
//                    Text(text = " Player 1 score: ${scorePlayer1.value}")
//                    Spacer(modifier = Modifier.width(20.dp))
//                    Text(text = "  player 2 score: ${scorePlayer2.value}")
//                }
//
//                Spacer(modifier = Modifier.height(15.dp))
//
//
//            }
//            }
//        }




 }




