<?php

$pairs_list = [];

while (FALSE !== ($line = fgets(STDIN))) {
    $pairs_list[] = array_map('intval', preg_split('/(\-|\,)/', rtrim($line)));
}

$fully_contains = function ($pairs) {
    return ($pairs[0] >= $pairs[2] && $pairs[1] <= $pairs[3]) 
        || ($pairs[0] <= $pairs[2] && $pairs[1] >= $pairs[3]);
};

$overlaps = function ($pairs) {
    return ($pairs[1] >= $pairs[2] && $pairs[0] <= $pairs[3]) 
        || ($pairs[1] <= $pairs[2] && $pairs[0] >= $pairs[3]);
};

$ans_part1 = count(array_filter($pairs_list, $fully_contains));
$ans_part2 = count(array_filter($pairs_list, $overlaps));

printf("Part 1: %d\n", $ans_part1);
printf("Part 2: %d\n", $ans_part2);

?>