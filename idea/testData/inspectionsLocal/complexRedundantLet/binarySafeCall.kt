// PROBLEM: none
// WITH_RUNTIME

val x = 1
val y = x.<caret>let { it + it?.hashCode() }