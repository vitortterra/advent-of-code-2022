data class Move(
    val count: Int,
    val from: Int,
    val to: Int,
)

fun readMoves() : List<Move> {
    val moves = mutableListOf<Move>()

    while (true) {
        val rawLine = readLine()
        if (rawLine == null) {
            break
        }
        
        val regex = Regex("move ([0-9]+) from ([0-9]+) to ([0-9]+)")
        val match = regex.find(rawLine)
        val (count, from, to) = match!!.destructured.toList().map { it.toInt() }
        moves.add(Move(count, from, to))
    }
    return moves
}

fun readStacks() : List<ArrayDeque<Char>> {
    val lines = mutableListOf<String>()

    while (true) {
        val rawLine = readLine()
        if (rawLine.isNullOrBlank()) {
            break
        }
        lines.add(rawLine.filterIndexed { i, _ -> i % 4 == 1 })
    }

    val numStacks = lines.last().length
    val maxHeight = lines.size - 1
    
    val stacks = List(numStacks + 1, { ArrayDeque<Char>() })

    for (h in (maxHeight-1) downTo 0) {
        for (stackId in 1..numStacks) {
            val crate = lines[h][stackId-1];
            
            if (!crate.isWhitespace()) {
                stacks[stackId].addLast(crate)
            }
        }
    }

    return stacks
}

fun copyStacks(stacks : List<ArrayDeque<Char>>) : List<ArrayDeque<Char>> {
    return stacks.map { ArrayDeque<Char>(it.toList()) };
}

fun moveOneAtTime(startStacks : List<ArrayDeque<Char>>, moves : List<Move>) : List<ArrayDeque<Char>> {
    val stacks = copyStacks(startStacks)
    
    for (move in moves) {
        for (i in 1..move.count) {
            val crate = stacks[move.from].last()
            stacks[move.from].removeLast()
            stacks[move.to].addLast(crate)
        }
    }

    return stacks;
}

fun moveAllAtOnce(startStacks : List<ArrayDeque<Char>>, moves : List<Move>) : List<ArrayDeque<Char>> {
    val stacks = copyStacks(startStacks)
    
    for (move in moves) {
        val auxStack = ArrayDeque<Char>()
        
        for (i in 1..move.count) {
            val crate = stacks[move.from].last()
            stacks[move.from].removeLast()
            auxStack.addLast(crate);
        }

        while (auxStack.isNotEmpty()) {
            val crate = auxStack.last()
            auxStack.removeLast()
            stacks[move.to].addLast(crate);
        }
    }

    return stacks;
}


fun getStacksTops(stacks : List<ArrayDeque<Char>>) : String {
    val range = 1..(stacks.size - 1)
    
    return (range.map { it -> stacks[it].last() }).joinToString("")
}

fun main() {
    val stacks = readStacks()
    val moves = readMoves()

    val stacksPart1 = moveOneAtTime(stacks, moves)
    val stacksTopsPart1 = getStacksTops(stacksPart1)

    val stacksPart2 = moveAllAtOnce(stacks, moves)
    val stacksTopsPart2 = getStacksTops(stacksPart2)
    
    println("Part 1: $stacksTopsPart1")
    println("Part 2: $stacksTopsPart2")
}
