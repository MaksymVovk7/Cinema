import java.math.BigDecimal
import java.math.RoundingMode

fun main() {
    println("Enter the number of rows:")
    print("> ")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    print("> ")
    val seats = readLine()!!.toInt()
    println("")
    val myCinema = fillCinema(rows, seats)
    var currentIncome = 0
    val totalIncome = totalIncome(rows, seats)
    var purchasedTickets = 0
    do {
        showOptions()
        print("> ")
        val option = readLine()!!.toInt()
        when (option) {
            1 -> {
                printInfo(seats)
                printCinema(myCinema)
                println("")
            }
            2 -> {
                currentIncome += buyTicket(rows, seats, myCinema)
                purchasedTickets = purchasedTickets(myCinema, rows, seats)
            }
            3 -> showStatistics(purchasedTickets, rows, seats, currentIncome, totalIncome)
            else -> return
        }
    } while (option > 0)
}
fun showOptions() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}
fun printInfo(seats: Int) {
    println("")
    println("Cinema:")
    println("  ${IntArray(seats){it + 1 }.joinToString(" ")}")
}
fun fillCinema(rows: Int, seats: Int): Array<Array<String>> {
    val cinema = Array(rows) { Array<String>(seats){""} }
    for (i in 0 until rows) {
        for (j in 0 until seats) {
            cinema[i][j] = "S"
        }
    }
    return cinema
}
fun printCinema(cinema: Array<Array<String>>) {
    var count = 1
    for (item in cinema) {
        println("${count++} ${item.joinToString(" ")}")
    }
}
fun takeTicket(rows: Int, seats: Int, row: Int): Int {
    val tickets = rows * seats
    val half = rows / 2
    return if (tickets <= 60 ) {
        10
    } else {
        when {
            row <= half -> 10
            else -> 8
        }
    }
}
fun buyTicket(rows: Int, seats: Int, myCinema: Array<Array<String>>): Int {
    var result = 0
    var flag = true
    var stop = false
    do {
        println("")
        println("Enter a row number:")
        print("> ")
        val row = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        print("> ")
        val seat = readLine()!!.toInt()
        println("")
        if (row > rows || seat > seats) {
            println("Wrong input!")
            println("")
        } else {
            if (myCinema[row - 1][seat - 1] == "B") {
                println("That ticket has already been purchased!")
                println("")
                flag = false
                stop = true
            } else {
                result = takeTicket(rows, seats, row)
                myCinema[row - 1][seat - 1] = "B"
                stop = false
                flag = true
            }
            if (flag) {
                println("Ticket price: $$result")
                println("")
            }
        }
    } while (row > rows || seat > seats || stop)

    return result
}
fun purchasedTickets(myCinema: Array<Array<String>>, rows: Int, seats: Int): Int {
    var result = 0
    for (i in 0 until rows) {
        for (j in 0 until seats) {
            if (myCinema[i][j] == "B") {
                result++
            }
        }
    }
    return result
}
fun totalIncome(rows: Int, seats: Int): Int {
    val tickets = rows * seats
    val half = rows / 2
    return if (tickets <= 60 ) {
        tickets * 10
    } else {
        if (rows % 2 == 0){
            (half * seats * 10) + (half * seats * 8)
        } else {
            (half * seats * 10) + ((half + 1) * seats * 8)
        }
    }
}
fun percentage(purchasedTickets: Int, rows: Int, seats: Int): Double {
    val totalTickets = rows * seats
    return (purchasedTickets.toDouble() * 100) / totalTickets.toDouble()
}
fun showStatistics(purchasedTickets: Int, rows: Int, seats: Int, currentIncome: Int, totalIncome: Int) {
    println("")
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: ${BigDecimal(percentage(purchasedTickets, rows, seats)).setScale(2,RoundingMode.HALF_EVEN)}%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
    println("")
}
