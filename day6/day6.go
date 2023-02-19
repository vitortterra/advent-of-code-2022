package main

import (
    "bufio"
    "fmt"
    "log"
    "os"
)

func checkMarker(m map[byte]int, size int) bool {
    if len(m) < size {
        return false
    }

    for _, count := range m { 
        if count > 1 {
            return false
        }
    }
    
    return true
}

func findMarkerPosition(str string, markerSize int) int {
    marker := make(map[byte]int)

    for i := 0; i < len(str); i++ {
        marker[str[i]]++
        if i - markerSize >= 0 {
            marker[str[i - markerSize]]--
        }

        if (checkMarker(marker, markerSize)) {
            return i+1
        }
    }
    
    return -1
}

func main() {
    reader := bufio.NewReader(os.Stdin)
    str, err := reader.ReadString('\n')
    if err != nil {
        log.Fatal(err)
    }

    fmt.Printf("Part 1: %d\n", findMarkerPosition(str, 4))
    fmt.Printf("Part 2: %d\n", findMarkerPosition(str, 14))
}
