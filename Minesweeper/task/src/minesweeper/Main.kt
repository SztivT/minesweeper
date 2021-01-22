package minesweeper

import java.util.*
import kotlin.random.Random

val scanner = Scanner(System.`in`)
val field = Array(9) { CharArray(9) }
fun main() {
    print("How many mines do you want on the field?")
    var mines = scanner.nextInt()
    for (i in field.indices) {
        var minesInRow = 0
        if (i == field.lastIndex && mines > 0) {
            minesInRow = mines
        }
        if (mines > 0) {
            minesInRow = Random.nextInt() % mines
        }
        mines -= minesInRow
        for (j in field[i].indices) {
            if (field[i].lastIndex - j == minesInRow) {
                field[i][j] = 'X'
            }
            else if (Random.nextInt() % 2 == 1 && minesInRow > 0) {
                field[i][j] = 'X'
            }
            else {
                field[i][j] = '.'
            }
        }
    }
    for (row in field) {
        for (i in row.indices){
            print(row[i])
            if (i == row.lastIndex){
                print("\n")
            }
        }
    }
}
