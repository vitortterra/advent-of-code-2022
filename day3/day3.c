#include <stdio.h>
#include <string.h>

#define MAX_RUCKSACK_SIZE 128
#define NUM_ITEM_TYPES 52

char get_item(int index) {
    char s[NUM_ITEM_TYPES+1] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    return s[index];
}

int get_index(char c) {
    return c - 'a' >= 0 ? c - 'a' : c - 'A' + 26;
}

int get_priority(char c) {
    return get_index(c) + 1;
}

int main() {
    char rucksack[MAX_RUCKSACK_SIZE];
    char seen_in_compart[NUM_ITEM_TYPES] = { 0 };
    char seen_in_rucksack[NUM_ITEM_TYPES] = { 0 };
    char count[NUM_ITEM_TYPES] = { 0 };

    int priority_sum_p1 = 0;
    int priority_sum_p2 = 0;
    int line_count = 0;

    while (scanf("%s\n", rucksack) != EOF) {
        // Part 1
        memset(seen_in_compart, 0, NUM_ITEM_TYPES);
        int compart_size = strlen(rucksack) / 2;

        for (int i = 0; i < compart_size; i++) {
            seen_in_compart[get_index(rucksack[i])] = 1;
        }
        
        for (int i = compart_size; i < 2*compart_size; i++) {
            if (seen_in_compart[get_index(rucksack[i])]) {
                priority_sum_p1 += get_priority(rucksack[i]);
                break;
            }
        }
        
        // Part 2
        memset(seen_in_rucksack, 0, NUM_ITEM_TYPES);

        for (int i = 0; i < 2*compart_size; i++) {
            seen_in_rucksack[get_index(rucksack[i])] = 1;
        }

        for (int i = 0; i < NUM_ITEM_TYPES; i++) {
            count[i] += seen_in_rucksack[i];
        }

        if (line_count % 3 == 2) {
            for (int i = 0; i < NUM_ITEM_TYPES; i++) {
                if (count[i] == 3) {
                    priority_sum_p2 += get_priority(get_item(i));
                    break;                    
                }
            }
            memset(count, 0, NUM_ITEM_TYPES);
        }

        line_count++;
    }

    printf("Part 1: %d\n", priority_sum_p1);
    printf("Part 2: %d\n", priority_sum_p2);

    return 0;
}