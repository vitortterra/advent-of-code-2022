using System;
using System.Collections.Generic;
using System.Linq;

class Program 
{
    public static void Main(string[] args) 
    {
        // A: Rock B: Paper C: Scissors (opponent)
        // X: Rock (+1) Y: Paper (+2) Z: Scissors (+3)
        // Lose: 0 Draw: +3 Win: +6
        var movesToPoints = new Dictionary<string, int>()
        {
            {"AX", 4}, {"AY", 8}, {"AZ", 3}, 
            {"BX", 1}, {"BY", 5}, {"BZ", 9}, 
            {"CX", 7}, {"CY", 2}, {"CZ", 6} 
        };

        // A: Rock B: Paper C: Scissors (opponent)
        // X: Lose (0) Y: Draw (+3) Z: Win (+6)
        // Rock: +1 Paper: +2 Scissors: +3
        var requirementsToPoints = new Dictionary<string, int>()
        {
            {"AX", 3}, {"AY", 4}, {"AZ", 8}, 
            {"BX", 1}, {"BY", 5}, {"BZ", 9}, 
            {"CX", 2}, {"CY", 6}, {"CZ", 7} 
        };

        var moves = parseInput();
        var totalPointsPart1 = moves.Select(m => movesToPoints[m]).Sum();
        Console.WriteLine($"Part 1: {totalPointsPart1}");

        var totalPointsPart2 = moves.Select(m => requirementsToPoints[m]).Sum();
        Console.WriteLine($"Part 2: {totalPointsPart2}");
    }

    private static IEnumerable<string> parseInput()
    {
        string line = Console.ReadLine();
        var parsedLines = new List<string>();

        while(!String.IsNullOrWhiteSpace(line))
        {
            parsedLines.Add($"{line[0]}{line[2]}");
            line = Console.ReadLine();
        }

        return parsedLines;
    }
}
