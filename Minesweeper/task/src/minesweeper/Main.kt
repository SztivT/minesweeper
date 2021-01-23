package minesweeper

import java.util.*
import kotlin.random.Random

val scanner = Scanner(System.`in`)
val field = Array(9) { CharArray(9) }
val playfield = Array(9) { CharArray(9) }
var bInGame = false
var bSteppedOnMine = false
var mines = 0
fun main() {
    startGame()
}

fun startGame() {
    print("How many mines do you want on the field? ")
    mines = scanner.nextInt()
    flushField()
    printPlayfield()
    step()
}

fun step() {
    print("Set/unset mine marks or claim a cell as free:")
    val x = scanner.nextInt() - 1
    val y = scanner.nextInt() - 1
    val type = scanner.nextLine()
    when {
        !bInGame && field[0][0] == '.' -> {
            bInGame = true
            field[y][x] = '0'
            makeBoard()
            lookAround()
            adjustPlayfield(x, y)
        }
        playfield[y][x] in '0'..'9' || playfield[y][x] == '/' -> {
            println("There is a number here!")
            step()
        }
        type == " mine" -> adjustPlayfield(x,y,"mark")
        type == " free" -> adjustPlayfield(x, y, "free")
        else -> step()
    }
    printPlayfield()
}

fun flushField() {
    for (i in field.indices) {
        for (j in field[i].indices) {
            field[i][j] = '.'
            playfield[i][j] = '.'
        }
    }
}

fun makeBoard() {
    //prepare the minefield
    for (row in field) {
        for (i in row.indices) {
            if (row[i] != '0')
                row[i] = '.'
        }
    }
    var minesRemaining = mines
    //plant mines
    while (minesRemaining > 0) {
        val x = Random.nextInt(0, 9)
        val y = Random.nextInt(0, 9)
        if (field[y][x] == '.') {
            field[y][x] = 'X'
            minesRemaining--
        }
    }
    //initializing counters
    for (i in field.indices) {
        for (j in field[i].indices) {
            if (field[i][j] == '.') {
                field[i][j] = '0'
            }
        }
    }
}

fun lookAround() {

    for (i in field.indices) {
        for (j in field[i].indices) {
            if (field[i][j] == 'X') {
                when {
                    //Top Left
                    i == 0 && j == 0 -> {
                        if (field[i][j + 1] != 'X') {
                            field[i][j + 1]++
                        }
                        if (field[i + 1][j] != 'X') {
                            field[i + 1][j]++
                        }
                        if (field[i + 1][j + 1] != 'X') {
                            field[i + 1][j + 1]++
                        }
                    }
                    //Top Right
                    i == 0 && j == field[i].lastIndex -> {
                        if (field[i][j - 1] != 'X') {
                            field[i][j - 1]++
                        }
                        if (field[i + 1][j] != 'X') {
                            field[i + 1][j]++
                        }
                        if (field[i + 1][j - 1] != 'X') {
                            field[i + 1][j - 1]++
                        }
                    }
                    //Bottom Left
                    i == field.lastIndex && j == 0 -> {
                        if (field[i][j + 1] != 'X') {
                            field[i][j + 1]++
                        }
                        if (field[i - 1][j] != 'X') {
                            field[i - 1][j]++
                        }
                        if (field[i - 1][j + 1] != 'X') {
                            field[i - 1][j + 1]++
                        }
                    }
                    //Bottom Right
                    i == field.lastIndex && j == field[i].lastIndex -> {
                        if (field[i][j - 1] != 'X') {
                            field[i][j - 1]++
                        }
                        if (field[i - 1][j] != 'X') {
                            field[i - 1][j]++
                        }
                        if (field[i - 1][j - 1] != 'X') {
                            field[i - 1][j - 1]++
                        }
                    }
                    //Top row
                    i == 0 -> {
                        for (k in j - 1..j + 1) {
                            if (field[i][k] != 'X') {
                                field[i][k]++
                            }
                            if (field[i + 1][k] != 'X') {
                                field[i + 1][k]++
                            }
                        }
                    }
                    //Bottom row
                    i == field.lastIndex -> {
                        for (k in j - 1..j + 1) {
                            if (field[i][k] != 'X') {
                                field[i][k]++
                            }
                            if (field[i - 1][k] != 'X') {
                                field[i - 1][k]++
                            }
                        }
                    }
                    //Left row
                    j == 0 -> {
                        for (k in i - 1..i + 1) {
                            if (field[k][j] != 'X') {
                                field[k][j]++
                            }
                            if (field[k][j + 1] != 'X') {
                                field[k][j + 1]++
                            }
                        }
                    }
                    //Right row
                    j == field[i].lastIndex -> {
                        for (k in i - 1..i + 1) {
                            if (field[k][j] != 'X') {
                                field[k][j]++
                            }
                            if (field[k][j - 1] != 'X') {
                                field[k][j - 1]++
                            }
                        }
                    }
                    else -> {
                        for (k in i - 1..i + 1) {
                            for (n in j - 1..j + 1) {
                                if (field[k][n] != 'X') {
                                    field[k][n]++
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun adjustPlayfield(x: Int, y: Int, type: String = "auto") {
    if (x !in 0..8 || y !in 0..8) return
    when {
        playfield[y][x] == '/' -> return
        playfield[y][x] == '.' && type == "mark" -> playfield[y][x] = '*'
        playfield[y][x] == '*' && type == "mark" -> playfield[y][x] = '.'
        field[y][x] == 'X' && type == "auto" -> return
        field[y][x] == 'X' && type == "free" -> {
            bSteppedOnMine = true
            for (i in field.indices) {
                for (j in field[i].indices) {
                    if (field[i][j] == 'X') {
                        playfield[i][j] = field[i][j]
                    }
                }
            }
        }
        field[y][x] == '0' -> {
            playfield[y][x] = '/'
            for (k in y - 1..y + 1) {
                for (n in x - 1..x + 1) {
                    adjustPlayfield(n, k)
                }
            }
        }
        else -> playfield[y][x] = field[y][x]
    }
}
fun printPlayfield() {
    //header
    print("\n |")
    for (i in 1..9) {
        print(i)
    }
    println("|")
    print("-|")
    for (i in 1..9) {
        print("-")
    }
    println("|")
    //field
    for (i in playfield.indices) {
        print("${i + 1}|")
        for (j in playfield[i].indices) {
            print(playfield[i][j])
        }
        print("|\n")
    }
    print("-|")
    for (i in 1..9) {
        print("-")
    }
    println("|")
    if (field[0][0] != '.') {
        analyzePlayfield()
    }
}

fun analyzePlayfield() {
    var mineX = mutableListOf<Int>()
    var mineY = mutableListOf<Int>()
    var starX = mutableListOf<Int>()
    var starY = mutableListOf<Int>()
    var dotX = mutableListOf<Int>()
    var dotY = mutableListOf<Int>()
    for (i in 0..8) {
        for (j in 0..8) {
            if (field[i][j] == 'X') {
                mineX.add(j)
                mineY.add(i)
            }
            if (playfield[i][j] == '*') {
                starX.add(j)
                starY.add(i)
            }
            if (playfield[i][j] == '.') {
                dotX.add(j)
                dotY.add(i)
            }
        }
    }
    for (i in 0 until mineX.size) {
        if (i > starX.size -1) break
        if (mineX[i] != starX[i]) break
        if (mineY[i] != starY[i]) break
        if (i == mineX.lastIndex) {
            bInGame = false
        }
    }
        for (i in 0 until mineX.size) {
            if (i > dotX.size -1) break
            if (mineX[i] != dotX[i]) break
            if (mineY[i] != dotY[i]) break
            if (i == mineX.lastIndex) {
                bInGame = false
            }
        }
    when {
        !bInGame -> println("Congratulations! You found all the mines!")
        bSteppedOnMine -> println("You stepped on a mine and failed!")
        else -> step()
    }
}


