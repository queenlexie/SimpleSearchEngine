package search

import java.io.File
import kotlin.collections.Set as Set
fun main(args: Array<String>) {
    //println(readWords(readLine()!!.toString(), readLine()!!.toString().split(" ") as ArrayList<String>))
    //lookingForAPeople()
    //chooseUserMenu(getListOfPeople())
    readFromTheFile(args[1])
}

fun readWords(wordToFind: String, lineOfWords: List<String>): Int {
    for(oneWord in lineOfWords)
       // if(wordToFind.equals(oneWord, ignoreCase = true) || oneWord.contains(wordToFind, ignoreCase = true))
       if(wordToFind.equals(oneWord, ignoreCase = true))
            return (lineOfWords.indexOf(oneWord)+1)
    //else
        //println("Not Found")
    return -1
}
fun readANYWords(wordToFind: String, lineOfWords: List<String>): Int {
    for(oneWord in lineOfWords)
        if(wordToFind.contains(oneWord, ignoreCase = true))
            return (lineOfWords.indexOf(oneWord)+1)
    return -1
}
fun lookingForAPeople() {
    val listOfPeople = getListOfPeople()
    println("\nEnter the number of search queries:")
    val searchQueries = readLine()!!.toInt()
    for (i in 1..searchQueries) {
        println("\nEnter data to search people:")
        val dataToSearchPeople = readLine()!!.toString()
        printListOfPeople(dataToSearchPeople.toLowerCase(), listOfPeople)
    }
}
fun lookingForPeopleWithNameOrEmail(listOfPeople: List<String>) {
    println("\nEnter a name or email to search all suitable people.")
        val dataToSearchPeople = readLine()!!.toString()
        printListOfPeople(dataToSearchPeople.toLowerCase(), listOfPeople)
}
fun searchingThroughList(word: String, list: List<String>): List<String> {
        val outputList = mutableListOf<String>()
        for (line in list)
            if (readWords(word, line.split(" ")) != -1)
                outputList.add(line)
        return outputList
}

fun chooseUserMenu(listOfPeople: List<String>) {
    printMenu()
    when (readLine()!!.toInt()) {
            1 -> {
                //lookingForPeopleWithNameOrEmail(listOfPeople)
                //lookingForPeopleInMap(listOfPeople)
                selectStrategy(listOfPeople)
                chooseUserMenu(listOfPeople)
            }
            2 -> {
                println("\n=== List of people ===")
                for(person in listOfPeople)
                    println(person)
                chooseUserMenu(listOfPeople)
            }
            0 -> println("\nBye!")
            else -> {
                println("\nIncorrect option! Try again.")
                chooseUserMenu(listOfPeople)
            }
        }
}

fun getListOfPeople(): List<String> {
        val listOfPeople = mutableListOf<String>()
        println("Enter the number of people")
        val numberOfPeople = readLine()!!.toInt()
        println("Enter all people:")
        for (i in 1..numberOfPeople)
            listOfPeople.add(readLine()!!)
        return listOfPeople
}

fun printListOfPeople(dataToSearchPeople: String, listOfPeople: List<String>) {
    val listOfFoundPeople = searchingThroughList(dataToSearchPeople, listOfPeople)
        if (listOfFoundPeople.isNotEmpty()) {
            println("\nFound people:")
            for (data in listOfFoundPeople)
                println(data)
        } else
            println("No matching people found.")
}
fun printMenu(){
    println("\n=== Menu ===\n1. Search information.\n2. Print all data.\n0. Exit.")
}
fun readFromTheFile(filePath : String){
    val file = readFileAsLinesUsingReadLines(filePath)
    chooseUserMenu(file)
}
fun readFileAsLinesUsingReadLines(fileName: String): List<String>
        = File(fileName).readLines()

fun getDataToSearch(): String {
    println("\nEnter a name or email to search all suitable people.")
    return readLine()!!.toString()
}

fun createMap(listOfPeople: List<String>): MutableMap<Int, String> {
    val mapOfPeople = mutableMapOf<Int, String>()
    for((index, person) in listOfPeople.withIndex()) {
        mapOfPeople[index] = person
    }
    return mapOfPeople
}
fun searchingThroughMap(word: String, map: Map<Int, String>): Set<Int> {
    val keys: MutableSet<Int> = mutableSetOf()
    for ((key, value) in map) {
        if(readWords(word, value.split(" ")) != -1 )
            keys.add(key)
    }
    return keys
}
fun searchingForANYThroughMap(word: String, map: Map<Int, String>): Set<Int> {
    val keys: MutableSet<Int> = mutableSetOf()
    for ((key, value) in map) {
        if(readANYWords(word, value.split(" ")) != -1 )
            keys.add(key)
    }
    return keys
}

fun printSetOfPeople(setOfFoundPeopleIndex : Set<Int>, mapOfPeople : Map<Int, String>) {
    if (setOfFoundPeopleIndex.isNotEmpty()) {
        println("\n${setOfFoundPeopleIndex.size} persons found:")
        for (data in setOfFoundPeopleIndex)
            if(mapOfPeople.containsKey(data))
                println(mapOfPeople[data])
    } else
        println("No matching people found.")
}

fun searchWithALLStrategy(listOfPeople: List<String>){
    val dataToSearchPeople = getDataToSearch()
    val mapOfPeople = createMap(listOfPeople)
    val setOfFoundPeopleIndex = searchingThroughMap(dataToSearchPeople, mapOfPeople)
    printSetOfPeople(setOfFoundPeopleIndex, mapOfPeople)
}

fun searchWithANYStrategy(listOfPeople: List<String>){
    val dataToSearchPeople = getDataToSearch()
    val mapOfPeople = createMap(listOfPeople)
    val setOfFoundPeopleIndex = searchingForANYThroughMap(dataToSearchPeople, mapOfPeople)
    printSetOfPeople(setOfFoundPeopleIndex, mapOfPeople)
}

fun searchWithNONEStrategy(listOfPeople: List<String>){
    val dataToSearchPeople = getDataToSearch()
    val mapOfPeople = createMap(listOfPeople)
    val setOfFoundPeopleIndex = searchingForANYThroughMap(dataToSearchPeople, mapOfPeople)
    val setOfNotFoundPeopleIndex = mutableSetOf<Int>()
    for(element in mapOfPeople.keys)
        if(!setOfFoundPeopleIndex.contains(element))
            setOfNotFoundPeopleIndex.add(element)
    printSetOfPeople(setOfNotFoundPeopleIndex, mapOfPeople)
}

fun selectStrategy(listOfPeople: List<String>){
    println("Select a matching strategy: ALL, ANY, NONE")
    when (readLine()!!) {
        "ANY" -> searchWithANYStrategy(listOfPeople)
        "ALL" -> searchWithALLStrategy(listOfPeople)
        "NONE" -> searchWithNONEStrategy(listOfPeople)
    }
}