(* 
  Run with:

  ml-build aoe1.cm AdventOfCode.main aoe1-image
  sml @SMLload aoe1-image.XXXX < input.txt 

  where XXXX=your arch (for example, XXXX=amd64-linux) 
*)

structure AdventOfCode = 
struct

fun parse_input () = 
  let 
    fun helper lines =
      case TextIO.inputLine TextIO.stdIn of
          NONE => lines
        | SOME str => helper(str :: lines)
  in
    helper []
  end

fun main (prog_name, args) =
  let
    fun f (elem, acc) =
        let
          val (sum_list, curr_sum) = acc
        in
          case (Int.fromString elem) of
              NONE => 
                if curr_sum > 0
                then ((curr_sum :: sum_list), 0)
                else (sum_list, 0)
            | SOME num => (sum_list, curr_sum + num)
        end

    fun g (elem, acc) = 
      let 
        val (first, second, third) = acc
      in
        if elem > first then (elem, first, second)
        else if elem > second then (first, elem, second)
        else if elem > third then (first, second, elem)
        else (first, second, third)
      end

    val input_list = parse_input ()
    val (list_of_sums, _) = foldl f ([], 0) input_list

    val max_sum = foldl Int.max 0 list_of_sums
    val _ = print ("Part 1: " ^ Int.toString max_sum ^ "\n")

    val (first, second, third) = foldl g (0, 0, 0) list_of_sums
    val _ = print ("Part 2: " ^ Int.toString (first + second + third) ^ "\n")
  in
    0
  end
end
